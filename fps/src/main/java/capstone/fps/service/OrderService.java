package capstone.fps.service;

import capstone.fps.common.*;
import capstone.fps.entity.*;
import capstone.fps.model.AppData;
import capstone.fps.model.Response;
import capstone.fps.model.order.MdlDetailCreate;
import capstone.fps.model.order.MdlOrder;
import capstone.fps.model.order.MdlOrderBuilder;
import capstone.fps.model.ordermatch.Delta;
import capstone.fps.model.ordermatch.OrderMap;
import capstone.fps.model.ordermatch.OrderStat;
import capstone.fps.repository.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private OrderRepo orderRepository;
    private OrderDetailRepo orderDetailRepository;
    private ProductRepo productRepository;
    private AccountRepo accountRepository;
    private PaymentInfoRepo paymentInfoRepo;
    private ReceiveMemberRepo receiveMemberRepo;
    private ShipperRepo shipperRepo;
    private OrderMap orderMap;
    private TransactionRepo transactionRepo;
//    private final ShipperWait shipperWait;


    @Autowired
    public OrderService(OrderRepo orderRepository, OrderDetailRepo orderDetailRepository, ProductRepo productRepository, AccountRepo accountRepository, PaymentInfoRepo paymentInfoRepo, ReceiveMemberRepo receiveMemberRepo, ShipperRepo shipperRepo, OrderMap orderMap, TransactionRepo transactionRepo) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productRepository = productRepository;
        this.accountRepository = accountRepository;
        this.paymentInfoRepo = paymentInfoRepo;
        this.receiveMemberRepo = receiveMemberRepo;
        this.shipperRepo = shipperRepo;
        this.orderMap = orderMap;
        this.transactionRepo = transactionRepo;
//        this.shipperWait = shipperWait;
    }


//    public boolean cancelOrderAdmin(int orderId) {
//        Methods methods = new Methods();
//        long time = methods.getTimeNow();
//        Optional<FROrder> optionalFROrder = orderRepository.findById(orderId);
//        if (!optionalFROrder.isPresent()) {
//            return true;
//        }
//        FROrder frOrder = optionalFROrder.get();
//        //check cancelable
//        if (Fix.ORD_NEW.index != frOrder.getStatus()) {
//            return false;
//        }
//        frOrder.setDeleteTime(time);
//        frOrder.setStatus(Fix.ORD_CXL.index);
//        orderRepository.save(frOrder);
//        return true;
//    }

//    public boolean assignOrder(int orderId) {
//        Methods methods = new Methods();
//        long time = methods.getTimeNow();
//        FRAccount account = methods.getUser();
//        if (account.getShipper() == null) {
//            // Something is wrong: none shipper is not suppose to be able to access this method
//            return false;
//        }
//        Optional<FROrder> optionalFROrder = orderRepository.findById(orderId);
//        if (!optionalFROrder.isPresent()) {
//            return false;
//        }
//        FROrder frOrder = optionalFROrder.get();
//        frOrder.setShipper(account.getShipper());
//        frOrder.setStatus(Fix.ORD_ASS.index);
//        frOrder.setUpdateTime(time);
//        orderRepository.save(frOrder);
//        return true;
//    }

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


    // Web Admin - Order - Begin
    public Response<List<MdlOrder>> getOrderListAdm() {

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
        Repo repo = new Repo();
        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        FROrder frOrder = repo.getOrder(orderId, orderRepository);
        if (frOrder == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find order");
            return response;
        }
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, orderBuilder.buildDetailWthImg(frOrder, orderDetailRepository));
        return response;
    }

    public Response<MdlOrder> editOrderAdm(Gson gson, Integer orderId, MultipartFile buyerFace, MultipartFile bill, Integer status, String note, String customerDescription, String address, Double latitude, Double longitude) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Validator valid = new Validator();
        Repo repo = new Repo();
        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        FRAccount currentUser = methods.getUser(accountRepository);

        FROrder frOrder = repo.getOrder(orderId, orderRepository);
        if (frOrder == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find order");
            return response;
        }
        if (bill != null) {
            frOrder.setBill(methods.multipartToBytes(bill));
        }
//        FRAccount shipper = repo.getAccountByPhone(shipperPhone, accountRepository);
//        if (shipper != null) {
//            frOrder.setShipper(shipper.getShipper());
//        }
        if (address != null) {
            frOrder.setShipAddress(address);
        }
        if (longitude != null) {
            frOrder.setLongitude(longitude);
        }
        if (latitude != null) {
            frOrder.setLatitude(latitude);
        }
//        if (totalPrice != null) {
//            frOrder.setTotalPrice(totalPrice);
//        }
//        if (shipperEarn != null) {
//            frOrder.setShipperEarn(shipperEarn);
//        }
        if (customerDescription != null) {
            frOrder.setCustomerDescription(valid.nullProof(customerDescription));
        }
        if (note != null) {
            frOrder.setNote(valid.nullProof(note));
        }
        frOrder.setUpdateTime(time);
        frOrder.setStatus(valid.checkUpdateStatus(frOrder.getStatus(), status, Fix.ORD_STAT_LIST));
        frOrder.setEditor(currentUser);
        orderRepository.save(frOrder);
        if (buyerFace != null) {
            return checkout(gson, orderId, methods.multipartToBytes(buyerFace));
        }
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, orderBuilder.buildFull(frOrder, orderDetailRepository));
        return response;
    }

    public Response<MdlOrder> cancelOrderAdm(Integer orderId) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Repo repo = new Repo();
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        FRAccount currentUser = methods.getUser(accountRepository);
        FROrder frOrder = repo.getOrder(orderId, orderRepository);
        if (frOrder == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find order");
            return response;
        }
        frOrder.setUpdateTime(time);
        frOrder.setStatus(Fix.ORD_CXL.index);
        frOrder.setEditor(currentUser);
        orderRepository.save(frOrder);

        FRAccount buyer = frOrder.getAccount();
        buyer.setCurrentOrder(0);
        accountRepository.save(buyer);
        FRShipper frShipper = frOrder.getShipper();
        if (frShipper != null) {
            FRAccount shipper = frShipper.getAccount();
            shipper.setCurrentOrder(0);
            accountRepository.save(shipper);
        }
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
        return response;
    }
    // Web Admin - Order - Begin


    // Mobile Member - Order History - Begin
    public Response<List<MdlOrder>> getOrderListMem() {
        Methods methods = new Methods();
        FRAccount currentUser = methods.getUser(accountRepository);
        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        Response<List<MdlOrder>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        List<FROrder> frOrderList = orderRepository.findAllByAccount(currentUser);
        List<MdlOrder> mdlOrderList = new ArrayList<>();
        for (FROrder frOrder : frOrderList) {
            mdlOrderList.add(orderBuilder.buildHistoryBuyer(frOrder));
        }
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlOrderList);
        return response;
    }

    public Response<MdlOrder> getOrderDetailMem(Integer orderId) {
        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        Repo repo = new Repo();
        FROrder frOrder = repo.getOrder(orderId, orderRepository);
        if (frOrder == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find order");
            return response;
        }
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, orderBuilder.buildDetailWthImg(frOrder, orderDetailRepository));
        return response;
    }


    public Response<JsonObject> getFaceCheckout(Integer orderId) {
        Methods methods = new Methods();
        Response<JsonObject> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        Repo repo = new Repo();
        FROrder frOrder = repo.getOrder(orderId, orderRepository);
        if (frOrder == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find order");
            return response;
        }
        JsonObject faceObj = new JsonObject();
        faceObj.addProperty("face", methods.bytesToBase64(frOrder.getBuyerFace()));
        faceObj.addProperty("name", frOrder.getReceiverName());
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, faceObj);
        return response;
    }
    // Mobile Member - Order History - End


    // Mobile Member - Order Booking - Begin
    public Response<Integer> createOrder(Double longitude, Double latitude, String customerDescription, String proListStr, double distance, String deviceToken, String buyerAddress) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Validator valid = new Validator();
        Repo repo = new Repo();
        FRAccount currentUser = methods.getUser(accountRepository);
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
        FROrder frOrder = new FROrder();

        frOrder.setAccount(currentUser);
        frOrder.setShipper(null);
        frOrder.setBuyerFace(null);
        frOrder.setBill(null);
        frOrder.setOrderCode(new BigInteger(currentUser.getId() + "A" + time, 11).toString(36).toUpperCase());
        frOrder.setTotalPrice(totalPrice);
        frOrder.setAssignTime(null);
        frOrder.setBuyTime(null);
        frOrder.setReceiveTime(null);
        frOrder.setShipperEarn(methods.calculateShpEarn(distance));
        frOrder.setPriceLevel(null);
        frOrder.setShipAddress(buyerAddress);
        frOrder.setLongitude(longitude);
        frOrder.setLatitude(latitude);
        frOrder.setCustomerDescription(valid.nullProof(customerDescription));
        frOrder.setCreateTime(time);
        frOrder.setUpdateTime(null);
        frOrder.setDeleteTime(null);
        frOrder.setNote("");
        frOrder.setStatus(Fix.ORD_NEW.index);
        frOrder.setEditor(null);
        frOrder.setBuyerToken(deviceToken);
        frOrder.setShipperToken(null);
        frOrder.setRating(null);
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


        FRStore frStore = detailList.get(0).getFrProduct().getStore();
        frOrder = repo.getOrder(frOrder.getId(), orderRepository);
        orderMap.addOrder(frOrder, frStore.getLongitude(), frStore.getLatitude());


        currentUser.setCurrentOrder(frOrder.getId());
        accountRepository.save(currentUser);

        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, frOrder.getId());
        return response;
    }

    public Response<Integer> getOrderStatusMem(Integer orderId) {
        Repo repo = new Repo();
        Response<Integer> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        FROrder frOrder = repo.getOrder(orderId, orderRepository);
        if (frOrder == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find order");
            return response;
        }

        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, frOrder.getStatus());
        return response;
    }

    public Response<MdlOrder> cancelOrderMem(int orderId, double longitude, double latitude) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        Repo repo = new Repo();


        FROrder frOrder = repo.getOrder(orderId, orderRepository);
        if (frOrder == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find order");
            return response;
        }
        if (frOrder.getStatus() != Fix.ORD_NEW.index) {
            response.setResponse(Response.STATUS_FAIL, "Order is assigned");
            return response;
        }
        int col = orderMap.convertLon(longitude);
        int row = orderMap.convertLat(latitude);
        List<OrderStat> statList = orderMap.getNode(col, row).getStatList();
        OrderStat myOrderStat = null;
        for (OrderStat orderStat : statList) {
            if (orderId == orderStat.getFrOrder().getId()) {
                orderStat.setCancel(true);
                myOrderStat = orderStat;
                statList.remove(orderStat);
                break;
            }
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (myOrderStat.getLockBy() == 0) {
            frOrder.setDeleteTime(time);
            frOrder.setStatus(Fix.ORD_CXL.index);
            orderRepository.save(frOrder);

            FRAccount frAccount = methods.getUser(accountRepository);
            frAccount.setCurrentOrder(0);
            accountRepository.save(frAccount);
        }

        frOrder = repo.getOrder(orderId, orderRepository);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, orderBuilder.buildFull(frOrder, orderDetailRepository));
        return response;
    }
    // Mobile Member - Order Booking - End


    // Mobile Member - Order Rating - Begin
    public Response<Integer> rateOrder(int orderId, int rating) {
        Response<Integer> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        FROrder frOrder = orderRepository.findById(orderId).orElse(null);
        if (frOrder == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find order");
            return response;
        }
        if (frOrder.getRating() != null) {
            response.setResponse(Response.STATUS_FAIL, "This order had already been rated");
            return response;
        }
        frOrder.setRating(rating);
        FRShipper frShipper = frOrder.getShipper();
        frShipper.setRating((frShipper.getRating() * frShipper.getRatingCount() + rating) / (frShipper.getRatingCount() + 1));
        frShipper.setRatingCount(frShipper.getRatingCount() + 1);
        orderRepository.save(frOrder);
        shipperRepo.save(frShipper);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
        return response;
    }
    // Mobile Member - Order Rating - End


    // Mobile Shipper - Order History - Begin
    public Response<List<MdlOrder>> getOrderListShp() {
        Methods methods = new Methods();
        FRAccount currentUser = methods.getUser(accountRepository);
        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        Response<List<MdlOrder>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        List<FROrder> frOrderList = orderRepository.findAllByShipper(currentUser.getShipper());
        List<MdlOrder> mdlOrderList = new ArrayList<>();
        for (FROrder frOrder : frOrderList) {
            mdlOrderList.add(orderBuilder.buildHistoryBuyer(frOrder));
        }
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlOrderList);
        return response;
    }
    // Mobile Shipper - Order History - End


    // Mobile Shipper - Order Matching - Begin
    public Response<MdlOrder> autoAssign(Gson gson, double longitude, double latitude, String shipperToken, HttpServletRequest request) {
        int col = orderMap.convertLon(longitude);
        int row = orderMap.convertLat(latitude);
        Methods methods = new Methods();
        long waitTime = methods.getTimeNow() + (3 * 60 * 1000);
        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        FRAccount currentUser = methods.getUser(accountRepository);
        int accId = currentUser.getId();

        HttpSession session = request.getSession(true);
        session.setAttribute("cancelWait", false);

        while (methods.getTimeNow() < waitTime && !((boolean) session.getAttribute("cancelWait"))) {
            for (ArrayList<Delta> layer : orderMap.getLayers()) {
                for (Delta delta : layer) {
                    int colNode = col + delta.col;
                    int rowNode = row + delta.row;
                    List<OrderStat> node = orderMap.getNode(colNode, rowNode).getStatList();
                    if (node.size() > 0) {
                        for (OrderStat order : node) {
                            if (!order.isCancel() && order.getLockBy() == 0) {
                                order.setLockBy(accId);
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (!order.isCancel() && order.getLockBy() == accId) {
                                    orderMap.removeOrder(order, colNode, rowNode);
                                    FROrder frOrder = order.getFrOrder();
                                    frOrder.setShipper(currentUser.getShipper());
                                    frOrder.setStatus(Fix.ORD_ASS.index);
                                    frOrder.setPriceLevel(currentUser.getShipper().getPriceLevel().getPrice());
                                    frOrder.setShipperToken(shipperToken);
                                    orderRepository.save(frOrder);

                                    currentUser.setCurrentOrder(frOrder.getId());
                                    accountRepository.save(currentUser);

                                    notifyBuyer(frOrder);
                                    notifyShipper(frOrder, shipperToken);
                                    MdlOrder mdlOrder = orderBuilder.buildDetailWthImg(frOrder, orderDetailRepository);
                                    response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlOrder);
                                    return response;
                                }

                            }
                        }
                    }
                }
            }
//            for (int layerNo = 0; layerNo <= 4; layerNo++) {
//                ArrayList<Delta> layer = orderMap.getLayer(layerNo);
////                Collections.shuffle(layer);
//                for (Delta delta : layer) {
//                    int colNode = col + delta.col;
//                    int rowNode = row + delta.row;
//                    List<OrderStat> node = orderMap.getNode(colNode, rowNode).getStatList();
//                    if (node.size() > 0) {
//                        for (OrderStat order : node) {
//                            if (!order.isCancel() && order.getLockBy() == 0) {
//
//                                order.setLockBy(accId);
//                                try {
//                                    Thread.sleep(500);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                                if (!order.isCancel() && order.getLockBy() == accId) {
//                                    orderMap.removeOrder(order, colNode, rowNode);
//                                    FROrder frOrder = order.getFrOrder();
//                                    frOrder.setShipper(currentUser.getShipper());
//                                    frOrder.setStatus(Fix.ORD_ASS.index);
//                                    orderRepository.save(frOrder);
//                                    notifyBuyer(gson, frOrder);
//                                    notifyShipper(frOrder, shipperToken);
//                                    MdlOrder mdlOrder = orderBuilder.buildFull(frOrder, orderDetailRepository);
//                                    response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlOrder);
//                                    return response;
//                                }
//
//                            }
//                        }
//                    }
//                }
//            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if ((boolean) session.getAttribute("cancelWait")) {
            response.setResponse(Response.STATUS_SUCCESS, "cancel");
            return response;
        } else {
            response.setResponse(Response.STATUS_SUCCESS, "time out");
            return response;
        }
    }

    private String notifyBuyer(FROrder frOrder) {
        Methods methods = new Methods();
        JsonParser parser = new JsonParser();
        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Authorization", "key=" + Fix.FCM_KEY);

        JsonObject notification = new JsonObject();
        notification.addProperty("title", "FPS");
        notification.addProperty("body", "Your order has been taken by shipper " + frOrder.getShipper().getAccount().getName());
        notification.addProperty("sound", "default");
        notification.addProperty("click_action", "FCM_PLUGIN_ACTIVITY");
        notification.addProperty("icon", "fcm_push_icon");

        JsonObject data = new JsonObject();
//        data.add("order", parser.parse(gson.toJson(orderBuilder.buildFull(frOrder, orderDetailRepository))).getAsJsonObject());
        data.addProperty("orderId", frOrder.getId());

        JsonObject body = new JsonObject();
        body.add("notification", notification);
        body.add("data", data);
        body.addProperty("priority", "high");
        body.addProperty("to", frOrder.getBuyerToken());
        body.addProperty("restricted_package_name", "");

        return methods.sendHttpRequest(Fix.FCM_URL, header, body);
    }


    private String notifyShipper(FROrder frOrder, String shipperToken) {
        Methods methods = new Methods();
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Authorization", "key=" + Fix.FCM_KEY);

        JsonObject notification = new JsonObject();
        notification.addProperty("title", "FPS Shipper");
        notification.addProperty("body", "You has taken order" + frOrder.getOrderCode());
        notification.addProperty("sound", "default");
        notification.addProperty("click_action", "FCM_PLUGIN_ACTIVITY");
        notification.addProperty("icon", "fcm_push_icon");

        JsonObject data = new JsonObject();

        JsonObject body = new JsonObject();
        body.add("notification", notification);
        body.add("data", data);
        body.addProperty("priority", "high");
        body.addProperty("to", shipperToken);
        body.addProperty("restricted_package_name", "");

        return methods.sendHttpRequest(Fix.FCM_URL, header, body);
    }

    public Response<Integer> stopQueue(HttpServletRequest request) {
        Response<Integer> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        HttpSession session = request.getSession(true);
        session.setAttribute("cancelWait", true);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
        return response;
    }
    // Mobile Shipper - Order Matching - End


    // Mobile Shipper - Post Bill - Begin
    public Response<String> cancelOrderShp() {
        Methods methods = new Methods();
        Repo repo = new Repo();
        FRAccount shipper = methods.getUser(accountRepository);
        Response<String> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        int orderId = shipper.getCurrentOrder();
        if (orderId <= 0) {
            response.setResponse(Response.STATUS_FAIL, "no current order");
            return response;
        }
        FROrder frOrder = repo.getOrder(orderId, orderRepository);
        if (frOrder == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find order");
            return response;
        }
        frOrder.setDeleteTime(methods.getTimeNow());
        frOrder.setStatus(Fix.ORD_CXL.index);
        orderRepository.save(frOrder);

        FRAccount buyer = frOrder.getAccount();
        buyer.setCurrentOrder(0);
        accountRepository.save(buyer);
        shipper.setCurrentOrder(0);
        accountRepository.save(shipper);
        FRShipper frShipper = shipperRepo.findById(shipper.getShipper().getId()).get();
        int newRatingCount = frShipper.getRatingCount() + 1;
        frShipper.setRating(frShipper.getRating() * frShipper.getRatingCount() / newRatingCount);
        frShipper.setRatingCount(newRatingCount);
        shipperRepo.save(frShipper);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
        return response;
    }

    public Response<String> postBill(Integer orderId, String bill) {
        Methods methods = new Methods();
        Repo repo = new Repo();
        Response<String> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        FROrder frOrder = repo.getOrder(orderId, orderRepository);
        if (frOrder == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find order");
            return response;
        }
        frOrder.setBill(methods.base64ToBytes(bill));
        frOrder.setStatus(Fix.ORD_BUY.index);
        orderRepository.save(frOrder);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
        return response;
    }
    // Mobile Shipper - Post Bill - End


    // Mobile Shipper - Order Checkout - Begin
    public Response<MdlOrder> checkout(Gson gson, Integer orderId, byte[] faceBytes) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Repo repo = new Repo();
        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        FROrder frOrder = repo.getOrder(orderId, orderRepository);
        if (frOrder == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find order");
            return response;
        }
        // test face here
        String key = frOrder.getId() + "" + methods.getTimeNow();
        Map<String, String> faceResult = AppData.getFaceResult();
        faceResult.remove(key);
        System.out.println(faceRecognise(faceBytes, key));


        Long maxWait = methods.getTimeNow() + 1 * 60 * 1000;
        while (faceResult.get(key) == null && methods.getTimeNow() < maxWait) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (methods.getTimeNow() >= maxWait) {
            response.setResponse(Response.STATUS_FAIL, "Python connection error");
            return response;
        }

        String faceListStr = faceResult.get(key);
        faceResult.remove(key);
        FRAccount buyer = frOrder.getAccount();
        FRReceiveMember frReceiveMember = checkFaceResult(frOrder, faceListStr, buyer);
        if (frReceiveMember == null) {
            response.setResponse(Response.STATUS_FAIL, "Face not match");
            return response;
        }

        frOrder.setReceiverName(frReceiveMember.getName());

        // order
        frOrder.setStatus(Fix.ORD_COM.index);
        frOrder.setBuyerFace(faceBytes);
        frOrder.setReceiveTime(time);
        // shipper
        FRShipper frShipper = frOrder.getShipper();
        double revenue = frOrder.getPriceLevel() * frOrder.getShipperEarn();
        frShipper.setSumRevenue(frShipper.getSumRevenue() + revenue);
        frShipper.setOrderCount(frShipper.getOrderCount() + 1);
        FRPriceLevel nextLevel = frShipper.getPriceLevel().getNextLevel();
        if (nextLevel != null) {
            if (frShipper.getOrderCount() >= nextLevel.getOrderReq() && frShipper.getRating() >= nextLevel.getRateReq()) {
                frShipper.setPriceLevel(nextLevel);
            }
        }
        FRAccount shipperAcc = frShipper.getAccount();
        shipperAcc.setCurrentOrder(0);
        // buyer
        buyer.setCurrentOrder(0);
        buyer.setWallet(buyer.getWallet() - frOrder.getTotalPrice() - frOrder.getShipperEarn());

        orderRepository.save(frOrder);
        System.out.println("save frOrder");
        shipperRepo.save(frShipper);
        System.out.println("save frAccount");
        accountRepository.save(buyer);
        accountRepository.save(shipperAcc);
        System.out.println("save frAccount");
        notifyBuyerCheckout(frOrder);
        notifyShipperCheckout(frOrder);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, orderBuilder.buildFull(frOrder, orderDetailRepository));
        return response;

    }

    /*
    public Response<MdlOrder> checkoutOld(Gson gson, Integer orderId, byte[] faceBytes) {
        Methods methods = new Methods();
        Repo repo = new Repo();
        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        FROrder frOrder = repo.getOrder(orderId, orderRepository);
        if (frOrder == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find order");
            return response;
        }
        // test face here
        String key = frOrder.getId() + "" + methods.getTimeNow();
        Map<String, String> faceResult = AppData.getFaceResult();
        faceResult.remove(key);
        System.out.println(faceRecognise(faceBytes, key));

        Long maxWait = methods.getTimeNow() + 1 * 60 * 1000;
        while (faceResult.get(key) == null && methods.getTimeNow() < maxWait) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (methods.getTimeNow() >= maxWait) {
            response.setResponse(Response.STATUS_FAIL, "Python connection error");
            return response;
        }
        String faceListStr = faceResult.get(key);
        faceResult.remove(key);
        FRAccount buyer = frOrder.getAccount();
        List<FRPaymentInformation> informationList = paymentInfoRepo.findAllByAccount(buyer);
        FRPaymentInformation frPayInfo = informationList.get(0);
        String payUsername = frPayInfo.getUsername();
        String payPassword = frPayInfo.getPassword();
        String description = "Account " + buyer.getPhone() + " pay for order " + frOrder.getOrderCode();
        double price = (frOrder.getTotalPrice() + frOrder.getShipperEarn()) / Fix.USD;
        String priceStr = String.format("%.2f", price);

        String payId = handlingFaceResult(frOrder, faceListStr, gson, buyer, payUsername, payPassword, priceStr, description);
        System.out.println("PayPal resp " + payId);
        if ("fail".equals(payId)) {
            response.setResponse(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
            return response;
        } else {
            long time = methods.getTimeNow();
            FRTransaction frTransaction = new FRTransaction();
            frTransaction.setPayId(payId);
            frTransaction.setAmount(price);
            frTransaction.setTime(time);
            frTransaction.setPaymentInformation(frPayInfo);
            transactionRepo.save(frTransaction);
            System.out.println("save frTransaction");
            frOrder.setStatus(Fix.ORD_COM.index);
            frOrder.setBuyerFace(faceBytes);
            frOrder.setReceiveTime(time);
            orderRepository.save(frOrder);
            System.out.println("save frOrder");
            FRShipper frShipper = frOrder.getShipper();
            double revenue = frOrder.getPriceLevel() * frOrder.getShipperEarn();
            frShipper.setSumRevenue(frShipper.getSumRevenue() + revenue);
            frShipper.setOrderCount(frShipper.getOrderCount() + 1);
            FRPriceLevel nextLevel = frShipper.getPriceLevel().getNextLevel();
            if (nextLevel != null) {
                if (frShipper.getOrderCount() >= nextLevel.getOrderReq() && frShipper.getRating() >= nextLevel.getRateReq()) {
                    frShipper.setPriceLevel(nextLevel);
                }
            }
            shipperRepo.save(frShipper);
            buyer.setCurrentOrder(0);
            FRAccount shipperAcc = frShipper.getAccount();
            shipperAcc.setCurrentOrder(0);
            accountRepository.save(buyer);
            accountRepository.save(shipperAcc);
            System.out.println("save frShipper");
            notifyBuyerCheckout(frOrder);
            notifyShipperCheckout(frOrder);
            response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, orderBuilder.buildFull(frOrder, orderDetailRepository));
            return response;
        }
    }
    */

    /* Receive HttpReq from Python */
    public String receiveFaceResult(String key, String faceListStr) {
        AppData.getFaceResult().put(key, faceListStr);
        return "ok";
    }

    /* Compare Member List with buyer */
    private FRReceiveMember checkFaceResult(FROrder frOrder, String memListStr, FRAccount buyer) {
        Repo repo = new Repo();
        System.out.println("face str -" + memListStr + "-");
        memListStr = memListStr.replace("fps", "");
        String[] strList = memListStr.split("\\|");
        int buyerId = buyer.getId();
        for (String idStr : strList) {
            int revMemId;
            try {
                revMemId = Integer.parseInt(idStr);
            } catch (NumberFormatException ex) {
                return null;
            }
            FRReceiveMember receiveMember = repo.getReceiveMember(revMemId, receiveMemberRepo);
            if (receiveMember != null) {
                if (buyerId == receiveMember.getAccount().getId()) {
                    return receiveMember;
                }
            }
        }
        return null;
    }

    /*
    private String handlingFaceResult(FROrder frOrder, String memListStr, Gson gson, FRAccount buyer, String payUsername, String payPassword, String priceStr, String description) {
        Repo repo = new Repo();
        System.out.println("face str -" + memListStr + "-");
        memListStr = memListStr.replace("fps", "");
        String[] strList = memListStr.split("\\|");
        int buyerId = buyer.getId();
        for (String idStr : strList) {
            int revMemId;
            try {
                revMemId = Integer.parseInt(idStr);
            } catch (NumberFormatException ex) {
                return "fail";
            }
            FRReceiveMember receiveMember = repo.getReceiveMember(revMemId, receiveMemberRepo);
            if (receiveMember != null) {
                if (buyerId == receiveMember.getAccount().getId()) {
                    frOrder.setReceiverName(receiveMember.getName());
                    return callEmulatorServer(gson, payUsername, payPassword, priceStr, description);
                }
            }
        }
        return "fail";
    }
    */

    /* Send HttpReq to PayPal Emulator */
    private String callEmulatorServer(Gson gson, String payUsername, String payPassword, String priceStr, String description) {
        Methods methods = new Methods();
        URL url;
        HttpURLConnection urlConnection;
        String uri = Fix.PAY_SERVER_URL + Fix.MAP_API + "/pay/input";
        System.out.println(uri);
        System.out.println(payUsername);
        System.out.println(priceStr);
        System.out.println(description);
        String method = "POST";
        String[] paramName = {"username", "password", "price", "description"};
        String[] paramValue = {payUsername, payPassword, priceStr, description};
        try {
            url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            StringBuilder urlParameters = new StringBuilder(paramName[0] + "=" + paramValue[0]);
            for (int i = 1; i < paramName.length; i++) {
                urlParameters.append("&").append(paramName[i]).append("=").append(paramValue[i]);
            }
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(urlParameters.toString());
            wr.flush();
            wr.close();

            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String readStream = methods.readStream(urlConnection.getInputStream());
                System.out.println("response " + readStream);
                return gson.fromJson(readStream, String.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    private CommandPrompt faceRecognise(byte[] faceBytes, String key) {
        Methods methods = new Methods();
        String folderName = Fix.TEST_FACE_FOLDER + "fpsTestFile";
        String jpgName = "p" + methods.getTimeNow() + "." + "jpg";
        CommandPrompt commandPrompt = null;
        File directory = new File(folderName);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File jpgFile = new File(folderName + "/" + jpgName);
        ByteArrayInputStream bis = new ByteArrayInputStream(faceBytes);
        try {
            BufferedImage bufferedImage = ImageIO.read(bis);
            ImageIO.write(bufferedImage, Fix.DEF_IMG_TYPE, jpgFile);

            commandPrompt = new CommandPrompt();
            String cutAndRecenter = "docker run -v /Users/nguyenvanhieu/Project/CapstoneProject/docker:/docker -e PYTHONPATH=$PYTHONPATH:/docker -i fps-image python3 /docker/face_recognize_system/preprocess.py --input-dir /docker/data/testFace --output-dir /docker/output/test --crop-dim 180";
            commandPrompt.execute(cutAndRecenter);
            String testFaceAI = "docker run -v /Users/nguyenvanhieu/Project/CapstoneProject/docker:/docker -e PYTHONPATH=$PYTHONPATH:/docker -i fps-image python3 /docker/face_recognize_system/train_classifier.py --input-dir /docker/output/test --model-path /docker/etc/20170511-185253/20170511-185253.pb --classifier-path /docker/output/classifier.pkl --num-threads 5 --num-epochs 25 --min-num-images-per-class 5 --key-gen " + key;
            commandPrompt.execute(testFaceAI);
            //delete folder generated by Python
            methods.deleteDirectoryWalkTree(Fix.CROP_TEST_FACE_FOLDER + "fpsTestFile");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // delete folder generated by Java
        try {
            methods.deleteDirectoryWalkTree(folderName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commandPrompt;
    }

    public Response<String> notifyBuyerCheckout(FROrder frOrder) {
        Methods methods = new Methods();
        Response<String> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        JsonObject notification = new JsonObject();
        notification.addProperty("title", "FPS");
        notification.addProperty("body", "Order checkout successfully");
        notification.addProperty("sound", "default");
        notification.addProperty("click_action", "FCM_PLUGIN_ACTIVITY");
        notification.addProperty("icon", "fcm_push_icon");

        JsonObject data = new JsonObject();
        data.addProperty("orderId", frOrder.getId());
        JsonObject body = new JsonObject();
        body.add("notification", notification);
        body.add("data", data);
        body.addProperty("priority", "high");
        body.addProperty("to", frOrder.getBuyerToken());
        body.addProperty("restricted_package_name", "");

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Authorization", "key=" + Fix.FCM_KEY);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, methods.sendHttpRequest(Fix.FCM_URL, header, body));
        return response;
    }

    public Response<String> notifyShipperCheckout(FROrder frOrder) {
        Methods methods = new Methods();
        Response<String> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        JsonObject notification = new JsonObject();
        notification.addProperty("title", "FPS Shipper");
        notification.addProperty("body", "Order checkout successfully");
        notification.addProperty("sound", "default");
        notification.addProperty("click_action", "FCM_PLUGIN_ACTIVITY");
        notification.addProperty("icon", "fcm_push_icon");

        JsonObject data = new JsonObject();
        data.addProperty("orderId", frOrder.getId());
        JsonObject body = new JsonObject();
        body.add("notification", notification);
        body.add("data", data);
        body.addProperty("priority", "high");
        body.addProperty("to", frOrder.getShipperToken());
        body.addProperty("restricted_package_name", "");

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Authorization", "key=" + Fix.FCM_KEY);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, methods.sendHttpRequest(Fix.FCM_URL, header, body));
        return response;
    }
    // Mobile Shipper - Order Checkout - End


    public Response<JsonObject> getCurrentOrder() {
        Methods methods = new Methods();
        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        Response<JsonObject> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        FRAccount currentUser = methods.getUser(accountRepository);
        Integer currentOrder = currentUser.getCurrentOrder();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("orderId", currentOrder);
//        jsonObject.addProperty("orderStatus", orderRepository.findById(currentOrder).orElse(null).getStatus());
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, jsonObject);
        return response;
    }

    public Response<String> testNotify(Gson gson, int orderId, String deviceToken) {
        Methods methods = new Methods();
        JsonParser parser = new JsonParser();
        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        Response<String> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        FROrder frOrder = orderRepository.findById(orderId).orElse(null);

        JsonObject notification = new JsonObject();
        notification.addProperty("title", "FPS");
        notification.addProperty("body", "This is test notification");
        notification.addProperty("sound", "default");
        notification.addProperty("click_action", "FCM_PLUGIN_ACTIVITY");
        notification.addProperty("icon", "fcm_push_icon");

        JsonObject data = new JsonObject();
        data.add("order", parser.parse(gson.toJson(orderBuilder.buildFull(frOrder, orderDetailRepository))).getAsJsonObject());

        JsonObject body = new JsonObject();
        body.add("notification", notification);
        body.add("data", data);
        body.addProperty("priority", "high");
        body.addProperty("to", deviceToken);
        body.addProperty("restricted_package_name", "");

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Authorization", "key=" + Fix.FCM_KEY);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, methods.sendHttpRequest(Fix.FCM_URL, header, body));
        return response;
    }


}
