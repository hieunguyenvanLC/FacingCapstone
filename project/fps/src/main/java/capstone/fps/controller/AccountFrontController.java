package capstone.fps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountFrontController {

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
    public String getPageStore(Model model) {
        model.addAttribute("page", "store");
        return "admin/store";
    }
}