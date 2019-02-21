package capstone.fps.controller;

import capstone.fps.entity.FROrder;
import capstone.fps.model.MdlAccEdt;
import capstone.fps.model.Response;
import capstone.fps.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController extends AbstractController {

    private static final String API = "/api/order";
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(API)
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

    @GetMapping("/admin/order")
    public String showOrderList() {
        return "order";
    }

}
