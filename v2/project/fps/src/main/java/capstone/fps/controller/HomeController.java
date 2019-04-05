package capstone.fps.controller;

import capstone.fps.common.Fix;
import capstone.fps.model.MdlOrderSimple;
import capstone.fps.model.MdlReportSummary;
import capstone.fps.model.Response;
import capstone.fps.service.HomeService;
import capstone.fps.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController extends AbstractController {
    private static final String API = Fix.MAP_API + "report";

    private HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping(Fix.MAP_ANY + API + "/summary")
    public String showSummaryReport(@RequestParam("mon") Integer mon, @RequestParam("year") Integer year) {
        MdlReportSummary summary = new MdlReportSummary();
        Response response = new Response<MdlReportSummary>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        summary.setShipperCount(this.homeService.countNewShipper());
        summary.setNewCustomerCount(this.homeService.countNewCus(mon, year));
        summary.setNewOrderCount(this.homeService.countNewOrder(mon, year));
        summary.setNewStoreCount(this.homeService.countNewStore(mon,year));

        response.setResponse(Response.STATUS_SUCCESS, "", summary);
        return gson.toJson(response);
    }
}