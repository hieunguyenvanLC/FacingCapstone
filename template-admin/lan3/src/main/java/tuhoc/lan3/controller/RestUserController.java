package tuhoc.lan3.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.*;
import tuhoc.lan3.entity.Respone;
import tuhoc.lan3.entity.Test;

@RestController
public class RestUserController {
    private final Gson gson;

    public RestUserController() {
        this.gson = new GsonBuilder().excludeFieldsWithModifiers().setDateFormat("dd/MM/yyyy HH:mm").create();
    }

    @GetMapping("/user/testget")
    public String testHello(@RequestParam(value = "test", defaultValue = "123") String test) {
        Respone respone = new Respone(false, test);
        return gson.toJson(respone);
    }

    @PostMapping("/user/hello")
    public String testHello(@RequestBody Test body) {
        Respone respone = new Respone(false, body.getTest());
        return gson.toJson(respone);
    }
}
