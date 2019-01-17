package group.demo.controller;


import group.demo.entity.Student;
import group.demo.model.Response;
import group.demo.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@CrossOrigin
//Phải @ 2 cái này không thì bị lỗi 500
public class StudentController extends AbstractController {
    public static final String API = "/api";

    //API Response code => Lấy thông báo mong muốn ra
    public static final int STATUS_CODE_SERVER_ERROR = -1;
    public static final int STATUS_CODE_FAIL = 0;
    public static final int STATUS_CODE_SUCCESS = 1;
    public static final int STATUS_CODE_NO_RESULT = 2;

    public static final String MESSAGE_SUCCESS = "Success!";
    public static final String MESSAGE_FAIL = "Fail!";
    public static final String MESSAGE_SERVER_ERROR = "Server error!";
    public static final String MESSAGE_NO_RESULT = "No result!";

    private final StudentService studentService;

    //Phải khai báo để lấy đc Student Service
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //Lấy GET cho phương thức view all Student
    @GetMapping(API + "/viewallStudent")
    public String viewAllStudent() {
        // Map giá trị cho respone
        Response<List<Student>> response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        try {
            List<Student> students = studentService.findAll();
            response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS, students);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    //Lấy POST cho phương thức add
    @PostMapping(API + "/addStudent")
    public String createStudent(@RequestParam String name, @RequestParam double age, @RequestParam String student_code) {
        Response response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        try {
            studentService.createStudent(name, age, student_code);
            response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PostMapping(API + "/fixStudent")
    public String updateStudent(@RequestParam Integer studentId, @RequestParam String name, @RequestParam double age, @RequestParam String student_code) {
        Response response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        try {
            studentService.updateStudent(studentId, name, age, student_code);
            response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PostMapping(API + "delStudent")
    public String delStudent(@RequestParam int studentId) {
        Response response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        try {
            studentService.hideStudent(studentId);
            response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
}
