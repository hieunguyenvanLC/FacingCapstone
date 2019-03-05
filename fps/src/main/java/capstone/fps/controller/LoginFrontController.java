package capstone.fps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginFrontController {

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @GetMapping(path = "/loginPage")
    public String loginForm() {
        return "login";
    }

    @GetMapping(path = "/errorPage")
    public String errorPage() {
        return "error";
    }

    @GetMapping("/access_denied")
    public String accessDenied() {
        return "no_access";
    }

    @GetMapping("/sign_up_page")
    public String signUpPage() {
        return "sign_up";
    }

//    @GetMapping("/log_out")
//    public String logOut(HttpServletRequest request, HttpServletResponse response) {
//        CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
//        cookieClearingLogoutHandler.logout(request, response, null);
//        SecurityContextHolder.clearContext();
//        return "login";
//    }

}
