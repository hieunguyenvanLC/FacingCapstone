package capstone.fps.controller;

import capstone.fps.common.Fix;
import capstone.fps.entity.FROrder;
import capstone.fps.model.Response;
import capstone.fps.model.order.MdlOrderDetail;
import capstone.fps.model.order.MdlOrderSimple;
import capstone.fps.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController extends AbstractController {

    private static final String API = "/api/order";
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(Fix.MAP_MEM + API)
    public String createAccount(String shipAddress, Integer districtId, String customerDescription, String productList, String quantityList) {
        Response<Integer> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            Integer[] productListObj = gson.fromJson(productList, Integer[].class);
            Integer[] quantityListObj = gson.fromJson(quantityList, Integer[].class);
            FROrder frOrder = orderService.createOrder(shipAddress, districtId, customerDescription, productListObj, quantityListObj);
            int id = frOrder.getId();
            response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, id);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }


    @GetMapping(Fix.MAP_ANY + API + "s")
    public String showOrderList() {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            List<MdlOrderSimple> frOrders = orderService.findall();
            response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, frOrders);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponse(Response.STATUS_NO_RESULT, Response.MESSAGE_NO_RESULT);
        }
        return gson.toJson(response);
    }
    @GetMapping(Fix.MAP_ANY + API)
    public String getOrder(@RequestParam("id") Integer id) {
        Response response = new Response<MdlOrderDetail>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            MdlOrderDetail order = orderService.getOrder(id);
            response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, order);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponse(Response.STATUS_NO_RESULT, Response.MESSAGE_NO_RESULT);
        }
        return gson.toJson(response);
    }


    @PostMapping(Fix.MAP_ADM + API + "/cancel") //    /any/api/order/cancel
    public String cancelOrder(@RequestParam("id") Integer orderId, @RequestParam("userId") Integer userId) {
        // @RequestParam("id") Integer orderId, @RequestParam("userId") Integer userId
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            boolean check = orderService.cancelOrder(orderId, userId);
//            List<MdlOrderSimple> frOrders = orderService.findall();
            response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, check);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponse(Response.STATUS_NO_RESULT, Response.MESSAGE_NO_RESULT);
        }

        return gson.toJson(response);
    }

}
