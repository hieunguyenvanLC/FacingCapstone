package capstone.fps.controller;

import capstone.fps.common.Fix;
import capstone.fps.entity.FROrder;
import capstone.fps.model.Response;
import capstone.fps.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController extends AbstractController {

    private static final String API = "/api/order";
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(Fix.MAP_MEM + API)
    public String createOrder(Double longitude, Double latitude, String customerDescription, String productList, String quantityList) {
        Response<Integer> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            Integer[] productListObj = gson.fromJson(productList, Integer[].class);
            Integer[] quantityListObj = gson.fromJson(quantityList, Integer[].class);
            response = orderService.createOrder(longitude, latitude, customerDescription, productListObj, quantityListObj);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @DeleteMapping(Fix.MAP_MEM + API)
    public String memberCancelOrder(Integer orderId) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {

            response = orderService.memberCancelOrder(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

}
