package capstone.fps.controller;

import capstone.fps.common.Fix;
import capstone.fps.entity.FRProduct;
import capstone.fps.entity.FRStore;
import capstone.fps.model.MdlProBest;
import capstone.fps.model.MdlStoreReadNear;
import capstone.fps.model.Response;
import capstone.fps.service.StoreService;
import capstone.fps.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class StoreController extends AbstractController {

    private StoreService storeService;
    private ProductService productService;
    private static final String API = Fix.MAP_API + "store";

    public StoreController(StoreService storeService, ProductService productService) {
        this.storeService = storeService;
        this.productService = productService;
    }

    @GetMapping(Fix.MAP_ANY + API + "s")
    public String showListStore() {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            List<MdlStoreReadNear> stores=storeService.findAll();
            response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, stores);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponse(Response.STATUS_NO_RESULT, Response.MESSAGE_NO_RESULT);
        }
        return gson.toJson(response);
    }


    @PostMapping(Fix.MAP_ADM + API)
    public String createStore(String storeName, String address, Integer distId, Double longitude, Double latitude, String note) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            if (storeService.createStore(storeName, address, distId, longitude, latitude, note)) {
                response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @DeleteMapping(Fix.MAP_ADM + API)
    public String deactivateStore(Integer storeId, String reason) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            if (storeService.deactivateStore(storeId, reason)) {
                response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }


}
