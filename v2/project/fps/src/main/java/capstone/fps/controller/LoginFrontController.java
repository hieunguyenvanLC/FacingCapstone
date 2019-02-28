package capstone.fps.controller;

import capstone.fps.common.Fix;
import capstone.fps.entity.FRAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginFrontController {

    @GetMapping("/")
    public String index() {
        return "login";
    }

//    @GetMapping(Fix.MAP_MEM + "home")
//    public String index() {
//        return "index";
//    }
//
//    @GetMapping(Fix.MAP_ADM + "home")
//    public String admin() {
//        return "admin";
//    }

    @GetMapping(path = "/loginPage")
    public String loginForm() {
        return "login";
    }

//    @GetMapping(path = "/loginPage")
//    public String loginForm(Model model, HttpServletRequest request) {
//        model.addAttribute("user", new FRAccount());
//        try {
//            Object flash = request.getSession().getAttribute("flash");
//            model.addAttribute("flash", flash);
//            request.getSession().removeAttribute("flash");
//        } catch (Exception ex) {
//            // "flash" session attribute must not exist...do nothing and proceed normally
//        }
//        return "login";
//    }

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


}
