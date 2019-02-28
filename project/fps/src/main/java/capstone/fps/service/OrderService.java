package capstone.fps.service;

import capstone.fps.common.Fix;
import capstone.fps.common.Methods;
import capstone.fps.entity.*;
import capstone.fps.model.*;
import capstone.fps.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private DistrictRepo districtRepository;
    private OrderRepo orderRepository;
    private OrderDetailRepo orderDetailRepository;
    private ProductRepo productRepository;
    private StoreRepo storeRepository;
    private ShipperRepo shipperRepository;

    public OrderService(DistrictRepo districtRepository, OrderRepo orderRepository, OrderDetailRepo orderDetailRepository, ProductRepo productRepository) {
        this.districtRepository = districtRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productRepository = productRepository;
    }

//    private FRStatus getStatus(String statName) {
//        Optional<FRStatus> optional = orderStatusRepository.findByName(statName);
//        if (optional.isPresent()) {
//            return optional.get();
//        } else {
//            FRStatus status = new FRStatus();
//            status.setName(statName);
//            orderStatusRepository.save(status);
//            return orderStatusRepository.findByName(statName).get();
//        }
//    }

    private void setCreateTime(FROrder frOrder) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        frOrder.setBookTime(time);
        frOrder.setCreateTime(time);
        frOrder.setUpdateTime(time);
    }

    private void setUpdateTime(FROrder frOrder) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        frOrder.setUpdateTime(time);
    }

    private void setDeleteTime(FROrder frOrder) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        frOrder.setUpdateTime(time);
        frOrder.setDeleteTime(time);
    }

//    public FROrder createOrder(MdlOrderNew mdlOrder) {
//        FROrder frOrder = new FROrder();
//        FRAccount account = null;
//        if (account == null) {
//            return null;
//        }
//        frOrder.setAccount(account);
//        frOrder.setStatus(Fix.ORD_NEW);
//        setCreateTime(frOrder);
//        frOrder = fillInfo(mdlOrder, frOrder);
//        return frOrder;
//    }
//
//
//    private FROrder fillInfo(MdlOrderNew mdlOrder, FROrder frOrder) {
//        frOrder.setShipAddress(mdlOrder.getShipAddress());
//        Optional<FRDistrict> district = districtRepository.findById(mdlOrder.getFrDistrictId());
//        district.ifPresent(frOrder::setDistrict);
//        frOrder.setCustomerDescription(mdlOrder.getCustomerDescription());
//        Integer[] proList = mdlOrder.getProductList();
//        Integer[] quantityList = mdlOrder.getQuantityList();
//        if (proList == null || quantityList == null || proList.length != quantityList.length) {
//            return null;
//        }
//        orderRepository.save(frOrder);
//        double total = 0d;
//        for (int i = 0; i < proList.length; i++) {
//            //Check quantityList[i] == null ? shipperEarn ?
//            if (quantityList[i] >= 1) {
//                Optional<FRProduct> optional = productRepository.findById(proList[i]);
//                if (optional.isPresent()) {
//                    FROrderDetail frOrderDetail = new FROrderDetail();
//                    frOrderDetail.setOrder(frOrder);
//                    FRProduct frProduct = optional.get();
//                    frOrderDetail.setProduct(frProduct);
//                    frOrderDetail.setUnitPrice(frProduct.getPrice());
//                    frOrderDetail.setQuantity(quantityList[i]);
//                    orderDetailRepository.save(frOrderDetail);
//                    total += frProduct.getPrice() * quantityList[i];
//                }
//            }
//        }
//        frOrder.setTotalPrice(total);
//        orderRepository.save(frOrder);
//        return frOrder;
//    }

    public boolean cancelOrder(int orderId) {
        Optional<FROrder> optionalFROrder = orderRepository.findById(orderId);
        if (!optionalFROrder.isPresent()) {
            return true;
        }
        FROrder frOrder = optionalFROrder.get();
        //check cancelable
        if (Fix.ORD_NEW != frOrder.getStatus()) {
            return false;
        }
        setDeleteTime(frOrder);
        frOrder.setStatus(Fix.ORD_CXL);
        orderRepository.save(frOrder);
        return true;
    }

    public boolean assignOrder(int orderId) {
        Methods methods = new Methods();
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
        frOrder.setStatus(Fix.ORD_ASS);
        setUpdateTime(frOrder);
        orderRepository.save(frOrder);
        return true;
    }

    public FROrder createOrder(String shipAddress, Integer districtId, String customerDescription, Integer[] proList, Integer[] quantityList) {
        FROrder frOrder = new FROrder();
        Methods methods = new Methods();
        FRAccount account = methods.getUser();
        if (account == null) {
            return null;
        }
        frOrder.setAccount(account);
        frOrder.setStatus(Fix.ORD_NEW);
        setCreateTime(frOrder);

        frOrder.setShipAddress(shipAddress);
        Optional<FRDistrict> district = districtRepository.findById(districtId);
        district.ifPresent(frOrder::setDistrict);
        frOrder.setCustomerDescription(customerDescription);

        if (proList == null || quantityList == null || proList.length != quantityList.length) {
            return null;
        }
        orderRepository.save(frOrder);
        double total = 0d;
        for (int i = 0; i < proList.length; i++) {
            //Check quantityList[i] == null ? shipperEarn ?
            if (quantityList[i] >= 1) {
                Optional<FRProduct> optional = productRepository.findById(proList[i]);
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
        return frOrder;

    }


    public List<MdlOrderSimple> findall() {
        List<FROrder> frOrderList = orderRepository.findAll();
        List<MdlOrderSimple> orderList = new ArrayList<>();
        for (FROrder frOrder : frOrderList) {
            MdlOrderSimple mdlOrder = new MdlOrderSimple();
            mdlOrder.setId(frOrder.getId());
            mdlOrder.setBookTime(frOrder.getBookTime());
            mdlOrder.setCustomerName(frOrder.getAccount().getName());
            mdlOrder.setShipperName(frOrder.getShipper().getAccount().getName());
            mdlOrder.setTotalPrice(frOrder.getTotalPrice());
            mdlOrder.setStatus(frOrder.getStatus());
            orderList.add(mdlOrder);
        }
        return orderList;
    }

    public MdlOrderDetail getOrder(Integer id) {
        MdlOrderDetail orderDetail = new MdlOrderDetail();
        FROrder order = orderRepository.getOne(id);
        orderDetail.setId(order.getId());
        orderDetail.setBookTime(order.getBookTime());
        orderDetail.setTotalPrice(order.getTotalPrice());
        orderDetail.setStatus(order.getStatus());
        orderDetail.setShipAddress(order.getShipAddress());
        // get customer
        MdlAccountSimple customer = new MdlAccountSimple();
        customer.setId(order.getAccount().getId());
        customer.setName(order.getAccount().getName());
        orderDetail.setCustomer(customer);
        // get ordered products
        List<MdlOrderProductDetail> products = new ArrayList<MdlOrderProductDetail>();

        for (FROrderDetail frProduct : order.getOrderDetailCollection()) {
            MdlOrderProductDetail product = new MdlOrderProductDetail();
            product.setId(frProduct.getProduct().getId());
            product.setProductName(frProduct.getProduct().getProductName());
            product.setUnitPrice(frProduct.getUnitPrice());
            product.setQuantity(frProduct.getQuantity());
            products.add(product);
        }

        orderDetail.setProducts(products);

        return orderDetail;
    }
}
