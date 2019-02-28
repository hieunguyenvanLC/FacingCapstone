package capstone.fps.controller;

import capstone.fps.common.Fix;
import capstone.fps.model.product.MdlMemProBest;
import capstone.fps.model.Response;
import capstone.fps.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController extends AbstractController {

    private static final String API = Fix.MAP_API + "product";
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

}
