package capstone.fps.controller;

import capstone.fps.model.Response;
import capstone.fps.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AccountController extends AbstractController {

    private static final String API = "/api/account";
    AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(API)
    public String createAccount(@RequestParam String profileStr) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            if (accountService.createAccount(profileStr)) {
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
            if (accountService.updateProfile(profileStr)) {
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
