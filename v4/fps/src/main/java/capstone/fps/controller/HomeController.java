package capstone.fps.controller;

import capstone.fps.common.Fix;
import capstone.fps.model.Response;
import capstone.fps.model.home.MdlReportSummary;
import capstone.fps.model.home.MdlReportSummaryDetail;
import capstone.fps.service.HomeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController extends AbstractController {
    private static final String API = Fix.MAP_API + "/report";

    private HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping(Fix.MAP_ANY + API + "/summary")
    public String showSummaryReport(@RequestParam("mon") Integer mon, @RequestParam("year") Integer year/*, @RequestParam("day") Integer day*/) {
        MdlReportSummary summary = new MdlReportSummary();
        Response response = new Response<MdlReportSummary>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        summary.setShipperCount(this.homeService.countShipper()); //Count All Shipper
//        summary.setNewCustomerCount(this.homeService.countNewCus(mon, year));
        summary.setCustomerCount(this.homeService.countCus());  //Count All Customer
//        summary.setNewOrderCount(this.homeService.countNewOrder(mon, year));
        summary.setOrderCount(this.homeService.countOrder());   //Count All Order
//        summary.setNewStoreCount(this.homeService.countNewStore(mon,year));
        summary.setStoreCount(this.homeService.countStore());   //Count All Store

        YearMonth yearMonthObject = YearMonth.of(year, mon);
        Integer daysInMonth = yearMonthObject.lengthOfMonth(); //28

        List<String> labels = new ArrayList<String>();
        List<Integer> orders = new ArrayList<Integer>();
        List<Integer> canceledOrders = new ArrayList<Integer>();
        List<Integer> successOrders = new ArrayList<Integer>();

        for (Integer day = 1; day < daysInMonth ; day++) {
            labels.add(day.toString());
            orders.add(this.homeService.countOrderBy(mon, year, day));
            canceledOrders.add(this.homeService.countOrderCancelBy(mon, year, day));
            successOrders.add(this.homeService.countOrderSuccessBy(mon, year, day));
        }

        summary.setLabels(labels);
        summary.setOrders(orders);
        summary.setCanceledOrders(canceledOrders);
        summary.setSuccessOrders(successOrders);

        response.setResponse(Response.STATUS_SUCCESS, "", summary);
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ANY + API + "/summaryDetail")
    public String showSummaryDetailReport(@RequestParam("mon") Integer mon, @RequestParam("year") Integer year, @RequestParam("day") Integer day) {
        Response response = new Response<MdlReportSummaryDetail>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        MdlReportSummaryDetail detail = new MdlReportSummaryDetail();

        detail.setOrderCountBy(this.homeService.countOrderBy(mon, year, day)); //Count Order With Day Month Year
        detail.setOrderCancelBy(this.homeService.countOrderCancelBy(mon, year, day));   //Count Order Cancel With Day Month Year
        detail.setOrderSuccessBy(this.homeService.countOrderSuccessBy(mon, year, day));  //Count Order Success With Day Month Year
        detail.setSoldProductBy(this.homeService.sumProductByOrder(mon, year, day)); // Sum product by delivered order

        response.setResponse(Response.STATUS_SUCCESS, "", detail);
        return gson.toJson(response);
    }
}
