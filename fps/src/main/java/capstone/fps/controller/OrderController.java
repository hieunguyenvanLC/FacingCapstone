package capstone.fps.controller;

import capstone.fps.common.Fix;
import capstone.fps.model.MapFaceResult;
import capstone.fps.model.Response;
import capstone.fps.model.order.MdlOrder;
import capstone.fps.service.OrderService;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
            response = orderService.getOrderList();
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
    public String editOrderAdm(Integer orderId, MultipartFile buyerFace, MultipartFile bill, String buyerName, String buyerPhone, String shipperName, String shipperPhone, Integer status, Double latitude, Double longitude, Double totalPrice, Double shipperEarn, String customerDescription, String note) {
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.editOrderAdm(orderId, buyerFace, bill, buyerName, buyerPhone, shipperName, shipperPhone, status, latitude, longitude, totalPrice, shipperEarn, customerDescription, note);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
    // Web Admin - Order - End


    // Mobile Member - Order Booking - Begin
    @PostMapping(Fix.MAP_MEM + API)
    public String createOrderMem(Double longitude, Double latitude, String customerDescription, String proList, double distance, String deviceToken) {
        Response<Integer> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.createOrder(longitude, latitude, customerDescription, proList, distance, deviceToken);
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
    public String cancelOrderMem(Integer orderId, Integer col, Integer row) {
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.cancelOrderMem(orderId, col, row);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
    // Mobile Member - Order Booking - End


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


    // Mobile Shipper - Checkout - Begin
    @PutMapping(Fix.MAP_ANY + API + "/checkout")
    public String checkout(Integer orderId, String face) {
        Response<String> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.checkout(gson, orderId, face);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
    // Mobile Shipper - Checkout - End


//    @PostMapping(Fix.MAP_ANY + "/paypal")
//    public String receiveFaceTestResult(@RequestBody MapFaceResult map, HttpServletRequest request) {
//        orderService.receiveFaceTestResult(map.getRep(), request);
//
//        return "";
//
//    }

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
