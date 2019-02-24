package capstone.fps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountListController {
    @GetMapping("/admin/account")
    public String showAccountList(Model model) {
        model.addAttribute("page", "account");
        return "admin/account";
    }
    @GetMapping("/admin/admin")
    public String showAdminList(Model model) {
        model.addAttribute("page", "admin");
        return "admin/adminPage";
    }
    @GetMapping("/admin/shipper")
    public String showShipperList(Model model) {
        model.addAttribute("page", "shipper");
        return "admin/shipper";
    }
    @GetMapping("/admin/order")
    public String showOrderList(Model model) {
        model.addAttribute("page", "order");
        return "admin/order";
    }
    @GetMapping("/admin/store")
    public String showProductList(Model model) {
        model.addAttribute("page", "store");
        return "admin/store";
    }
}
