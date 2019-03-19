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

        summary.setShipperCount(this.homeService.countShipper()); //Count All Shipper
//        summary.setNewCustomerCount(this.homeService.countNewCus(mon, year));
        summary.setCustomerCount(this.homeService.countCus());  //Count All Customer
//        summary.setNewOrderCount(this.homeService.countNewOrder(mon, year));
        summary.setOrderCount(this.homeService.countOrder());   //Count All Order
//        summary.setNewStoreCount(this.homeService.countNewStore(mon,year));
        summary.setStoreCount(this.homeService.countStore());   //Count All Store


        summary.setOrderCountBy(this.homeService.countOrderBy(mon, year, day)); //Count Order With Day Month Year

        summary.setOrderCancelBy(this.homeService.countOrderCancelBy(mon, year, day));   //Count Order Cancel With Day Month Year

        summary.setOrderSuccessBy(this.homeService.countOrderSuccessBy(mon, year, day));  //Count Order Success With Day Month Year


        response.setResponse(Response.STATUS_SUCCESS, "", summary);
        return gson.toJson(response);
    }
}
