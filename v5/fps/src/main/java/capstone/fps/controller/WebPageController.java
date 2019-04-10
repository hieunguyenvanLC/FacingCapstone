package capstone.fps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPageController {

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

//    @GetMapping("/sign_up_page")
//    public String signUpPage() {
//        return "sign_up";
//    }

    @GetMapping("/access_denied")
    public String accessDenied() {
        return "no_access";
    }

    @GetMapping("/adm/")
    public String getPageHome() {
        return "admin/dashboard";
    }

    @GetMapping("/adm/account/mem")
    public String getPageAccMem() {
        return "admin/accountMember";
    }

    @GetMapping("/adm/account/shp")
    public String getPageAccShp() {
        return "admin/accountShipper";
    }

    @GetMapping("/adm/account/adm")
    public String getPageAccAdm() {
        return "admin/accountAdmin";
    }

    @GetMapping("/adm/order")
    public String getPageOrder() {
        return "admin/order";
    }

    @GetMapping("/adm/product")
    public String getPageProduct() {
        return "admin/product";
    }

    @GetMapping("/adm/store")
    public String getPageStore() {
        return "admin/store";
    }


}