package group.demo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.RestController;

@RestController
//Phai add Rest Controller thi no moi tra ve chuoi
//Thang nao goi thi se tra ve cho thang do

public class AbstractController {

    protected  final Gson gson;

    public AbstractController(){
        this.gson=new GsonBuilder().excludeFieldsWithModifiers().setDateFormat("dd/MM/yyyy HH:mm").create();
    }
}
