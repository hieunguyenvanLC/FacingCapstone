package capstone.fps.controller;

import capstone.fps.entity.FRAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginFrontController {

    @GetMapping("/api/index")
    public String index() {
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("page", "home");
        return "admin/admin";
    }

    @GetMapping(path = "/login")
    public String loginForm(Model model, HttpServletRequest request) {
        model.addAttribute("user", new FRAccount());
        try {
            Object flash = request.getSession().getAttribute("flash");
            model.addAttribute("flash", flash);

            request.getSession().removeAttribute("flash");
        } catch (Exception ex) {
            // "flash" session attribute must not exist...do nothing and proceed normally
        }
        return "login";
    }

    @GetMapping("/access_denied")
    public String accessDenied() {
        return "no_access";
    }

    @GetMapping("/sign_up_page")
    public String signUpPage() {
        return "sign_up";
    }


//    @GetMapping("/base")
//    public String base() {
//        return "admin/base";
//    }

    @GetMapping("/user")
    public String user() {
        return "admin/user";
    }

    @GetMapping("/userd")
    public String userd() {
        return "admin/user-detail";
    }
}
