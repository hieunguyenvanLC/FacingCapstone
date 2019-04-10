package capstone.fps.controller;

import capstone.fps.model.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController extends AbstractController {

    @GetMapping("/logout_success")
    public String logoutSuccess() {
        return gson.toJson(new Response<>(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS));
    }
}
