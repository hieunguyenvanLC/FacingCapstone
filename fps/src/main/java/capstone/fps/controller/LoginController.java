package capstone.fps.controller;

import capstone.fps.entity.FRAccount;
import capstone.fps.model.Response;
import capstone.fps.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController extends AbstractController {

    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/checkLogin")
    public String getLogin(@RequestParam Double phone, @RequestParam String password) {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        if (loginService.checkLogin(phone, password)) {
            response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS);
        } else {
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }


}
