package tuhoc.lan3.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tuhoc.lan3.entity.User;
import tuhoc.lan3.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final Gson gson;

    public UserController(UserService userService) {
        this.userService = userService;
        this.gson = new GsonBuilder().excludeFieldsWithModifiers().setDateFormat("dd/MM/yyyy HH:mm").create();
    }

    @GetMapping("/user")
    public String viewUserList(Model model) {
        try {
            List<User> users = userService.findAll();
            model.addAttribute("users", users);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "user";
    }

    @GetMapping("/user-new")
    public String addUser(Model model) {
        return "user-detail";
    }


    @PostMapping("/user-new")
    public String adddedUser(@RequestParam Integer id, @RequestParam String username, @RequestParam String gender, @RequestParam String fullname, @RequestParam String password, Model model) {
        try {
            User user = null;

            if (id != null) {
                user = userService.findById(id);
            }

            if (user != null) {
                //  userService.updateUser(username, fullname, (gender == "male" ? true : false), password);
                user.setUsername(username);
                user.setFullname(fullname);
                user.setGender((gender == "male" ? true : false));
                user.setPassword(password);
                userService.updateUser(user);
                model.addAttribute("success", "User updated...");
            } else {
                userService.createUser(username, fullname, (gender == "male" ? true : false), password);
                model.addAttribute("success", "User added...");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("error", "User add fail!");
        }
        return "user-detail";
//        return "user";
        //   return "/user";
    }

    @GetMapping("/user-detail")
    public String viewUser(@RequestParam("id") Integer userID, Model model) {
        try {
            User user = userService.findById(userID);
            model.addAttribute("user", user);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "user-detail";
    }

    @DeleteMapping("/user")
    @ResponseBody
    public String deleteUser(@RequestParam("id") Integer userID) {
        try {
            userService.deleteUser(userID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "{ \"status\": \"ok\" }";
    }

    @DeleteMapping("/users")
    @ResponseBody
    public String deleteUser(@RequestParam("ids") String userIDs) {
        String[] ids = userIDs.split(",");


        try {
            for (int idx = 0; idx < ids.length; idx++) {
                Integer userID = Integer.parseInt(ids[idx]);
                userService.deleteUser(userID);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "{ \"status\": \"ok\" }";
    }
}
