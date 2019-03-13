package capstone.fps.controller;

import capstone.fps.common.Fix;
import capstone.fps.model.Response;
import capstone.fps.model.order.MdlOrder;
import capstone.fps.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class OrderController extends AbstractController {

    private static final String API = Fix.MAP_API + "/order";
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(Fix.MAP_MEM + API)
    public String createOrder(Double longitude, Double latitude, String customerDescription, String proList) {
        Response<Integer> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
//            Integer[] productListObj = gson.fromJson(productList, Integer[].class);
//            Integer[] quantityListObj = gson.fromJson(quantityList, Integer[].class);
//

//            String[] proStr = productList.split("x");
//            Integer[] productListObj = new Integer[proStr.length];
//            for (int i = 0; i < proStr.length; i++) {
//                productListObj[i] = Integer.parseInt(proStr[i]);
//            }
//
//            String[] quantityListStr = quantityList.split("x");
//            Integer[] quantityListObj = new Integer[quantityListStr.length];
//            for (int i = 0; i < quantityListStr.length; i++) {
//                quantityListObj[i] = Integer.parseInt(quantityListStr[i]);
//            }

            response = orderService.createOrder(longitude, latitude, customerDescription, proList);
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


    @GetMapping(Fix.MAP_ADM + API)
    public String getOrderList() {
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

}
