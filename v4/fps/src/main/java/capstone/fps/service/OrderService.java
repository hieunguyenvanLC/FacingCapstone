package capstone.fps.service;

import capstone.fps.common.Fix;
import capstone.fps.common.Methods;
import capstone.fps.common.Repo;
import capstone.fps.common.Validator;
import capstone.fps.entity.*;
import capstone.fps.model.Response;
import capstone.fps.model.order.MdlDetailCreate;
import capstone.fps.model.order.MdlOrder;
import capstone.fps.model.order.MdlOrderBuilder;
import capstone.fps.model.order.MdlOrderDetail;
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

    public Response<Integer> createOrder(Double longitude, Double latitude, String customerDescription, String proListStr) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Validator valid = new Validator();
        Repo repo = new Repo();
        FRAccount currentUser = methods.getUser();
        Response<Integer> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        if (currentUser == null) {
            response.setResponse(Response.STATUS_FAIL, "Authentication fail.");
            return response;
        }
        proListStr = valid.checkOrderProList(proListStr);
        if (proListStr == null) {
            response.setResponse(Response.STATUS_FAIL, "Product list error");
            return response;
        }

        String[] proList = proListStr.split("n");
        List<MdlDetailCreate> detailList = new ArrayList<>();
        double totalPrice = 0d;
        for (String pro : proList) {
            String[] detail = pro.split("x");
            int proId = Integer.parseInt(detail[0]);
            int quantity = Integer.parseInt(detail[1]);
            FRProduct frProduct = repo.getProduct(proId, productRepository);
            if (frProduct == null) {
                response.setResponse(Response.STATUS_FAIL, "Cant find proId " + proId);
                return response;
            }
            if (quantity <= 0) {
                response.setResponse(Response.STATUS_FAIL, "proId " + proId + " has quantity " + quantity);
                return response;
            }
            detailList.add(new MdlDetailCreate(frProduct, quantity));
            totalPrice += frProduct.getPrice() * quantity;
        }
        FRStore store = detailList.get(0).getFrProduct().getStore();
        FROrder frOrder = new FROrder();

        frOrder.setAccount(currentUser);
        frOrder.setShipper(null);
        frOrder.setBuyerFace(null);
        frOrder.setBill(null);
        frOrder.setOrderCode(null);
        frOrder.setTotalPrice(totalPrice);
        frOrder.setBookTime(time);
        frOrder.setReceiveTime(null);
        frOrder.setShipperEarn(methods.caculateShpEarn(longitude, latitude, store.getLongitude(), store.getLatitude()));
        frOrder.setShipAddress(null);
        frOrder.setDistrict(null);
        frOrder.setLongitude(longitude);
        frOrder.setLatitude(latitude);
        frOrder.setCustomerDescription(valid.nullProof(customerDescription));
        frOrder.setCreateTime(time);
        frOrder.setUpdateTime(null);
        frOrder.setDeleteTime(null);
        frOrder.setNote("");
        frOrder.setStatus(Fix.ORD_NEW.index);
        frOrder.setEditor(null);
        orderRepository.save(frOrder);

        for (MdlDetailCreate detail : detailList) {
            FROrderDetail frOrderDetail = new FROrderDetail();
            FRProduct frProduct = detail.getFrProduct();
            frOrderDetail.setOrder(frOrder);
            frOrderDetail.setProduct(frProduct);
            frOrderDetail.setUnitPrice(frProduct.getPrice());
            frOrderDetail.setQuantity(detail.getQuantity());
            orderDetailRepository.save(frOrderDetail);
        }
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, frOrder.getId());
        return response;
    }

//    public Response<Integer> createOrder(Double longitude, Double latitude, String customerDescription, Integer[] proIdList, Integer[] quantityList) {
//        Methods methods = new Methods();
//        long time = methods.getTimeNow();
//        Validator valid = new Validator();
//        Repo repo = new Repo();
//        FRAccount account = methods.getUser();
//        Response<Integer> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
//        if (account == null) {
//            response.setResponse(Response.STATUS_FAIL, "Authentication fail.");
//            return response;
//        }
//        if (proIdList == null || quantityList == null || proIdList.length != quantityList.length) {
//            response.setResponse(Response.STATUS_FAIL, "Product list error");
//            return response;
//        }
//        List<FRProduct> frProducts = new ArrayList<>();
//        for (int i = 0; i < proIdList.length; i++) {
//            FRProduct frProduct = repo.getProduct(proIdList[i], productRepository);
//            if (frProduct == null) {
//                response.setResponse(Response.STATUS_FAIL, "Cant find proId " + proIdList[i]);
//                return response;
//            }
//            if (quantityList[i] <= 0) {
//                response.setResponse(Response.STATUS_FAIL, "proId " + proIdList[i] + " has quantity " + quantityList[i]);
//                return response;
//            }
//            frProducts.add(frProduct);
//        }
//        FROrder frOrder = new FROrder();
//        frOrder.setAccount(account);
//        frOrder.setLongitude(longitude);
//        frOrder.setLatitude(latitude);
//        frOrder.setCustomerDescription(valid.nullProof(customerDescription));
//        frOrder.setCreateTime(time);
//        frOrder.setNote("");
//        frOrder.setStatus(Fix.ORD_NEW.index);
//        orderRepository.save(frOrder);
//        double total = 0d;
//        for (int i = 0; i < proIdList.length; i++) {
//            FROrderDetail frOrderDetail = new FROrderDetail();
//            FRProduct frProduct = frProducts.get(i);
//            frOrderDetail.setOrder(frOrder);
//            frOrderDetail.setProduct(frProduct);
//            frOrderDetail.setUnitPrice(frProduct.getPrice());
//            frOrderDetail.setQuantity(quantityList[i]);
//            orderDetailRepository.save(frOrderDetail);
//            total += frProduct.getPrice() * quantityList[i];
//        }
//
//        frOrder.setTotalPrice(total);
//        orderRepository.save(frOrder);
//
//        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, frOrder.getId());
//        return response;
//
//    }

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

    public Response<MdlOrder> editOrderAdm(Integer orderId, MultipartFile buyerFace, MultipartFile bill, String buyerName, String buyerPhone, String shipperName, String shipperPhone, Integer status, Double latitude, Double longitude, Double totalPrice, Double shipperEarn, String customerDescription, String note) {
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
        if (customerDescription != null) {
            frOrder.setCustomerDescription(valid.nullProof(customerDescription));
        }
        if (note != null) {
            frOrder.setNote(valid.nullProof(note));
        }
        frOrder.setUpdateTime(time);
        frOrder.setStatus(valid.checkUpdateStatus(frOrder.getStatus(), status, Fix.STO_STAT_LIST));
        frOrder.setEditor(currentUser);
        orderRepository.save(frOrder);

        MdlOrder mdlOrder = orderBuilder.buildFull(frOrder, orderDetailRepository);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlOrder);
        return response;


    }
}