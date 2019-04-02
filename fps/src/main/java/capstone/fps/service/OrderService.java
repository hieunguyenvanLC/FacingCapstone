package capstone.fps.service;

import capstone.fps.common.*;
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
import com.google.gson.JsonObject;
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
    private ReceiveMemberRepo receiveMemberRepo;
    private OrderMap orderMap;
    private TransactionRepo transactionRepo;
    @Autowired
    private ShipperWait shipperWait;


    public OrderService(DistrictRepo districtRepository, OrderRepo orderRepository, OrderDetailRepo orderDetailRepository, ProductRepo productRepository, AccountRepo accountRepository, PaymentInfoRepo paymentInfoRepo, ReceiveMemberRepo receiveMemberRepo, OrderMap orderMap, TransactionRepo transactionRepo) {
        this.districtRepository = districtRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productRepository = productRepository;
        this.accountRepository = accountRepository;

        this.paymentInfoRepo = paymentInfoRepo;
        this.receiveMemberRepo = receiveMemberRepo;
        this.orderMap = orderMap;
        this.transactionRepo = transactionRepo;
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
    public Response<Integer> createOrder(Double longitude, Double latitude, String customerDescription, String proListStr, double distance, String deviceToken) {
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
        frOrder.setShipperEarn(methods.calculateShpEarn(distance));
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
        frOrder.setBuyerToken(deviceToken);
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
    public Response<MdlOrder> autoAssign(Gson gson, double longitude, double latitude, String shipperToken) {
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
                                    frOrder.setStatus(Fix.ORD_ASS.index);
                                    orderRepository.save(frOrder);
                                    notifyBuyer(gson, frOrder);
                                    notifyShipper(frOrder, shipperToken);
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

    private String notifyBuyer(Gson gson, FROrder frOrder) {
        Methods methods = new Methods();
        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        JsonObject notification = new JsonObject();
        notification.addProperty("title", "FPS");
        notification.addProperty("body", "Your order has been taken by shipper " + frOrder.getShipper().getAccount().getName());
        notification.addProperty("sound", "default");
        notification.addProperty("click_action", "FCM_PLUGIN_ACTIVITY");
        notification.addProperty("icon", "fcm_push_icon");
        JsonObject body = new JsonObject();
        body.add("notification", notification);
        body.addProperty("data", gson.toJson(orderBuilder.buildFull(frOrder, orderDetailRepository)));
        body.addProperty("priority", "high");
        body.addProperty("to", frOrder.getBuyerToken());
        body.addProperty("restricted_package_name", "");
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "key=" + Fix.FCM_KEY);
        return methods.sendHttpRequest(Fix.FCM_URL, header, body);
    }


    private String notifyShipper( FROrder frOrder, String shipperToken) {
        Methods methods = new Methods();
        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        JsonObject notification = new JsonObject();
        notification.addProperty("title", "FPS");
        notification.addProperty("body", "You has taken order" + frOrder.getId());
        notification.addProperty("sound", "default");
        notification.addProperty("click_action", "FCM_PLUGIN_ACTIVITY");
        notification.addProperty("icon", "fcm_push_icon");
        JsonObject body = new JsonObject();
        body.add("notification", notification);
        body.addProperty("data", "");
        body.addProperty("priority", "high");
        body.addProperty("to", shipperToken);
        body.addProperty("restricted_package_name", "");
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "key=" + Fix.FCM_KEY);
        return methods.sendHttpRequest(Fix.FCM_URL, header, body);
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

        // face to byte[]
        byte[] faceBytes = methods.base64ToBytes(face);
        // test face here
        String key = methods.getTimeNow() + "";
        CommandPrompt commandPrompt = faceRecognise(faceBytes, key);

        List<String> result = commandPrompt.getResult();
        String memListStr = null;
        for (int i = 0; i < result.size() && memListStr == null; i++) {
            String line = result.get(i);
            if (line.contains(Fix.FACE_RESULT_TAG + key)) {
                memListStr = line.replace(Fix.FACE_RESULT_TAG + key, "").trim();
            }
        }
        FRAccount buyer = frOrder.getAccount();
        List<FRPaymentInformation> informationList = paymentInfoRepo.findAllByAccount(buyer);
        FRPaymentInformation frPayInfo = informationList.get(0);
        String payUsername = frPayInfo.getUsername();
        String payPassword = frPayInfo.getPassword();
        String description = "Account " + buyer.getPhone() + " pay for order " + frOrder.getId();
        double price = (frOrder.getTotalPrice() + frOrder.getShipperEarn()) / Fix.USD;
        String priceStr = String.format("%.2f", price);


        String payId = handlingFaceResult(memListStr, gson, buyer, payUsername, payPassword, priceStr, description);
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

            frOrder.setStatus(Fix.ORD_COM.index);
            frOrder.setBuyerFace(faceBytes);
            frOrder.setReceiveTime(time);
            orderRepository.save(frOrder);
            response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
            return response;
        }
    }

    // Compare Member List with buyer
    private String handlingFaceResult(String memListStr, Gson gson, FRAccount buyer, String payUsername, String payPassword, String priceStr, String description) {
        Repo repo = new Repo();
        memListStr = memListStr.replace("fps", "");
        String[] strList = memListStr.split("|");
        for (String idStr : strList) {
            int revMemId = Integer.parseInt(idStr);
            FRReceiveMember receiveMember = repo.getReceiveMember(revMemId, receiveMemberRepo);
            if (receiveMember != null) {
                if (receiveMember.getAccount().getId() == buyer.getId()) {
                    return callEmulatorServer(gson, payUsername, payPassword, priceStr, description);
                }
            }
        }
        return "fail";
    }

    // Send HttpReq to PayPal Emulator
    private String callEmulatorServer(Gson gson, String payUsername, String payPassword, String priceStr, String description) {
        Methods methods = new Methods();
        URL url;
        HttpURLConnection urlConnection;
        String uri = Fix.PAY_SERVER_URL + Fix.MAP_API + "/pay/input";
        String method = "POST";
        String[] paramName = {"username", "password", "price", "description"};
        String[] paramValue = {payUsername, payPassword, priceStr, description};
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
                return gson.fromJson(methods.readStream(urlConnection.getInputStream()), String.class);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    public CommandPrompt faceRecognise(byte[] faceBytes, String key) {
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
            String testFaceAI = "docker run -v /Users/nguyenvanhieu/Project/CapstoneProject/docker:/docker -e PYTHONPATH=$PYTHONPATH:/docker -i fps-image python3 /docker/face_recognize_system/train_classifier.py --input-dir /docker/output/test --model-path /docker/etc/20170511-185253/20170511-185253.pb --classifier-path /docker/output/classifier.pkl --num-threads 5 --num-epochs 5 --min-num-images-per-class 5 --key-gen " + key;
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
    // Mobile Shipper - Order Checkout - End


}
