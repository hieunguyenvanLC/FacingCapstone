package capstone.fps.controller;

import capstone.fps.common.Fix;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController extends AbstractController {

    @GetMapping(value = Fix.MAP_ANY + Fix.MAP_API + "csrf")
    public String getCsrfToken(HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return gson.toJson(token.getToken());
    }

}
