package capstone.fps.controller;

import capstone.fps.common.Fix;
import capstone.fps.model.Response;
import capstone.fps.model.store.MdlAdmStoAll;
import capstone.fps.model.store.MdlAdmStoOne;
import capstone.fps.service.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class StoreController extends AbstractController {

    private StoreService storeService;
    private static final String API = Fix.MAP_API + "store";

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping(Fix.MAP_ADM + API)
    public String getStoreList() {
        Response<List<MdlAdmStoAll>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            List<MdlAdmStoAll> storeList = storeService.getStoreList();
            response.setResponse(storeList.size(), Response.MESSAGE_SUCCESS, storeList);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }


    @GetMapping(Fix.MAP_ADM + API + "/detail")
    public String getStoreDetailAdm(Integer storeId) {
        Response<MdlAdmStoOne> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = storeService.getStoreDetailAdm(storeId);

        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PostMapping(Fix.MAP_ADM + API)
    public String createStore(String storeName, String address, Integer distId, Double longitude, Double latitude, MultipartFile storeImg, String note) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = storeService.createStore(storeName, address, distId, longitude, latitude,storeImg, note);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PutMapping(Fix.MAP_ADM + API)
    public String editStore(Integer storeId, String storeName, String phone, String address, Integer distId, Double longitude, Double latitude, String note) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = storeService.updateStore(storeId, storeName, phone, address, distId, longitude, latitude, note);
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
