package capstone.fps.controller;

import capstone.fps.model.MdlAccBan;
import capstone.fps.model.MdlAccEdt;
import capstone.fps.model.MdlAccNewMem;
import capstone.fps.model.Response;
import capstone.fps.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountMemberController extends AbstractController {

    private static final String API = "/api/account/member";
    AccountService accountService;

    public AccountMemberController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(API)
    public String createAccountMember(@RequestParam String dataStr) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            MdlAccNewMem accountCreate = gson.fromJson(dataStr, MdlAccNewMem.class);
            if (accountService.createAccountMember(accountCreate)) {
                response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PutMapping(API)
    public String updateProfile(@RequestParam String profileStr) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            MdlAccEdt accEdt = gson.fromJson(profileStr, MdlAccEdt.class);
            if (accountService.updateProfile(accEdt)) {
                response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @DeleteMapping(API)
    public String deactivateAccount(@RequestParam String banStr) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            MdlAccBan accBan = gson.fromJson(banStr, MdlAccBan.class);
            if (accountService.banAccount(accBan)) {
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
