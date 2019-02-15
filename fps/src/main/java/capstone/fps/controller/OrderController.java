package capstone.fps.controller;

import capstone.fps.entity.FROrder;
import capstone.fps.model.MdlOrderNew;
import capstone.fps.model.Response;
import capstone.fps.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController extends AbstractController {

    private static final String API = "/api/order";
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(API)
    public String createAccount(@RequestParam String dataStr) {
        Response<Integer> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            MdlOrderNew mdlOrder = gson.fromJson(dataStr, MdlOrderNew.class);
            FROrder frOrder = orderService.createOrder(mdlOrder);
            response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, frOrder.getId());
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

}
