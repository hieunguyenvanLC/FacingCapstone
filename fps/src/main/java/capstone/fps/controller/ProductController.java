package capstone.fps.controller;

import capstone.fps.model.MdlProBest;
import capstone.fps.model.Response;
import capstone.fps.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController extends AbstractController {

    private static final String API = "/api/product/";
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/pro5best")
    public String getProBest5() {
        Response<List<MdlProBest>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            List<MdlProBest> mdlProBests = productService.getBest5();
            response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlProBests);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
}
