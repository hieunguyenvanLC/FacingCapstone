package group.demo.controller;

import group.demo.entity.Employee;
import group.demo.model.Response;
import group.demo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@CrossOrigin
public class EmployeeController extends AbstractController{

    public static final String API="/api";

    //API Response code
    public static final int STATUS_CODE_SERVER_ERROR = -1;
    public static final int STATUS_CODE_FAIL = 0;
    public static final int STATUS_CODE_SUCCESS = 1;
    public static final int STATUS_CODE_NO_RESULT = 2;

    public static final String MESSAGE_SUCCESS = "Success!";
    public static final String MESSAGE_FAIL = "Fail!";
    public static final String MESSAGE_SERVER_ERROR = "Server error!";
    public static final String MESSAGE_NO_RESULT = "No result!";

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(API +"/viewall")
    public String viewAllEmployee(){
        Response<List<Employee>> response=new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        try{
            List<Employee> employees=employeeService.findAll();
            response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS, employees);
        }catch (Exception ex){
            ex.printStackTrace();
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PostMapping(API + "/add")
    public String createEmployee(@RequestParam String name, @RequestParam String phone){
        Response response=new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        try {
            employeeService.createEmployee(name,phone);
            response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            response.setResponse(STATUS_CODE_SERVER_ERROR,MESSAGE_SERVER_ERROR);
        }
        return  gson.toJson(response);
    }
}
