package nghia.firsttask.controller;


import nghia.firsttask.entity.Product;
import nghia.firsttask.model.Response;
import nghia.firsttask.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@CrossOrigin
public class ProductController extends AbstractController {

    public static final String API = "/api";

    //API Response code
    public static final int STATUS_CODE_SERVER_ERROR = -1;
    public static final int STATUS_CODE_FAIL = 0;
    public static final int STATUS_CODE_SUCCESS = 1;
    public static final int STATUS_CODE_NO_RESULT = 2;

    public static final String MESSAGE_SUCCESS = "Success!";
    public static final String MESSAGE_FAIL = "Fail!";
    public static final String MESSAGE_SERVER_ERROR = "Server error!";
    public static final String MESSAGE_NO_RESULT = "No result!";


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping(API + "/viewall")
    public String viewAllProduct() {
        Response<List<Product>> response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        try {
            List<Product> products = productService.findAll();
            response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS, products);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PostMapping(API + "/add")
    public String createProduct(@RequestParam String name, @RequestParam Double price) {
        Response response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        try {
            productService.createProduct(name, price);
            response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PostMapping(API + "/fix")
    public String updateProduct(@RequestParam Integer proId, @RequestParam String name, @RequestParam Double price) {
        Response response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        try {
            productService.updateProduct(proId, name, price);
            response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PostMapping(API + "/del")
    public String delProduct(@RequestParam int proId) {
        System.out.println("del " + proId);
        Response response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        try {
            productService.hideProduct(proId);
            response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

}
