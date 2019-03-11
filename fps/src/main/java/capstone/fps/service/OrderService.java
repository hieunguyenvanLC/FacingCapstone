package capstone.fps.service;

import capstone.fps.common.Fix;
import capstone.fps.common.Methods;
import capstone.fps.common.Repo;
import capstone.fps.common.Validator;
import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FROrder;
import capstone.fps.entity.FROrderDetail;
import capstone.fps.entity.FRProduct;
import capstone.fps.model.Response;
import capstone.fps.model.order.MdlOrder;
import capstone.fps.model.order.MdlOrderBuilder;
import capstone.fps.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private DistrictRepo districtRepository;
    private OrderRepo orderRepository;
    private OrderDetailRepo orderDetailRepository;
    private ProductRepo productRepository;
    private AccountRepo accountRepository;


    public OrderService(DistrictRepo districtRepository, OrderRepo orderRepository, OrderDetailRepo orderDetailRepository, ProductRepo productRepository, AccountRepo accountRepository) {
        this.districtRepository = districtRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productRepository = productRepository;
        this.accountRepository = accountRepository;
    }


    public boolean cancelOrder(int orderId) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Optional<FROrder> optionalFROrder = orderRepository.findById(orderId);
        if (!optionalFROrder.isPresent()) {
            return true;
        }
        FROrder frOrder = optionalFROrder.get();
        //check cancelable
        if (Fix.ORD_NEW.index != frOrder.getStatus()) {
            return false;
        }
        frOrder.setDeleteTime(time);
        frOrder.setStatus(Fix.ORD_CXL.index);
        orderRepository.save(frOrder);
        return true;
    }

    public boolean assignOrder(int orderId) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        FRAccount account = methods.getUser();
        if (account.getShipper() == null) {
            // Something is wrong: none shipper is not suppose to be able to access this method
            return false;
        }
        Optional<FROrder> optionalFROrder = orderRepository.findById(orderId);
        if (!optionalFROrder.isPresent()) {
            return false;
        }
        FROrder frOrder = optionalFROrder.get();
        frOrder.setShipper(account.getShipper());
        frOrder.setStatus(Fix.ORD_ASS.index);
        frOrder.setUpdateTime(time);
        orderRepository.save(frOrder);
        return true;
    }

    public Response<Integer> createOrder(Double longitude, Double latitude, String customerDescription, Integer[] proIdList, Integer[] quantityList) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Validator valid = new Validator();

        FRAccount account = methods.getUser();
        Response<Integer> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        if (account == null) {
            response.setResponse(Response.STATUS_FAIL, "Authentication fail.");
            return response;
        }
        if (proIdList == null || quantityList == null || proIdList.length != quantityList.length) {
            response.setResponse(Response.STATUS_FAIL, "Product list error");
            return response;
        }
        List<FRProduct> frProducts = new ArrayList<>();
        for (int i = 0; i < proIdList.length; i++) {
            Optional<FRProduct> optional = productRepository.findById(proIdList[i]);
            if (optional.isPresent()) {

            }
        }


        FROrder frOrder = new FROrder();
        frOrder.setAccount(account);

        frOrder.setLongitude(longitude);
        frOrder.setLatitude(latitude);

        frOrder.setCustomerDescription(valid.nullProof(customerDescription));
        frOrder.setCreateTime(time);
        frOrder.setNote("");
        frOrder.setStatus(Fix.ORD_NEW.index);


        orderRepository.save(frOrder);
        double total = 0d;
        for (int i = 0; i < proIdList.length; i++) {
            //Check quantityList[i] == null ? shipperEarn ?
            if (quantityList[i] >= 1) {
                Optional<FRProduct> optional = productRepository.findById(proIdList[i]);
                if (optional.isPresent()) {
                    FROrderDetail frOrderDetail = new FROrderDetail();
                    frOrderDetail.setOrder(frOrder);
                    FRProduct frProduct = optional.get();
                    frOrderDetail.setProduct(frProduct);
                    frOrderDetail.setUnitPrice(frProduct.getPrice());
                    frOrderDetail.setQuantity(quantityList[i]);
                    orderDetailRepository.save(frOrderDetail);
                    total += frProduct.getPrice() * quantityList[i];
                }
            }
        }
        frOrder.setTotalPrice(total);
        orderRepository.save(frOrder);


        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
        return response;

    }

    public Response memberCancelOrder(int orderId) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        FRAccount currentUser = methods.getUser();
        Response<Integer> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        Optional<FROrder> optionalFROrder = orderRepository.findById(orderId);
        if (!optionalFROrder.isPresent()) {
            response.setResponse(Response.STATUS_FAIL, "Cant find order " + orderId);
            return response;
        }
        FROrder frOrder = optionalFROrder.get();
        if (!frOrder.getAccount().getId().equals(currentUser.getId())) {
            response.setResponse(Response.STATUS_FAIL, "Not your order");
            return response;
        }

        if (frOrder.getStatus() == Fix.ORD_NEW.index) {
            frOrder.setDeleteTime(time);
            frOrder.setStatus(Fix.ORD_CXL.index);
            orderRepository.save(frOrder);
            response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
            return response;
        } else if (frOrder.getStatus() == Fix.ORD_ASS.index) {
            notifyShipper();
            frOrder.setDeleteTime(time);
            frOrder.setStatus(Fix.ORD_CXL.index);
            orderRepository.save(frOrder);
            response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
            return response;
        } else {
            response.setResponse(Response.STATUS_FAIL, "Order already on deli");
            return response;
        }
    }

    public void notifyShipper() {

    }

    public Response<List<MdlOrder>> getOrderList() {

        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        Response<List<MdlOrder>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        List<FROrder> frOrderList = orderRepository.findAll();
        List<MdlOrder> mdlOrderList = new ArrayList<>();
        for (FROrder frOrder : frOrderList) {
            mdlOrderList.add(orderBuilder.buildAdminTableRow(frOrder));
        }
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlOrderList);
        return response;

    }

    public Response<MdlOrder> getOrderDetailAdm(Integer orderId) {
        Methods methods = new Methods();
        Repo repo = new Repo();
        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        FROrder frOrder = repo.getOrder(orderId, orderRepository);
        if (frOrder == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find order");
            return response;
        }
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, orderBuilder.buildFull(frOrder, orderDetailRepository));
        return response;
    }

    public Response<MdlOrder> editOrderAdm(Integer orderId, MultipartFile buyerFace, MultipartFile bill, String buyerName, String buyerPhone, String shipperName, String shipperPhone, Integer status, Double latitude, Double longitude, Double totalPrice, Double shipperEarn, String note) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Validator valid = new Validator();
        Repo repo = new Repo();
        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        FRAccount currentUser = methods.getUser();

        FROrder frOrder = repo.getOrder(orderId, orderRepository);
        if (frOrder == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find order");
            return response;
        }
        if (buyerFace != null) {
            frOrder.setBuyerFace(methods.multipartToBytes(buyerFace));
        }
        if (bill != null) {
            frOrder.setBill(methods.multipartToBytes(bill));
        }
        FRAccount shipper = repo.getAccountByPhone(shipperPhone, accountRepository);
        if (shipper != null) {
            frOrder.setShipper(shipper.getShipper());
        }
        if (longitude != null) {
            frOrder.setLongitude(longitude);
        }
        if (latitude != null) {
            frOrder.setLatitude(latitude);
        }
        if (totalPrice != null) {
            frOrder.setTotalPrice(totalPrice);
        }
        if (shipperEarn != null) {
            frOrder.setShipperEarn(shipperEarn);
        }
        frOrder.setUpdateTime(time);
        frOrder.setNote(valid.nullProof(note));
        frOrder.setStatus(valid.checkUpdateStatus(frOrder.getStatus(), status, Fix.STO_STAT_LIST));
        frOrder.setEditor(currentUser);
        orderRepository.save(frOrder);

        MdlOrder mdlOrder = orderBuilder.buildFull(frOrder, orderDetailRepository);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlOrder);
        return response;


    }
}
