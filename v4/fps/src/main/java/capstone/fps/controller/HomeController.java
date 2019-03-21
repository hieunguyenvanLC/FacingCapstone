package capstone.fps.controller;

import capstone.fps.common.Fix;
import capstone.fps.entity.FROrder;
import capstone.fps.model.Response;
import capstone.fps.model.home.MdlChartData;
import capstone.fps.model.home.MdlReportSummary;
import capstone.fps.model.home.MdlReportSummaryDetail;
import capstone.fps.model.order.MdlOrder;
import capstone.fps.model.order.MdlOrderBuilder;
import capstone.fps.service.HomeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

//        YearMonth yearMonthObject = YearMonth.of(year, mon);
//        Integer daysInMonth = yearMonthObject.lengthOfMonth(); //28

//        List<String> labels = new ArrayList<String>();
//        List<Integer> orders = new ArrayList<Integer>();
//        List<Integer> canceledOrders = new ArrayList<Integer>();
//        List<Integer> successOrders = new ArrayList<Integer>();
//
//        for (Integer day = 1; day < daysInMonth ; day++) {
//            labels.add(day.toString());
//            orders.add(this.homeService.countOrderBy(mon, year, day));
//            canceledOrders.add(this.homeService.countOrderCancelBy(mon, year, day));
//            successOrders.add(this.homeService.countOrderSuccessBy(mon, year, day));
//        }
//
//        summary.setLabels(labels);
//        summary.setOrders(orders);
//        summary.setCanceledOrders(canceledOrders);
//        summary.setSuccessOrders(successOrders);

        response.setResponse(Response.STATUS_SUCCESS, "", summary);
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ANY + API + "/canceledorderchart")
    public String cancelOrderChart(@RequestParam("type") Integer type, @RequestParam("start") Long start, @RequestParam("end") Long end) {
        Response response = new Response<MdlChartData<Integer>>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        Date startDate = new Date(start); // neu start la milisec thi khong can nhan 1000
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        int startYear = startCal.get(Calendar.YEAR);
        int startMonth = startCal.get(Calendar.MONTH) + 1; // start from 0, mon 0 = thang 1
        int startDay = startCal.get(Calendar.DAY_OF_MONTH);

        Date endDate = new Date(end); // neu start la milisec thi khong can nhan 1000
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int endYear = endCal.get(Calendar.YEAR);
        int endMonth = endCal.get(Calendar.MONTH) + 1;
        int endDay = endCal.get(Calendar.DAY_OF_MONTH);

        MdlChartData<Integer> chartData = new MdlChartData<Integer>();
        List<String> labels = new ArrayList<String>();
        List<Integer> orders = new ArrayList<Integer>();

        if (type == 0) { // neu la  theo tuan //ok
            for (Integer day = startDay; day <= endDay; day++) {
                labels.add(day.toString());
                long startUnix = this.dateToUnix(startYear, startMonth, day, 0, 0, 0);
                long endUnix = this.dateToUnix(startYear, startMonth, day + 1, 0, 0, 0);
                orders.add(this.homeService.countOrderCancelBy(startUnix, endUnix)); // Gia su startMonth = endMonth
            }
        } else if (type == 2) { // neu la theo thang
            for (Integer mon = startMonth; mon <= endMonth; mon++) {
                labels.add(mon.toString());
                long startUnix = this.dateToUnix(startYear, mon, 1, 0, 0, 0);
                long endUnix = this.dateToUnix(startYear, mon + 1, 1, 0, 0, 0);
                orders.add(this.homeService.countOrderCancelBy(startUnix, endUnix));
            }
        } else { // neu la theo nam
            for (Integer year = startYear; year <= endYear; year++) {
                labels.add(year.toString());
                long startUnix = this.dateToUnix(year, 1, 1, 0, 0, 0);
                long endUnix = this.dateToUnix(year + 1, 1, 1, 0, 0, 0);
                orders.add(this.homeService.countOrderCancelBy(startUnix, endUnix)); // Gia su startMonth = endMonth
            }
        }

        chartData.setLabels(labels);
        chartData.setData(orders);

        response.setResponse(Response.STATUS_SUCCESS, "", chartData);
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ANY + API + "/successorderchart")
    public String successOrderChart(@RequestParam("type") Integer type, @RequestParam("start") Long start, @RequestParam("end") Long end) {
        Response response = new Response<MdlChartData<Integer>>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        Date startDate = new Date(start); // neu start la milisec thi khong can nhan 1000
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        int startYear = startCal.get(Calendar.YEAR);
        int startMonth = startCal.get(Calendar.MONTH) + 1; // start from 0, mon 0 = thang 1
        int startDay = startCal.get(Calendar.DAY_OF_MONTH);

        Date endDate = new Date(end); // neu start la milisec thi khong can nhan 1000
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int endYear = endCal.get(Calendar.YEAR);
        int endMonth = endCal.get(Calendar.MONTH) + 1;
        int endDay = endCal.get(Calendar.DAY_OF_MONTH);

        MdlChartData<Integer> chartData = new MdlChartData<Integer>();
        List<String> labels = new ArrayList<String>();
        List<Integer> orders = new ArrayList<Integer>();

        if (type == 0) { // neu la  theo tuan //ok
            for (Integer day = startDay; day <= endDay; day++) {
                labels.add(day.toString());
                long startUnix = this.dateToUnix(startYear, startMonth, day, 0, 0, 0);
                long endUnix = this.dateToUnix(startYear, startMonth, day + 1, 0, 0, 0);
                orders.add(this.homeService.countOrderSuccessBy(startUnix, endUnix)); // Gia su startMonth = endMonth
            }
        } else if (type == 2) { // neu la theo thang
            for (Integer mon = startMonth; mon <= endMonth; mon++) {
                labels.add(mon.toString());
                long startUnix = this.dateToUnix(startYear, mon, 1, 0, 0, 0);
                long endUnix = this.dateToUnix(startYear, mon + 1, 1, 0, 0, 0);
                orders.add(this.homeService.countOrderSuccessBy(startUnix, endUnix));
            }
        } else { // neu la theo nam
            for (Integer year = startYear; year <= endYear; year++) {
                labels.add(year.toString());
                long startUnix = this.dateToUnix(year, 1, 1, 0, 0, 0);
                long endUnix = this.dateToUnix(year + 1, 1, 1, 0, 0, 0);
                orders.add(this.homeService.countOrderSuccessBy(startUnix, endUnix)); // Gia su startMonth = endMonth
            }
        }

        chartData.setLabels(labels);
        chartData.setData(orders);

        response.setResponse(Response.STATUS_SUCCESS, "", chartData);
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ANY + API + "/soldproductchart")
    public String productSoldChart(@RequestParam("type") Integer type, @RequestParam("start") Long start, @RequestParam("end") Long end) {
        Response response = new Response<MdlChartData<Integer>>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        Date startDate = new Date(start); // neu start la milisec thi khong can nhan 1000
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        int startYear = startCal.get(Calendar.YEAR);
        int startMonth = startCal.get(Calendar.MONTH) + 1; // start from 0, mon 0 = thang 1
        int startDay = startCal.get(Calendar.DAY_OF_MONTH);

        Date endDate = new Date(end); // neu start la milisec thi khong can nhan 1000
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int endYear = endCal.get(Calendar.YEAR);
        int endMonth = endCal.get(Calendar.MONTH) + 1;
        int endDay = endCal.get(Calendar.DAY_OF_MONTH);

        MdlChartData<Integer> chartData = new MdlChartData<Integer>();
        List<String> labels = new ArrayList<String>();
        List<Integer> orders = new ArrayList<Integer>();

        if (type == 0) { // neu la  theo tuan //ok
            for (Integer day = startDay; day <= endDay; day++) {
                labels.add(day.toString());
                long startUnix = this.dateToUnix(startYear, startMonth, day, 0, 0, 0);
                long endUnix = this.dateToUnix(startYear, startMonth, day + 1, 0, 0, 0);
                orders.add(this.homeService.sumProductByOrder(startUnix, endUnix)); // Gia su startMonth = endMonth
            }
        } else if (type == 2) { // neu la theo thang
            for (Integer mon = startMonth; mon <= endMonth; mon++) {
                labels.add(mon.toString());
                long startUnix = this.dateToUnix(startYear, mon, 1, 0, 0, 0);
                long endUnix = this.dateToUnix(startYear, mon + 1, 1, 0, 0, 0);
                orders.add(this.homeService.sumProductByOrder(startUnix, endUnix));
            }
        } else { // neu la theo nam
            for (Integer year = startYear; year <= endYear; year++) {
                labels.add(year.toString());
                long startUnix = this.dateToUnix(year, 1, 1, 0, 0, 0);
                long endUnix = this.dateToUnix(year + 1, 1, 1, 0, 0, 0);
                orders.add(this.homeService.sumProductByOrder(startUnix, endUnix)); // Gia su startMonth = endMonth
            }
        }

        chartData.setLabels(labels);
        chartData.setData(orders);

        response.setResponse(Response.STATUS_SUCCESS, "", chartData);
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ANY + API + "/successratechart")
    public String rateSuccesfull(@RequestParam("type") Integer type, @RequestParam("start") Long start, @RequestParam("end") Long end) {
        Response response = new Response<MdlChartData<Double>>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        Date startDate = new Date(start); // neu start la milisec thi khong can nhan 1000
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        int startYear = startCal.get(Calendar.YEAR);
        int startMonth = startCal.get(Calendar.MONTH) + 1; // start from 0, mon 0 = thang 1
        int startDay = startCal.get(Calendar.DAY_OF_MONTH);

        Date endDate = new Date(end); // neu start la milisec thi khong can nhan 1000
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int endYear = endCal.get(Calendar.YEAR);
        int endMonth = endCal.get(Calendar.MONTH) + 1;
        int endDay = endCal.get(Calendar.DAY_OF_MONTH);

        MdlChartData<Double> chartData = new MdlChartData<Double>();
        List<String> labels = new ArrayList<String>();
        List<Double> orders = new ArrayList<Double>();

        if (type == 0) { // neu la  theo tuan //ok
            for (Integer day = startDay; day <= endDay; day++) {
                labels.add(day.toString());
                long startUnix = this.dateToUnix(startYear, startMonth, day, 0, 0, 0);
                long endUnix = this.dateToUnix(startYear, startMonth, day + 1, 0, 0, 0);
                double total = this.homeService.countOrderBy(startUnix, endUnix) * 1.0D;
                if (total != 0) {
                    double rate = this.homeService.countOrderSuccessBy(startUnix, endUnix) / total * 100.0D;
                    orders.add(rate);
                } else {
                    orders.add(0.0D);
                } // Gia su startMonth = endMonth
            }
        } else if (type == 2) { // neu la theo thang
            for (Integer mon = startMonth; mon <= endMonth; mon++) {
                labels.add(mon.toString());
                long startUnix = this.dateToUnix(startYear, mon, 1, 0, 0, 0);
                long endUnix = this.dateToUnix(startYear, mon + 1, 1, 0, 0, 0);
                double total = this.homeService.countOrderBy(startUnix, endUnix) * 1.0D;
                if (total != 0) {
                    double rate = this.homeService.countOrderSuccessBy(startUnix, endUnix) / total * 100.0D;
                    orders.add(rate);
                } else {
                    orders.add(0.0D);
                }
            }
        } else { // neu la theo nam
            for (Integer year = startYear; year <= endYear; year++) {
                labels.add(year.toString());
                long startUnix = this.dateToUnix(year, 1, 1, 0, 0, 0);
                long endUnix = this.dateToUnix(year + 1, 1, 1, 0, 0, 0);
                double total = this.homeService.countOrderBy(startUnix, endUnix) * 1.0D;
                if (total != 0) {
                    double rate = this.homeService.countOrderSuccessBy(startUnix, endUnix) / total * 100.0D;
                    orders.add(rate);
                } else {
                    orders.add(0.0D);
                }
            }
        }

        chartData.setLabels(labels);
        chartData.setData(orders);

        response.setResponse(Response.STATUS_SUCCESS, "", chartData);
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ANY + API + "/incomeammountchart")
    public String totalAmount(@RequestParam("type") Integer type, @RequestParam("start") Long start, @RequestParam("end") Long end) {
        Response response = new Response<MdlChartData<Integer>>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        Date startDate = new Date(start); // neu start la milisec thi khong can nhan 1000
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        int startYear = startCal.get(Calendar.YEAR);
        int startMonth = startCal.get(Calendar.MONTH) + 1; // start from 0, mon 0 = thang 1
        int startDay = startCal.get(Calendar.DAY_OF_MONTH);

        Date endDate = new Date(end); // neu start la milisec thi khong can nhan 1000
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int endYear = endCal.get(Calendar.YEAR);
        int endMonth = endCal.get(Calendar.MONTH) + 1;
        int endDay = endCal.get(Calendar.DAY_OF_MONTH);

        MdlChartData<Integer> chartData = new MdlChartData<Integer>();
        List<String> labels = new ArrayList<String>();
        List<Integer> orders = new ArrayList<Integer>();

        if (type == 0) { // neu la  theo tuan //ok
            for (Integer day = startDay; day <= endDay; day++) {
                labels.add(day.toString());
                long startUnix = this.dateToUnix(startYear, startMonth, day, 0, 0, 0);
                long endUnix = this.dateToUnix(startYear, startMonth, day + 1, 0, 0, 0);
                orders.add(this.homeService.sumTotalAmount(startUnix, endUnix)); // Gia su startMonth = endMonth
            }
        } else if (type == 2) { // neu la theo thang
            for (Integer mon = startMonth; mon <= endMonth; mon++) {
                labels.add(mon.toString());
                long startUnix = this.dateToUnix(startYear, mon, 1, 0, 0, 0);
                long endUnix = this.dateToUnix(startYear, mon + 1, 1, 0, 0, 0);
                orders.add(this.homeService.sumTotalAmount(startUnix, endUnix));
            }
        } else { // neu la theo nam
            for (Integer year = startYear; year <= endYear; year++) {
                labels.add(year.toString());
                long startUnix = this.dateToUnix(year, 1, 1, 0, 0, 0);
                long endUnix = this.dateToUnix(year + 1, 1, 1, 0, 0, 0);
                orders.add(this.homeService.sumTotalAmount(startUnix, endUnix)); // Gia su startMonth = endMonth
            }
        }

        chartData.setLabels(labels);
        chartData.setData(orders);

        response.setResponse(Response.STATUS_SUCCESS, "", chartData);
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ANY + API + "/paidshipperchart")
    public String paidShipper(@RequestParam("type") Integer type, @RequestParam("start") Long start, @RequestParam("end") Long end) {
        /* type tuong ung 0-tuan, 1-thang, 2-nam */
        /* start: kieu unix time dung de filter */
        /* end: kieu unix time */
        Response response = new Response<MdlChartData<Integer>>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        Date startDate = new Date(start); // neu start la milisec thi khong can nhan 1000
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        int startYear = startCal.get(Calendar.YEAR);
        int startMonth = startCal.get(Calendar.MONTH) + 1; // start from 0, mon 0 = thang 1
        int startDay = startCal.get(Calendar.DAY_OF_MONTH);

        Date endDate = new Date(end); // neu start la milisec thi khong can nhan 1000
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int endYear = endCal.get(Calendar.YEAR);
        int endMonth = endCal.get(Calendar.MONTH) + 1;
        int endDay = endCal.get(Calendar.DAY_OF_MONTH);

        MdlChartData<Integer> chartData = new MdlChartData<Integer>();
        List<String> labels = new ArrayList<String>();
        List<Integer> orders = new ArrayList<Integer>();

        if (type == 0) { // neu la  theo tuan //ok
            for (Integer day = startDay; day <= endDay; day++) {
                labels.add(day.toString());
                long startUnix = this.dateToUnix(startYear, startMonth, day, 0, 0, 0);
                long endUnix = this.dateToUnix(startYear, startMonth, day + 1, 0, 0, 0);
                orders.add(this.homeService.sumShipperEarn(startUnix, endUnix)); // Gia su startMonth = endMonth
            }
        } else if (type == 2) { // neu la theo thang
            for (Integer mon = startMonth; mon <= endMonth; mon++) {
                labels.add(mon.toString());
                long startUnix = this.dateToUnix(startYear, mon, 1, 0, 0, 0);
                long endUnix = this.dateToUnix(startYear, mon + 1, 1, 0, 0, 0);
                orders.add(this.homeService.sumShipperEarn(startUnix, endUnix));
            }
        } else { // neu la theo nam
            for (Integer year = startYear; year <= endYear; year++) {
                labels.add(year.toString());
                long startUnix = this.dateToUnix(year, 1, 1, 0, 0, 0);
                long endUnix = this.dateToUnix(year + 1, 1, 1, 0, 0, 0);
                orders.add(this.homeService.sumShipperEarn(startUnix, endUnix)); // Gia su startMonth = endMonth
            }
        }

        chartData.setLabels(labels);
        chartData.setData(orders);

        response.setResponse(Response.STATUS_SUCCESS, "", chartData);
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ANY + API + "/orderchart")
    public String showOrderChart(@RequestParam("type") Integer type, @RequestParam("start") Long start, @RequestParam("end") Long end) {
        /* type tuong ung 0-tuan, 1-thang, 2-nam */
        /* start: kieu unix time dung de filter */
        /* end: kieu unix time */
        Response response = new Response<MdlChartData<Integer>>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        Date startDate = new Date(start); // neu start la milisec thi khong can nhan 1000
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        int startYear = startCal.get(Calendar.YEAR);
        int startMonth = startCal.get(Calendar.MONTH) + 1; // start from 0, mon 0 = thang 1
        int startDay = startCal.get(Calendar.DAY_OF_MONTH);

        Date endDate = new Date(end); // neu start la milisec thi khong can nhan 1000
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int endYear = endCal.get(Calendar.YEAR);
        int endMonth = endCal.get(Calendar.MONTH) + 1;
        int endDay = endCal.get(Calendar.DAY_OF_MONTH);

        MdlChartData<Integer> chartData = new MdlChartData<Integer>();
        List<String> labels = new ArrayList<String>();
        List<Integer> orders = new ArrayList<Integer>();

        if (type == 0) { // neu la  theo tuan //ok
            for (Integer day = startDay; day <= endDay; day++) {
                labels.add(day.toString());
                long startUnix = this.dateToUnix(startYear, startMonth, day, 0, 0, 0);
                long endUnix = this.dateToUnix(startYear, startMonth, day + 1, 0, 0, 0);
                orders.add(this.homeService.countOrderBy(startUnix, endUnix)); // Gia su startMonth = endMonth
            }
        } else if (type == 2) { // neu la theo thang
            for (Integer mon = startMonth; mon <= endMonth; mon++) {
                labels.add(mon.toString());
                long startUnix = this.dateToUnix(startYear, mon, 1, 0, 0, 0);
                long endUnix = this.dateToUnix(startYear, mon + 1, 1, 0, 0, 0);
                orders.add(this.homeService.countOrderBy(startUnix, endUnix));
            }
        } else { // neu la theo nam
            for (Integer year = startYear; year <= endYear; year++) {
                labels.add(year.toString());
                long startUnix = this.dateToUnix(year, 1, 1, 0, 0, 0);
                long endUnix = this.dateToUnix(year + 1, 1, 1, 0, 0, 0);
                orders.add(this.homeService.countOrderBy(startUnix, endUnix)); // Gia su startMonth = endMonth
            }
        }

        chartData.setLabels(labels);
        chartData.setData(orders);

        response.setResponse(Response.STATUS_SUCCESS, "", chartData);
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ANY + API + "/summaryDetail")
    public String showSummaryDetailReport(@RequestParam("mon") Integer mon, @RequestParam("year") Integer year, @RequestParam("day") Integer day) {
        Response response = new Response<MdlReportSummaryDetail>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        MdlReportSummaryDetail detail = new MdlReportSummaryDetail();

        detail.setOrderCountBy(0); //Count Order With Day Month Year
        detail.setOrderCancelBy(0);   //Count Order Cancel With Day Month Year
        detail.setOrderSuccessBy(0);  //Count Order Success With Day Month Year
        detail.setSoldProductBy(0); // Sum product by delivered order

        response.setResponse(Response.STATUS_SUCCESS, "", detail);
        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ANY + API + "/orderlist")
    public String getOrderList(@RequestParam("status") Integer status, @RequestParam("start") Long start, @RequestParam("end") Long end) {
        Response<List<MdlOrder>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        List<MdlOrder> mdlOrders = new ArrayList<>();

        try {
            List<FROrder> orders = this.homeService.getOrderList(status, start, end);
            MdlOrderBuilder orderBuilder = new MdlOrderBuilder();

            for (FROrder order : orders) {
                mdlOrders.add(orderBuilder.buildAdminTableRow(order));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }

        response.setResponse(Response.STATUS_SUCCESS, "", mdlOrders);
        return gson.toJson(response);
    }

    private long dateToUnix(int year, int month, int days, int hour, int min, int sec) {
        YearMonth yearMonthObject = YearMonth.of(year, month);
        Integer daysInMonth = yearMonthObject.lengthOfMonth();

        if (days > daysInMonth) {
            days = 1;
            if (month == 12) {
                month = 1;
                year++;
            } else {
                month++;
            }
        }

        if (month > 12) {
            month = 1;
            year++;
        }

        LocalDateTime dateTime = LocalDateTime.of(year, month, days, hour, min, sec);
        return dateTime.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
    }
}
