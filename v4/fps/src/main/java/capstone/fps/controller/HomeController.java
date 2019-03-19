package capstone.fps.controller;

import capstone.fps.common.Fix;
import capstone.fps.model.Response;
import capstone.fps.model.home.MdlReportSummary;
import capstone.fps.service.HomeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController extends AbstractController {
    private static final String API = Fix.MAP_API + "/report";

    private HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping(Fix.MAP_ANY + API + "/summary")
    public String showSummaryReport(@RequestParam("mon") Integer mon, @RequestParam("year") Integer year, @RequestParam("day") Integer day) {
        MdlReportSummary summary = new MdlReportSummary();
        Response response = new Response<MdlReportSummary>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        summary.setShipperCount(this.homeService.countShipper());
//        summary.setNewCustomerCount(this.homeService.countNewCus(mon, year));
        summary.setCustomerCount(this.homeService.countCus());
//        summary.setNewOrderCount(this.homeService.countNewOrder(mon, year));
        summary.setOrderCount(this.homeService.countOrder());
//        summary.setNewStoreCount(this.homeService.countNewStore(mon,year));
        summary.setStoreCount(this.homeService.countStore());
        summary.setOrderCountBy(this.homeService.countOrderBy(mon, year, day));

        summary.setOrderCancelBy(this.homeService.countOrderCancelby(mon, year, day));
        response.setResponse(Response.STATUS_SUCCESS, "", summary);
        return gson.toJson(response);
    }
}
