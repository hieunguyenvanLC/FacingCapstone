package ui.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ui.demo.entity.Employee;
import ui.demo.service.EmployeeService;

import java.util.List;

@Controller
public class EmployeeFrontController {

    private final EmployeeService employeeService;

    public EmployeeFrontController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public String viewEmployeeList() {
        return "trangchu";
    }

    @GetMapping("/")
    public String viewHome() {
        return "Home";
    }

    @GetMapping("/user")
    public String viewUser(Model model) {
        try{
            List<Employee> employees=employeeService.findAll();
            model.addAttribute("employees", employees);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return "User";
    }
}
