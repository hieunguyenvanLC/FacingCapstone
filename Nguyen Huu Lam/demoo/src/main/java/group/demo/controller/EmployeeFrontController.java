package group.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeFrontController {

    @GetMapping("/all")
    public String viewEmployeeList() {
        return "index";
    }
}
