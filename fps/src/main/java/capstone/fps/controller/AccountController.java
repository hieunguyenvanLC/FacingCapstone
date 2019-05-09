package capstone.fps.controller;

import capstone.fps.common.Fix;
import capstone.fps.model.Response;
import capstone.fps.model.account.MdlAdmin;
import capstone.fps.model.account.MdlMember;
import capstone.fps.model.account.MdlShipper;
import capstone.fps.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class AccountController extends AbstractController {

    private static final String API = Fix.MAP_API + "/account";

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


//    @DeleteMapping(Fix.MAP_ADM + API)
//    public String deactivateAccount(Integer accountId, String reason) {
//        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
//        try {
//            if (accountService.banAccount(accountId, reason)) {
//                response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
//        }
//        return gson.toJson(response);
//    }
//
//    @PutMapping(Fix.MAP_ADM + API + "/activate")
//    public String activateAccount(Integer accountId, String reason) {
//        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
//        try {
//            if (accountService.activateAccount(accountId, reason)) {
//                response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
//        }
//        return gson.toJson(response);
//    }

    // Admin Web - Member - Begin
    @GetMapping(Fix.MAP_ADM + API + "/mem/list")
    public String getMemberListAdm() {
        Response<List<MdlMember>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.getMemberList();
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ADM + API + "/mem/detail")
    public String getMemberDetailAdm(int accId) {
        Response<MdlMember> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.getMemberDetailAdm(accId);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PutMapping(Fix.MAP_ADM + API + "/mem")
    public String updateMemberAdm(int accId, String name, String email, Long dob, String note, Integer status) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.updateMemberAdm(accId, name, email, dob, note, status);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
    // Admin Web - Member - End


    // Admin Web - Shipper - Begin
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
    public String updateShipper(int accId, String phone, String password, Double sumRevenue, String name, Integer sourceId, Long dob, String email, String note, Integer priceLevelId, MultipartFile userFace, String introduce, Integer extraPoint, Integer reportPoint, Integer status, String natId, Long natDate, String bikeRegId, Long bikeRegDate, MultipartFile natFront, MultipartFile natBack, MultipartFile bikeRegFront, MultipartFile bikeRegBack) {
        Response<MdlShipper> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.updateShipper(accId, phone, password, sumRevenue, name, sourceId, dob, email, note, priceLevelId, userFace, introduce, extraPoint, reportPoint, status, natId, natDate, bikeRegId, bikeRegDate, natFront, natBack, bikeRegFront, bikeRegBack);
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
    public String getShipperDetailAdm(int accId) {
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


    // Admin Web - Admin - Begin
    @GetMapping(Fix.MAP_ADM + API + "/adm")
    public String getAdminList() {
        Response<List<MdlAdmin>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.getListAdmin();
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PostMapping(Fix.MAP_ADM + API + "/adm")
    public String createAdmin(String username, String password, String name, String email, String natId, Long natDate, Long dob, String note) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.createAccountAdmin(username, password, name, email, natId, natDate, dob, note);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PutMapping(Fix.MAP_ADM + API + "/adm")
    public String updateAdmin(int accId, String name, String email, String natId, Long natDate, Long dob, String note) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.editAccountAdmin(accId, name, email, natId, natDate, dob, note);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ADM + API + "/profile")
    public String getAdminProfileAdm() {
        Response<MdlAdmin> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.getAdminProfileAdm();
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PutMapping(Fix.MAP_ADM + API + "/profile")
    public String updateProfileAdm(String password, String name, MultipartFile avatar) {
        Response<MdlAdmin> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.updateProfileAdm(password, name, avatar);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
    // Admin Web - Admin - End


    // Mobile Mem - Register - Begin
    @PostMapping(Fix.MAP_ANY + API)
    public String createAccountMember(String phoneNumber, String password, String fullName, String face1, String face2, String face3, String payUsername, String payPassword) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.createAccountMember(phoneNumber, password, fullName, face1, face2, face3, payUsername, payPassword);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
    // Mobile Mem - Register - End


    // Mobile Mem - Profile - Begin
    @GetMapping(Fix.MAP_MEM + API + "/detail")
    public String getMemberProfileMem() {
        Response<MdlMember> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.getMemberDetailMem();
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PutMapping(Fix.MAP_MEM + API)
    public String updateMemberDetailMem(String name, String email, Long dob) {
        Response<MdlMember> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.updateMemberDetailMem(name, email, dob);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }


    @GetMapping(Fix.MAP_MEM + API + "/avatar")
    public String getAvatarMem() {
        Response<MdlMember> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.getAvatarMem();
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PutMapping(Fix.MAP_MEM + API + "/avatar")
    public String updateAvatarMem(String avatar) {
        Response<String> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.updateAvatar(avatar);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PutMapping(Fix.MAP_MEM + API + "/face")
    public String updateRevMemFace(Integer revMemId, String revMemName, String face1, String face2, String face3) {
        Response<String> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.updateMemberFaceMem(revMemId, revMemName, face1, face2, face3);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
    // Mobile Mem - Profile - End

    // Mobile Shipper - Begin
    @GetMapping(Fix.MAP_SHP + API + "/shp/detail")
    public String getShipperDetailShp() {
        Response<MdlShipper> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = accountService.getShipperDetailShp();
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
    // Mobile Shipper - End

}
