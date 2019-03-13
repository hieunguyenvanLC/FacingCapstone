package capstone.fps.controller;

import capstone.fps.common.Fix;
import capstone.fps.model.Response;
import capstone.fps.model.account.MdlShipper;
import capstone.fps.model.account.MdlAdmAccAdmGet;
import capstone.fps.model.account.MdlAdmAccMemGet;
import capstone.fps.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Controller
public class AccountController extends AbstractController {

    private static final String API = Fix.MAP_API + "/account";

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(Fix.MAP_ANY + API)
    public String createAccountMember(String phoneNumber, String password, String fullName) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.createAccountMember(phoneNumber, password, fullName);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }


    @PostMapping(Fix.MAP_ADM + API + "/adm")
    public String createAccountAdmin(String username, String password, String fullName, String email, String natId, long natDate, long dob, String note) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.createAccountAdmin(username, password, fullName, email, natId, natDate, dob, note);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ADM + API + "/adm")
    public String getAccountAdminList() {
        Response<List<MdlAdmAccAdmGet>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            List<MdlAdmAccAdmGet> admList = accountService.getListAdmin();
            response.setResponse(admList.size(), Response.MESSAGE_SUCCESS, admList);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ADM + API + "/mem")
    public String getAccountMemberList() {
        Response<List<MdlAdmAccMemGet>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            List<MdlAdmAccMemGet> accList = accountService.getListMember();
            response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, accList);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    // Admin Web - Shipper
    @PostMapping(Fix.MAP_ADM + API + "/shp")
    public String createShipper(String phone, String password, String name, Integer sourceId, Long dob, String email, String note, Integer priceLevelId, MultipartFile userFace, String introduce, String natId, long natDate, String bikeRegId, long bikeRegDate, MultipartFile natFront, MultipartFile natBack, MultipartFile bikeRegFront, MultipartFile bikeRegBack) {
        Response<MdlShipper> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.createShipper(phone, password, name, sourceId, dob, email, note, priceLevelId, userFace, introduce, natId, natDate, bikeRegId, bikeRegDate, natFront, natBack, bikeRegFront, bikeRegBack);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PutMapping(Fix.MAP_ADM + API + "/shp")
    public String updateShipper(Integer accId, String phone, Double sumRevenue, String name, Integer sourceId, Long dob, String email, String note, Integer priceLevelId, MultipartFile userFace, String introduce, Integer extraPoint, Integer reportPoint, Integer status, String natId, Long natDate, String bikeRegId, Long bikeRegDate, MultipartFile natFront, MultipartFile natBack, MultipartFile bikeRegFront, MultipartFile bikeRegBack) {
        Response<MdlShipper> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.updateShipper(accId, phone, sumRevenue, name, sourceId, dob, email, note, priceLevelId, userFace, introduce, extraPoint, reportPoint, status, natId, natDate, bikeRegId, bikeRegDate, natFront, natBack, bikeRegFront, bikeRegBack);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }


    @GetMapping(Fix.MAP_ADM + API + "/shp")
    public String getShipperListAdm() {
        Response<List<MdlShipper>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.getShipperListAdm();
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ADM + API + "/shp/detail")
    public String getShipperDetailAdm(Integer accId) {
        Response<MdlShipper> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.getShipperDetailAdm(accId);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
    // Admin Web - Shipper - End

    @PutMapping(Fix.MAP_ADM + API + "/adm")
    public String editAccountAdmin(Integer accId, String fullName, String email, String natId, long natDate, long dob, String note) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.editAccountAdmin(accId, fullName, email, natId, natDate, dob, note);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PutMapping(Fix.MAP_ADM + API + "/mem")
    public String editAccountMember(Integer accId, String fullName, String email, long dob, String note) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.editAccountMember(accId, fullName, email, dob, note);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

//    @PutMapping(Fix.MAP_LOG + API)
//    public String updateProfile(@RequestParam String profileStr) {
//        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
//        try {
////            MdlAccEdt accEdt = gson.fromJson(profileStr, MdlAccEdt.class);
////            if (accountService.updateProfile(accEdt)) {
////                response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
//        }
//        return gson.toJson(response);
//    }

    @DeleteMapping(Fix.MAP_ADM + API)
    public String deactivateAccount(Integer accountId, String reason) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            if (accountService.banAccount(accountId, reason)) {
                response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PutMapping(Fix.MAP_ADM + API + "/activate")
    public String activateAccount(Integer accountId, String reason) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            if (accountService.activateAccount(accountId, reason)) {
                response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
}
