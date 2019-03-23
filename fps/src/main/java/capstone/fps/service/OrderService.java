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
import capstone.fps.model.ordermatch.Delta;
import capstone.fps.model.ordermatch.OrderMap;
import capstone.fps.model.ordermatch.OrderStat;
import capstone.fps.model.ordermatch.ShipperWait;
import capstone.fps.repository.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private DistrictRepo districtRepository;
    private OrderRepo orderRepository;
    private OrderDetailRepo orderDetailRepository;
    private ProductRepo productRepository;
    private AccountRepo accountRepository;
    private PaymentInfoRepo paymentInfoRepo;

    @Autowired
    private OrderMap orderMap;
    @Autowired
    private ShipperWait shipperWait;


    public OrderService(DistrictRepo districtRepository, OrderRepo orderRepository, OrderDetailRepo orderDetailRepository, ProductRepo productRepository, AccountRepo accountRepository, PaymentInfoRepo paymentInfoRepo) {
        this.districtRepository = districtRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productRepository = productRepository;
        this.accountRepository = accountRepository;

        this.paymentInfoRepo = paymentInfoRepo;
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
    // Web Admin - Order - Begin


    // Mobile Member - Order Booking - Begin
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

        FROrder frOrder = new FROrder();

        frOrder.setAccount(currentUser);
        frOrder.setShipper(null);
        frOrder.setBuyerFace(null);
        frOrder.setBill(null);
        frOrder.setOrderCode(null);
        frOrder.setTotalPrice(totalPrice);
        frOrder.setBookTime(time);
        frOrder.setReceiveTime(null);
        frOrder.setShipperEarn(null);
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

        orderMap.addOrder(frOrder, orderDetailRepository);

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

    public Response<MdlOrder> getOrderDetailMem(Integer orderId) {
        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        Repo repo = new Repo();
        FROrder frOrder = repo.getOrder(orderId, orderRepository);
        if (frOrder == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find order");
            return response;
        }
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, orderBuilder.buildFull(frOrder, orderDetailRepository));
        return response;
    }


    public Response<MdlOrder> cancelOrderMem(int orderId, int col, int row) {
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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (myOrderStat.getLockBy() == 0) {
            frOrder.setDeleteTime(time);
            frOrder.setStatus(Fix.ORD_CXL.index);
            orderRepository.save(frOrder);
        }

        frOrder = repo.getOrder(orderId, orderRepository);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, orderBuilder.buildFull(frOrder, orderDetailRepository));
        return response;
    }

    // Mobile Member - Order Booking - End


    // Mobile Shipper - Order Matching - Begin
    public Response<MdlOrder> autoAssign(double longitude, double latitude) {
        int col = orderMap.convertLon(longitude);
        int row = orderMap.convertLat(latitude);
        Methods methods = new Methods();
        long waitTime = methods.getTimeNow() + (3 * 60 * 1000);
        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        FRAccount currentUser = methods.getUser();
        int accId = currentUser.getId();

        shipperWait.setCancel(false);

        while (methods.getTimeNow() < waitTime && !shipperWait.isCancel()) {
            for (int layerNo = 0; layerNo <= 4; layerNo++) {
                ArrayList<Delta> layer = orderMap.getLayer(layerNo);
//                Collections.shuffle(layer);
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
                                    frOrder.setShipperEarn(methods.caculateShpEarn(frOrder.getLongitude(), frOrder.getLatitude(), order.getStoreLon(), order.getStoreLat(), longitude, latitude));
                                    frOrder.setStatus(Fix.ORD_ASS.index);
                                    orderRepository.save(frOrder);
                                    MdlOrder mdlOrder = orderBuilder.buildFull(frOrder, orderDetailRepository);
                                    response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlOrder);
                                    return response;
                                }

                            }
                        }
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (shipperWait.isCancel()) {
            response.setResponse(Response.STATUS_SUCCESS, "cancel");
            return response;
        } else {
            response.setResponse(Response.STATUS_SUCCESS, "time out");
            return response;
        }
    }

    public Response<Integer> stopQueue() {
        Response<Integer> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        shipperWait.setCancel(true);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
        return response;
    }
    // Mobile Shipper - Order Matching - End


    // Mobile Shipper - Order Checkout - Begin
    public Response<String> checkout(Gson gson, Integer orderId, String face) {
        Methods methods = new Methods();
        Repo repo = new Repo();
        Response<String> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        FROrder frOrder = repo.getOrder(orderId, orderRepository);
        if (frOrder == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find order");
            return response;
        }

        // test face here


        final String uri = Fix.PAY_SERVER_URL + Fix.MAP_API + "/pay/input";

        FRAccount buyer = frOrder.getAccount();
        List<FRPaymentInformation> informationList = paymentInfoRepo.findAllByAccount(frOrder.getAccount());

        FRPaymentInformation frPayInfo = informationList.get(0);

        String payUsername = frPayInfo.getUsername();
        String payPassword = frPayInfo.getPassword();
        String description = "Account " + buyer.getPhone() + " pay for order " + frOrder.getId();
        double price = (frOrder.getTotalPrice() + frOrder.getShipperEarn()) / Fix.usd;
        String priceStr = String.format("%.2f", price);


//        Map<String, String> params = new HashMap<>();
//        params.put("username", payUsername);
//        params.put("password", payPassword);
//        params.put("price", priceStr);
//        params.put("description", description);


        URL url;
        HttpURLConnection urlConnection;


        String method = "POST";
        final String[] paramName = {"username", "password", "price", "description"};
        final String[] paramValue = {payUsername, payPassword, priceStr, description};

        try {
            url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);


            if (paramName.length > 0) {
                String urlParameters = paramName[0] + "=" + paramValue[0];
                for (int i = 1; i < paramName.length; i++) {
                    urlParameters = urlParameters + "&" + paramName[i] + "=" + paramValue[i];
                }
                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();
            }

            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                String result = gson.fromJson(methods.readStream(urlConnection.getInputStream()), String.class);
                response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, result);
                return response;


            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        RestTemplate restTemplate = new RestTemplate();
//        String result = gson.fromJson(restTemplate.getForObject(uri, String.class, params), String.class);


//        shipperWait.setCancel(true);
        response.setResponse(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        return response;
    }
    // Mobile Shipper - Order Checkout - End
}
