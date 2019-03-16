package capstone.fps.controller;

import capstone.fps.common.Fix;
import capstone.fps.model.MdlOrderSimple;
import capstone.fps.model.Response;
import capstone.fps.service.HomeService;
import capstone.fps.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController extends AbstractController {
    private static final String API = Fix.MAP_API + "report";

    private HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping(Fix.MAP_ANY + API + "/summary")
    public String showSummaryReport() {
        Response response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        Integer cnt = this.homeService.countNewShipper(0, 0);
        response.setResponse(Response.STATUS_SUCCESS, "", cnt);
        return gson.toJson(response);
    }
}
