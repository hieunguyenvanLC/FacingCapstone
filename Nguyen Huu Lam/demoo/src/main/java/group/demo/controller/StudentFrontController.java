package group.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentFrontController {

    @GetMapping("/allStudent")
    public String viewStudentList() {

        return "indexStudent";
    }
}
