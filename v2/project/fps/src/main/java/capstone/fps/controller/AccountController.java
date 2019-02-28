package capstone.fps.controller;

import capstone.fps.common.Fix;
import capstone.fps.model.MdlAccAdmAdm;
import capstone.fps.model.Response;
import capstone.fps.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@Controller
public class AccountController extends AbstractController {

    private static final String API = Fix.MAP_API + "account";

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(Fix.MAP_ANY + API)
    public String createAccountMember(String phoneNumber, String password, String fullName) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            if (accountService.createAccountMember(phoneNumber, password, fullName)) {
                response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PostMapping(Fix.MAP_ADM + API + "/shp")
    public String createAccountShipper(String phoneNumber, String password, String fullName, String email, String userImg, String natId, long natDate, long dob, String note, String bikeRegId, String introduce, String natFrnt, String natBack, String bikeRegFrnt, String bikeRegBack, int sourceId) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] userImgB = decoder.decode(userImg);
            byte[] natFrontB = decoder.decode(natFrnt);
            byte[] natBackB = decoder.decode(natBack);
            byte[] bikeRegFrontB = decoder.decode(bikeRegFrnt);
            byte[] bikeRegBackB = decoder.decode(bikeRegBack);
            if (accountService.createAccountShipper(phoneNumber, password, fullName, email, userImgB, natId, natDate, dob, note, bikeRegId, introduce, natFrontB, natBackB, bikeRegFrontB, bikeRegBackB, sourceId)) {
                response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
            }
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
            if (accountService.createAccountAdmin(username, password, fullName, email, natId, natDate, dob, note)) {
                response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ADM + API + "/adm")
    public String getAccountAdminList() {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            List<MdlAccAdmAdm> admList = accountService.getListAdmin();
            response.setResponse(admList.size(), Response.MESSAGE_SUCCESS, admList);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }


    @PutMapping(Fix.MAP_LOG + API)
    public String updateProfile(@RequestParam String profileStr) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
//            MdlAccEdt accEdt = gson.fromJson(profileStr, MdlAccEdt.class);
//            if (accountService.updateProfile(accEdt)) {
//                response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

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


//    @PutMapping(API + "/face")
//    public String updateFace(@RequestParam MultipartFile faceImg) {
//        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
//        try {
//
//            Profile profile = userService.findUserByUsername(profileModel.getAccountUser()).getProfileId();
//            if (profile == null) {
//                profile = new Profile();
//            }
//
//            userService.handleImage(profileModel, avatar);
//            LOGGER.info(profileModel.getAvatar());
//            profile = profileTransformer.ProfileModelToEntity(profileModel, profile);
//            profile = userService.saveProfile(profile);
//
//            Account account = userService.findUserByUsername(profileModel.getAccountUser());
//            account.setProfileId(profile);
//            accountRepository.save(account);
//
//            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS);
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error(e.getMessage());
//            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
//        }
//        return gson.toJson(response);
//    }

}
