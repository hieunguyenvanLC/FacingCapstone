package capstone.fps.controller;

import capstone.fps.common.Fix;
import capstone.fps.common.Methods;
import capstone.fps.model.MapFaceResult;
import capstone.fps.model.Response;
import capstone.fps.model.order.MdlOrder;
import capstone.fps.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class OrderController extends AbstractController {

    private static final String API = Fix.MAP_API + "/order";
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Web Admin - Order - Begin
    @GetMapping(Fix.MAP_ADM + API)
    public String getOrderListAdm() {
        Response<List<MdlOrder>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.getOrderListAdm();
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ADM + API + "/detail")
    public String getOrderDetailAdm(Integer orderId) {
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.getOrderDetailAdm(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PutMapping(Fix.MAP_ADM + API)
    public String editOrderAdm(Integer orderId, MultipartFile buyerFace, MultipartFile bill, Integer status, String note, String customerDescription, String address, Double latitude, Double longitude) {
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.editOrderAdm(gson, orderId, buyerFace, bill, status, note, customerDescription, address, latitude, longitude);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
    // Web Admin - Order - End


    // Mobile Member - Order History - Begin
    @GetMapping(Fix.MAP_MEM + API + "/history")
    public String getOrderListMem() {
        Response<List<MdlOrder>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.getOrderListMem();
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
    // Mobile Member - Order History - End


    // Mobile Member - Order Booking - Begin
    @PostMapping(Fix.MAP_MEM + API)
    public String createOrderMem(Double longitude, Double latitude, String customerDescription, String proList, double distance, String deviceToken, String buyerAddress) {
        Response<Integer> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.createOrder(longitude, latitude, customerDescription, proList, distance, deviceToken, buyerAddress);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_MEM + API + "/stat")
    public String getOrderStatusMem(Integer orderId) {
        Response<Integer> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.getOrderStatusMem(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_MEM + API + "/detail")
    public String getOrderDetailMem(Integer orderId) {
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.getOrderDetailMem(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @DeleteMapping(Fix.MAP_MEM + API)
    public String cancelOrderMem(int orderId, double longitude, double latitude) {
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.cancelOrderMem(orderId, longitude, latitude);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
    // Mobile Member - Order Booking - End


    // Mobile Member - Order Rating - Begin
    @PutMapping(Fix.MAP_MEM + API + "/rate")
    public String rateOrderMem(int orderId, int rating) {
        Response<Integer> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.rateOrder(orderId, rating);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
    // Mobile Member - Order Rating - End


    // Mobile Shipper - Order History - Begin
    @GetMapping(Fix.MAP_SHP + API + "/history")
    public String getOrderListShp() {
        Response<List<MdlOrder>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.getOrderListShp();
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_SHP + API + "/detail")
    public String getOrderDetailShp(Integer orderId) {
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.getOrderDetailMem(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
    // Mobile Shipper - Order History - End


    // Mobile Shipper - Queue - Begin
    @DeleteMapping(Fix.MAP_SHP + API)
    public String cancelQueue() {
        Response<Integer> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.stopQueue();
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_SHP + API)
    public String startQueue(double longitude, double latitude, String shipperToken) {
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.autoAssign(gson, longitude, latitude, shipperToken);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
    // Mobile Shipper - Queue - Begin


    // Mobile Shipper - Post Bill - Begin
    @PutMapping(Fix.MAP_ANY + API + "/bill")
    public String postBill(Integer orderId, String bill) {
        Response<String> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.postBill(orderId, bill);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
    // Mobile Shipper - Post Bill - End

    // Mobile Shipper - Checkout - Begin
    @PutMapping(Fix.MAP_ANY + API + "/checkout")
    public String checkout(Integer orderId, String face) {
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            Methods methods = new Methods();
            byte[] faceBytes = methods.base64ToBytes(face);
            response = orderService.checkout(gson, orderId, faceBytes);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PostMapping(Fix.MAP_ANY + "/python")
    public String receiveFaceResult(@RequestBody MapFaceResult map) {
        return orderService.receiveFaceResult(map.key, map.faceListStr);
    }
    // Mobile Shipper - Checkout - End


    @GetMapping(Fix.MAP_ANY + API + "/notify")
    public String testNotify(int orderId, String shipperToken) {
        Response<String> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.testNotify(gson, orderId, shipperToken);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
}
