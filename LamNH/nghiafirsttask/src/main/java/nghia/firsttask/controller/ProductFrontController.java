package nghia.firsttask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductFrontController {


    @GetMapping("")
    public String viewProductList(Model model) {
        System.out.println("product");
        model.addAttribute("page", "productPage");
        return "product";
    }


}
