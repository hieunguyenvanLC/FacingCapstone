package Controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class Controller {
    @RequestMapping("/")
    public String home(final Model model){
        model.addAttribute("message", "Test");
        return "index";
    }
}
