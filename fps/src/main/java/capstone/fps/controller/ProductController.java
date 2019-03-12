package capstone.fps.controller;

import capstone.fps.common.Fix;
import capstone.fps.model.product.MdlMemProBest;
import capstone.fps.model.Response;
import capstone.fps.model.product.MdlProduct;
import capstone.fps.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ProductController extends AbstractController {

    private static final String API = Fix.MAP_API + "/product";
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(Fix.MAP_ANY + API + "/best5")
    public String getProBest5() {
        Response<List<MdlMemProBest>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            List<MdlMemProBest> mdlProBests = productService.getBest5();
            response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlProBests);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PostMapping(Fix.MAP_ANY + Fix.MAP_API + "/check")
    public String getCheck(String col, String row) {
//        String col = "2";
//        String row = "werw";
        System.out.println(col + " ok " + row);
        String s = col + " & " + row;
        return s;
    }


//    @DeleteMapping(Fix.MAP_ADM + API)
//    public String deactivateAccount(Integer accountId, String reason) {
//        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
//        try {
//            if (productService.(accountId, reason)) {
//                response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
//        }
//        return gson.toJson(response);
//    }

    @GetMapping(Fix.MAP_ADM + API)
    public String getProList(Integer storeId) {
        Response<List<MdlProduct>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            List<MdlProduct> mdlProList = productService.getProList(storeId);
            response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlProList);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }


    @PostMapping(Fix.MAP_ADM + API)
    public String createProduct(String proName, Integer storeId, Double price, MultipartFile proImg, String description, String note) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = productService.createProduct(proName, storeId, price, proImg, description, note);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }


    @PutMapping(Fix.MAP_ADM + API)
    public String updateProduct(String proName, Integer proId, Double price, MultipartFile proImg, String description, String note, Integer status) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = productService.updateProduct(proId, proName, price, proImg, description, note, status);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }


}
