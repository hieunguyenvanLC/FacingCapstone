package capstone.fps.controller;

import capstone.fps.common.Fix;
import capstone.fps.model.Response;
//import capstone.fps.model.home.*;
import capstone.fps.model.home.MdlChartData;
import capstone.fps.model.home.MdlReportSummary;
import capstone.fps.model.home.MdlReportSummaryDetail;
import capstone.fps.model.order.MdlOrder;
import capstone.fps.service.HomeService;
import capstone.fps.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.WeekFields;
import java.util.*;

@Controller
public class HomeController extends AbstractController {
    private static final String API = Fix.MAP_API + "/report";

    private HomeService homeService;
    private OrderService orderService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
        this.orderService = orderService;
    }

    @GetMapping(Fix.MAP_ANY + API + "/summary")
    public String showSummaryReport(@RequestParam("mon") Integer mon, @RequestParam("year") Integer year/*, @RequestParam("day") Integer day*/) {
        MdlReportSummary summary = new MdlReportSummary();
        Response response = new Response<MdlReportSummary>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        long endUnix = System.currentTimeMillis();
        Calendar now = unixToCalendar(endUnix);
        int nowWeek = now.get(Calendar.WEEK_OF_YEAR);
//        long startTodayUnix = this.dateToUnix(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        long startTodayUnix = this.dayToUnix();
        long startTWeekUnix = this.weekToUnix(now.get(Calendar.YEAR), nowWeek);
//        long startTMonthUnix = this.dateToUnix(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, 1, 0, 0, 0);
        long startTMonthUnix = this.MonthToUnix();

        //Shipper
        summary.setShipperCount(this.homeService.countShipper()); //Count All Shipper
        summary.setShipperCountTDay(this.homeService.countShipper(startTodayUnix, endUnix));
        summary.setShipperCountTWeek(this.homeService.countShipper(startTWeekUnix, endUnix));
        summary.setShipperCountTMonth(this.homeService.countShipper(startTMonthUnix, endUnix));

//        summary.setNewCustomerCount(this.homeService.countNewCus(mon, year));

        //Customer
        summary.setCustomerCount(this.homeService.countCus());  //Count All Customer
        summary.setCustomerCountTDay(this.homeService.countCus(startTodayUnix, endUnix));  //Count  Customer Day
        summary.setCustomerCountTWeek(this.homeService.countCus(startTWeekUnix, endUnix));  //Count  Customer Week
        summary.setCustomerCountTMonth(this.homeService.countCus(startTMonthUnix, endUnix));  //Count  Customer Month

//        summary.setNewOrderCount(this.homeService.countNewOrder(mon, year));

        //Order
        summary.setOrderCount(this.homeService.countOrder());   //Count All Order
        summary.setOrderCountTDay(this.homeService.countOrderBy(startTodayUnix, endUnix));  //Day
        summary.setOrderCountTWeek(this.homeService.countOrderBy(startTWeekUnix, endUnix));  //Week
        summary.setOrderCountTMonth(this.homeService.countOrderBy(startTMonthUnix, endUnix));  //Month
        //Store
//        summary.setNewStoreCount(this.homeService.countNewStore(mon,year));
        summary.setStoreCount(this.homeService.countStore());   //Count All Store
        summary.setStoreCountTDay(this.homeService.countStore(startTodayUnix, endUnix));
        summary.setStoreCountTWeek(this.homeService.countStore(startTWeekUnix, endUnix));
        summary.setStoreCountTMonth(this.homeService.countStore(startTMonthUnix, endUnix));

        ///
        summary.setSoldProductCount(this.homeService.sumProductByOrder());
        summary.setSuccessRate(this.homeService.allSuccessRate());
        summary.setTotalAmount(this.homeService.sumTotalAmount());
        summary.setPaidShipper(this.homeService.sumShipperEarn());


        // Success Rate
        summary.setSuccessRateTDay(this.homeService.successRate(startTodayUnix, endUnix));
        summary.setSuccessRateTWeek(this.homeService.successRate(startTWeekUnix, endUnix));
        summary.setSuccessRateTMonth(this.homeService.successRate(startTMonthUnix, endUnix));
        // Total Amount
        summary.setTotalAmountTDay(this.homeService.sumTotalAmount(startTodayUnix, endUnix));
        summary.setTotalAmountTWeek(this.homeService.sumTotalAmount(startTWeekUnix, endUnix));
        summary.setTotalAmountTMonth(this.homeService.sumTotalAmount(startTMonthUnix, endUnix));
        // Paid Shipper Amount
        summary.setPaidShipperTDay(this.homeService.sumShipperEarn(startTodayUnix, endUnix));
        summary.setPaidShipperTWeek(this.homeService.sumShipperEarn(startTWeekUnix, endUnix));
        summary.setPaidShipperTMonth(this.homeService.sumShipperEarn(startTMonthUnix, endUnix));
        // Sold Products
        summary.setSoldProductCountTDay(this.homeService.sumProductByOrder(startTodayUnix, endUnix));
        summary.setSoldProductCountTWeek(this.homeService.sumProductByOrder(startTWeekUnix, endUnix));
        summary.setSoldProductCountTMonth(this.homeService.sumProductByOrder(startTMonthUnix, endUnix));

        response.setResponse(Response.STATUS_SUCCESS, "", summary);
        return gson.toJson(response);
    }

//    @GetMapping(Fix.MAP_ANY + API + "/canceledorderchart")
//    public String cancelOrderChart(@RequestParam("type") Integer type, @RequestParam("start") Long start, @RequestParam("end") Long end) {
//        Response response = new Response<MdlChartData<Integer>>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
//        Boolean isCancel = false;
//        String errorMsg = "";
//
//        Calendar startCal = unixToCalendar(start); // neu start la milisec thi khong can nhan 1000
//        int startYear = startCal.get(Calendar.YEAR);
//        int startMonth = startCal.get(Calendar.MONTH) + 1; // start from 0, mon 0 = thang 1
//        int startDay = startCal.get(Calendar.DAY_OF_MONTH);
//
//        Calendar endCal = unixToCalendar(end); // neu start la milisec thi khong can nhan 1000
//        int endYear = endCal.get(Calendar.YEAR);
//        int endMonth = endCal.get(Calendar.MONTH) + 1;
//        int endDay = endCal.get(Calendar.DAY_OF_MONTH);
//
//        MdlChartData<Integer> chartData = new MdlChartData<Integer>();
//        List<String> labels = new ArrayList<String>();
//        List<Integer> orders = new ArrayList<Integer>();
//
//        if (type == 0) { // neu la  theo tuan //ok
//            int days = diffDay(startCal, endCal);
//            if (days <= 31) {
//                Calendar dateIdx = (Calendar) startCal.clone();
//                for (; dateIdx.compareTo(endCal) <= 0; dateIdx.add(Calendar.DAY_OF_MONTH, 1)) {
//                    labels.add(formatDate(dateIdx, "dd/MM/yyyy"));
//                    long startUnix = dateIdx.getTimeInMillis();
//                    Calendar tempEnd = (Calendar) dateIdx.clone();
//                    tempEnd.add(Calendar.DAY_OF_MONTH, 1);
//                    long endUnix = tempEnd.getTimeInMillis();
//                    orders.add(this.homeService.countOrderCancelBy(startUnix, endUnix)); // Gia su startMonth = endMonth
//                }
//                isCancel = true;
//            } else {
//                errorMsg = "The number of days is out of range (Only accept less than or equal 31 days)!";
//            }
//
//        } else if (type == 1) {
//            // Neu la week
//            int weeks = diffWeek(startCal, endCal);
//            int startWeek = startCal.get(Calendar.WEEK_OF_YEAR);
//            if (weeks <= 52) {
//                for (Integer weekIdx = 0; weekIdx < weeks; weekIdx++) {
//                    Integer curWeek = startWeek + weekIdx > 52 ? weekIdx : startWeek + weekIdx;
//                    Integer curYear = startWeek + weekIdx > 12 ? endYear : startYear; // boi vi gioi han la 12 thang nen chi co 2 nam lien tiep nhau (vd 2018 - 2019)
//                    labels.add(String.format("%02d", curWeek) + "/" + curYear.toString());
//                    long startUnix = this.weekToUnix(curYear, curWeek);
//                    long endUnix = this.weekToUnix(curYear, curWeek + 1);
//                    orders.add(this.homeService.countOrderCancelBy(startUnix, endUnix));
//                }
//                isCancel = true;
//            } else {
//                errorMsg = "The number of weeks is out of range (Only accept less than or equal 52 weeks)!";
//            }
//        } else if (type == 2) { // neu la theo thang
//            int months = diffMon(startCal, endCal);
//            if (months <= 12) {
//                for (Integer monIdx = 0; monIdx <= months; monIdx++) {
//                    Integer curMon = startMonth + monIdx > 12 ? monIdx : startMonth + monIdx;
//                    Integer curYear = startMonth + monIdx > 12 ? endYear : startYear; // boi vi gioi han la 12 thang nen chi co 2 nam lien tiep nhau (vd 2018 - 2019)
//                    labels.add(String.format("%02d", curMon) + "/" + curYear.toString());
//                    long startUnix = this.dateToUnix(curYear, curMon, 1, 0, 0, 0);
//                    long endUnix = this.dateToUnix(curYear, curMon + 1, 1, 0, 0, 0);
//                    orders.add(this.homeService.countOrderCancelBy(startUnix, endUnix));
//                }
//                isCancel = true;
//            } else {
//                errorMsg = "The number of months is out of range (Only accept less than or equal 12 months)!";
//            }
//        } else { // neu la theo nam
//            if (endYear - startYear < 30) {
//                for (Integer year = startYear; year <= endYear; year++) {
//                    labels.add(year.toString());
//                    long startUnix = this.dateToUnix(year, 1, 1, 0, 0, 0);
//                    long endUnix = this.dateToUnix(year + 1, 1, 1, 0, 0, 0);
//                    orders.add(this.homeService.countOrderCancelBy(startUnix, endUnix)); // Gia su startMonth = endMonth
//                }
//                isCancel = true;
//            } else {
//                errorMsg = "The number of years is out of range (Only accept less than or equal 30 years)!";
//            }
//        }
//
//        if (isCancel) {
//            chartData.setLabels(labels);
//            chartData.setData(orders);
//
//            response.setResponse(Response.STATUS_SUCCESS, "", chartData);
//        } else {
//            response.setResponse(Response.STATUS_FAIL, errorMsg);
//        }
//        return gson.toJson(response);
//    }
//
//    @GetMapping(Fix.MAP_ANY + API + "/successorderchart")
//    public String successOrderChart(@RequestParam("type") Integer type, @RequestParam("start") Long start, @RequestParam("end") Long end) {
//        Response response = new Response<MdlChartData<Integer>>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
//
//        Boolean isSuccessOrder = false;
//        String errorMsg = "";
//        Calendar startCal = unixToCalendar(start);
//        int startYear = startCal.get(Calendar.YEAR);
//        int startMonth = startCal.get(Calendar.MONTH) + 1;
//        int startDay = startCal.get(Calendar.DAY_OF_MONTH);
//
//        Calendar endCal = unixToCalendar(end);
//        int endYear = endCal.get(Calendar.YEAR);
//        int endMonth = endCal.get(Calendar.MONTH) + 1;
//        int endDay = endCal.get(Calendar.DAY_OF_MONTH);
//
//        MdlChartData<Integer> chartData = new MdlChartData<Integer>();
//        List<String> labels = new ArrayList<String>();
//        List<Integer> orders = new ArrayList<Integer>();
//
//        if (type == 0) { // neu la  theo ngay //ok
//            int days = diffDay(startCal, endCal);
//            if (days <= 31) {
//                Calendar dateIdx = (Calendar) startCal.clone();
//                for (; dateIdx.compareTo(endCal) <= 0; dateIdx.add(Calendar.DAY_OF_MONTH, 1)) {
//                    labels.add(formatDate(dateIdx, "dd/MM/yyyy"));
//                    long startUnix = dateIdx.getTimeInMillis();
//                    Calendar tempEnd = (Calendar) dateIdx.clone();
//                    tempEnd.add(Calendar.DAY_OF_MONTH, 1);
//                    long endUnix = tempEnd.getTimeInMillis();
//                    orders.add(this.homeService.countOrderSuccessBy(startUnix, endUnix));
//                }
//                isSuccessOrder = true;
//            } else {
//                errorMsg = "The number of days is out of range (Only accept less than or equal 31 days)!";
//            }
//        } else if (type == 1) {
//            //If is week
//            int weeks = diffWeek(startCal, endCal);
//            int startWeek = startCal.get(Calendar.WEEK_OF_YEAR);
//            if (weeks <= 52) {
//                for (Integer weekIdx = 0; weekIdx < weeks; weekIdx++) {
//                    Integer curWeek = startWeek + weekIdx > 52 ? weekIdx : startWeek + weekIdx;
//                    Integer curYear = startWeek + weekIdx > 12 ? endYear : startYear; // boi vi gioi han la 12 thang nen chi co 2 nam lien tiep nhau (vd 2018 - 2019)
//                    labels.add(String.format("%02d", curWeek) + "/" + curYear.toString());
//                    long startUnix = this.weekToUnix(curYear, curWeek);
//                    long endUnix = this.weekToUnix(curYear, curWeek + 1);
//                    orders.add(this.homeService.countOrderSuccessBy(startUnix, endUnix));
//                }
//                isSuccessOrder = true;
//            } else {
//                errorMsg = "The number of days is out of range (Only accept less than or equal 31 days)!";
//            }
//        } else if (type == 2) { // neu la theo thang
//            int months = diffMon(startCal, endCal);
//            if (months <= 12) {
//                for (Integer monIdx = 0; monIdx <= months; monIdx++) {
//                    Integer curMon = startMonth + monIdx > 12 ? monIdx : startMonth + monIdx;
//                    Integer curYear = startMonth + monIdx > 12 ? endYear : startYear; // boi vi gioi han la 12 thang nen chi co 2 nam lien tiep nhau (vd 2018 - 2019)
//                    labels.add(String.format("%02d", curMon) + "/" + curYear.toString());
//                    long startUnix = this.dateToUnix(curYear, curMon, 1, 0, 0, 0);
//                    long endUnix = this.dateToUnix(curYear, curMon + 1, 1, 0, 0, 0);
//                    orders.add(this.homeService.countOrderSuccessBy(startUnix, endUnix));
//                }
//                isSuccessOrder = true;
//            } else {
//                errorMsg = "The number of months is out of range (Only accept less than or equal 12 months)!";
//            }
//        } else { // neu la theo nam
//            if (endYear - startYear < 30) {
//                for (Integer year = startYear; year <= endYear; year++) {
//                    labels.add(year.toString());
//                    long startUnix = this.dateToUnix(year, 1, 1, 0, 0, 0);
//                    long endUnix = this.dateToUnix(year + 1, 1, 1, 0, 0, 0);
//                    orders.add(this.homeService.countOrderSuccessBy(startUnix, endUnix)); // Gia su startMonth = endMonth
//                }
//                isSuccessOrder = true;
//            } else {
//                errorMsg = "The number of years is out of range (Only accept less than or equal 30 years)!";
//            }
//        }
//
//        if (isSuccessOrder) {
//            chartData.setLabels(labels);
//            chartData.setData(orders);
//
//            response.setResponse(Response.STATUS_SUCCESS, "", chartData);
//        } else {
//            response.setResponse(Response.STATUS_FAIL, errorMsg);
//        }
//        return gson.toJson(response);
//    }

    @GetMapping(Fix.MAP_ANY + API + "/soldproductchart")
    public String productSoldChart(@RequestParam("type") Integer type, @RequestParam("start") Long start, @RequestParam("end") Long end) {
        Response response = new Response<MdlChartData<Integer>>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        Boolean isSold = false;
        String errorMsg = "";

        Calendar startCal = unixToCalendar(start); // neu start la milisec thi khong can nhan 1000
        int startYear = startCal.get(Calendar.YEAR);
        int startMonth = startCal.get(Calendar.MONTH) + 1; // start from 0, mon 0 = thang 1
        int startDay = startCal.get(Calendar.DAY_OF_MONTH);

        Calendar endCal = unixToCalendar(end); // neu start la milisec thi khong can nhan 1000
        int endYear = endCal.get(Calendar.YEAR);
        int endMonth = endCal.get(Calendar.MONTH) + 1;
        int endDay = endCal.get(Calendar.DAY_OF_MONTH);
        MdlChartData<Integer[]> chartData = new MdlChartData<Integer[]>();
        List<String> labels = new ArrayList<String>();
        List<Integer[]> orders = new ArrayList<Integer[]>();

        if (type == 0) { // neu la  theo tuan //ok
            int days = diffDay(startCal, endCal);
            if (days <= 31) {
                Calendar dateIdx = (Calendar) startCal.clone();
                for (; dateIdx.compareTo(endCal) <= 0; dateIdx.add(Calendar.DAY_OF_MONTH, 1)) {
                    long startUnix = dateIdx.getTimeInMillis();
                    Calendar tempEnd = (Calendar) dateIdx.clone();
                    tempEnd.add(Calendar.DAY_OF_MONTH, 1);
                    long endUnix = tempEnd.getTimeInMillis();
                    Integer[] cols = {
                            this.homeService.sumProductByOrder(startUnix, endUnix)
                    };
                    labels.add(formatDate(dateIdx, "dd/MM/yyyy"));
                    orders.add(cols); // Gia su startMonth = endMonth
                }
                isSold = true;
            } else {
                errorMsg = "The number of days is out of range (Only accept less than or equal 31 days)!";
            }

        } else if (type == 1) {
            // Neu la week
            int weeks = diffWeek(startCal, endCal);
            int startWeek = startCal.get(Calendar.WEEK_OF_YEAR);
            if (weeks <= 52) {
                for (Integer weekIdx = 0; weekIdx < weeks; weekIdx++) {
                    Integer curWeek = startWeek + weekIdx > 52 ? weekIdx : startWeek + weekIdx;
                    Integer curYear = startWeek + weekIdx > 12 ? endYear : startYear; // boi vi gioi han la 12 thang nen chi co 2 nam lien tiep nhau (vd 2018 - 2019)
                    long startUnix = this.weekToUnix(curYear, curWeek);
                    long endUnix = this.weekToUnix(curYear, curWeek + 1);
                    Integer[] cols = {
                            this.homeService.sumProductByOrder(startUnix, endUnix)
                    };
                    labels.add(String.format("%02d", curWeek) + "/" + curYear.toString());
                    orders.add(cols); // Gia su startMonth = endMonth

                }
                isSold = true;
            } else {
                errorMsg = "The number of weeks is out of range (Only accept less than or equal 52 weeks)!";
            }
        } else if (type == 2) { // neu la theo thang
            // neu la theo thang
            int months = diffMon(startCal, endCal);

            if (months <= 12) {
                for (Integer monIdx = 0; monIdx <= months; monIdx++) {
                    Integer curMon = startMonth + monIdx > 12 ? monIdx : startMonth + monIdx;
                    Integer curYear = startMonth + monIdx > 12 ? endYear : startYear; // boi vi gioi han la 12 thang nen chi co 2 nam lien tiep nhau (vd 2018 - 2019)
                    long startUnix = this.dateToUnix(curYear, curMon, 1, 0, 0, 0);
                    long endUnix = this.dateToUnix(curYear, curMon + 1, 1, 0, 0, 0);
                    Integer[] cols = {
                            this.homeService.sumProductByOrder(startUnix, endUnix)
                    };
                    labels.add(String.format("%02d", curMon) + "/" + curYear.toString());
                    orders.add(cols); // Gia su startMonth = endMonth

                }
                isSold = true;
            } else {
                errorMsg = "The number of months is out of range (Only accept less than or equal 12 months)!";
            }
        } else { // neu la theo nam
            if (endYear - startYear < 30) {
                for (Integer year = startYear; year <= endYear; year++) {
                    long startUnix = this.dateToUnix(year, 1, 1, 0, 0, 0);
                    long endUnix = this.dateToUnix(year + 1, 1, 1, 0, 0, 0);
                    Integer[] cols = {
                            this.homeService.sumProductByOrder(startUnix, endUnix)
                    };
                    labels.add(year.toString());
                    orders.add(cols); // Gia su startMonth = endMonth

                }
                isSold = true;
            } else {
                errorMsg = "The number of years is out of range (Only accept less than or equal 30 years)!";
            }
        }


        if (isSold) {
            chartData.setLabels(labels);
            chartData.setData(orders);

            response.setResponse(Response.STATUS_SUCCESS, "", chartData);
        } else {
            response.setResponse(Response.STATUS_FAIL, errorMsg);
        }

        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ANY + API + "/successratechart")
    public String rateSuccesfull(@RequestParam("type") Integer type, @RequestParam("start") Long start, @RequestParam("end") Long end) {
        Response response = new Response<MdlChartData<Double>>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        Boolean isSuccess = false;
        String errorMsg = "";

        Calendar startCal = unixToCalendar(start); // neu start la milisec thi khong can nhan 1000
        int startYear = startCal.get(Calendar.YEAR);
        int startMonth = startCal.get(Calendar.MONTH) + 1; // start from 0, mon 0 = thang 1
        int startDay = startCal.get(Calendar.DAY_OF_MONTH);


        Calendar endCal = unixToCalendar(end); // neu start la milisec thi khong can nhan 1000
        int endYear = endCal.get(Calendar.YEAR);
        int endMonth = endCal.get(Calendar.MONTH) + 1;
        int endDay = endCal.get(Calendar.DAY_OF_MONTH);

        MdlChartData<Double[]> chartData = new MdlChartData<Double[]>();
        List<String> labels = new ArrayList<String>();
        List<Double[]> orders = new ArrayList<Double[]>();

        if (type == 0) { // neu la  theo tuan //ok
            int days = diffDay(startCal, endCal);
            if (days <= 31) {
                Calendar dateIdx = (Calendar) startCal.clone();
                for (; dateIdx.compareTo(endCal) <= 0; dateIdx.add(Calendar.DAY_OF_MONTH, 1)) {

                    long startUnix = dateIdx.getTimeInMillis();
                    Calendar tempEnd = (Calendar) dateIdx.clone();
                    tempEnd.add(Calendar.DAY_OF_MONTH, 1);
                    long endUnix = tempEnd.getTimeInMillis();

                    Integer success = this.homeService.countOrderSuccessBy(startUnix, endUnix);
                    Integer canceled = this.homeService.countOrderCancelBy(startUnix, endUnix);
                    Double total = (success + canceled) * 1.0D;
                    if (total != 0) {
                        Double[] cols = {
                                success / total * 100.0D,
                                canceled / total * 100.0D,
                        };
                        orders.add(cols);
                    } else {
                        Double[] cols = {
                                0.0D,
                                0.0D
                        };
                        orders.add(cols);
                    } // Gia su startMonth = endMonth
                    labels.add(formatDate(dateIdx, "dd/MM/yyyy"));
                }
                isSuccess = true;
            } else {
                errorMsg = "The number of days is out of range (Only accept less than or equal 31 days)!";
            }
        } else if (type == 1) {
            // Neu la week
            int weeks = diffWeek(startCal, endCal);
            int startWeek = startCal.get(Calendar.WEEK_OF_YEAR);
            if (weeks <= 52) {
                for (Integer weekIdx = 0; weekIdx < weeks; weekIdx++) {
                    Integer curWeek = startWeek + weekIdx > 52 ? weekIdx : startWeek + weekIdx;
                    Integer curYear = startWeek + weekIdx > 12 ? endYear : startYear; // boi vi gioi han la 12 thang nen chi co 2 nam lien tiep nhau (vd 2018 - 2019)
                    long startUnix = this.weekToUnix(curYear, curWeek);
                    long endUnix = this.weekToUnix(curYear, curWeek + 1);

                    Integer success = this.homeService.countOrderSuccessBy(startUnix, endUnix);
                    Integer canceled = this.homeService.countOrderCancelBy(startUnix, endUnix);
                    Double total = (success + canceled) * 1.0D;
                    if (total != 0) {
                        Double[] cols = {
                                success / total * 100.0D,
                                canceled / total * 100.0D,
                        };
                        orders.add(cols);
                    } else {
                        Double[] cols = {
                                0.0D,
                                0.0D
                        };
                        orders.add(cols);
                    } // Gia su startMonth = endMonth
                    labels.add(String.format("%02d", curWeek) + "/" + curYear.toString());
                }
                isSuccess = true;
            } else {
                errorMsg = "The number of weeks is out of range (Only accept less than or equal 52 weeks)!";
            }
        } else if (type == 2) {
            // Neu la months
            int months = diffMon(startCal, endCal);

            if (months <= 12) {
                for (Integer monIdx = 0; monIdx <= months; monIdx++) {
                    Integer curMon = startMonth + monIdx > 12 ? monIdx : startMonth + monIdx;
                    Integer curYear = startMonth + monIdx > 12 ? endYear : startYear; // boi vi gioi han la 12 thang nen chi co 2 nam lien tiep nhau (vd 2018 - 2019)
                    long startUnix = this.dateToUnix(curYear, curMon, 1, 0, 0, 0);
                    long endUnix = this.dateToUnix(curYear, curMon + 1, 1, 0, 0, 0);

                    Integer success = this.homeService.countOrderSuccessBy(startUnix, endUnix);
                    Integer canceled = this.homeService.countOrderCancelBy(startUnix, endUnix);
                    Double total = (success + canceled) * 1.0D;
                    if (total != 0) {
                        Double[] cols = {
                                success / total * 100.0D,
                                canceled / total * 100.0D,
                        };
                        orders.add(cols);
                    } else {
                        Double[] cols = {
                                0.0D,
                                0.0D
                        };
                        orders.add(cols);
                    } // Gia su startMonth = endMonth
                    labels.add(String.format("%02d", curMon) + "/" + curYear.toString());
                }
                isSuccess = true;
            } else {
                errorMsg = "The number of weeks is out of range (Only accept less than or equal 52 weeks)!";
            }
        } else {
            if (endYear - startYear < 30) {
                for (Integer year = startYear; year <= endYear; year++) {

                    long startUnix = this.dateToUnix(year, 1, 1, 0, 0, 0);
                    long endUnix = this.dateToUnix(year + 1, 1, 1, 0, 0, 0);

                    Integer success = this.homeService.countOrderSuccessBy(startUnix, endUnix);
                    Integer canceled = this.homeService.countOrderCancelBy(startUnix, endUnix);
                    Double total = (success + canceled) * 1.0D;
                    if (total != 0) {
                        Double[] cols = {
                                success / total * 100.0D,
                                canceled / total * 100.0D,
                        };
                        orders.add(cols);
                    } else {
                        Double[] cols = {
                                0.0D,
                                0.0D
                        };
                        orders.add(cols);
                    } // Gia su startMonth = endMonth
                    labels.add(year.toString());
                }
                isSuccess = true;
            } else {
                errorMsg = "The number of years is out of range (Only accept less than or equal 30 years)!";
            }
        }

        if (isSuccess) {
            chartData.setLabels(labels);
            chartData.setData(orders);

            response.setResponse(Response.STATUS_SUCCESS, "", chartData);
        } else {
            response.setResponse(Response.STATUS_FAIL, errorMsg);
        }

        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ANY + API + "/incomeammountchart")
    public String totalAmount(@RequestParam("type") Integer type, @RequestParam("start") Long start, @RequestParam("end") Long end) {
        Response response = new Response<MdlChartData<Integer>>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        Boolean isSuccess = false;
        String errorMsg = "";

        Calendar startCal = unixToCalendar(start); // neu start la milisec thi khong can nhan 1000
        int startYear = startCal.get(Calendar.YEAR);
        int startMonth = startCal.get(Calendar.MONTH) + 1; // start from 0, mon 0 = thang 1
        int startDay = startCal.get(Calendar.DAY_OF_MONTH);

        Calendar endCal = unixToCalendar(end); // neu start la milisec thi khong can nhan 1000
        int endYear = endCal.get(Calendar.YEAR);
        int endMonth = endCal.get(Calendar.MONTH) + 1;
        int endDay = endCal.get(Calendar.DAY_OF_MONTH);

        MdlChartData<Integer[]> chartData = new MdlChartData<Integer[]>();
        List<String> labels = new ArrayList<String>();
        List<Integer[]> orders = new ArrayList<Integer[]>();

        if (type == 0) { // neu la  theo ngay //ok
            int days = diffDay(startCal, endCal);
            if (days <= 31) {
                Calendar dateIdx = (Calendar) startCal.clone();
                for (; dateIdx.compareTo(endCal) <= 0; dateIdx.add(Calendar.DAY_OF_MONTH, 1)) {
                    long startUnix = dateIdx.getTimeInMillis();
                    Calendar tempEnd = (Calendar) dateIdx.clone();
                    tempEnd.add(Calendar.DAY_OF_MONTH, 1);
                    long endUnix = tempEnd.getTimeInMillis();
                    Integer[] cols = {
                            this.homeService.sumTotalAmount(startUnix, endUnix),
                            this.homeService.sumShipperEarn(startUnix, endUnix)
                    };
                    labels.add(formatDate(dateIdx, "dd/MM/yyyy"));
                    orders.add(cols); // Gia su startMonth = endMonth
                }
                isSuccess = true;
            } else {
                errorMsg = "The number of days is out of range (Only accept less than or equal 31 days)!";
            }
        } else if (type == 1) {
            // Neu la week
            int weeks = diffWeek(startCal, endCal);
            int startWeek = startCal.get(Calendar.WEEK_OF_YEAR);
            if (weeks <= 52) {
                for (Integer weekIdx = 0; weekIdx < weeks; weekIdx++) {
                    Integer curWeek = startWeek + weekIdx > 52 ? weekIdx : startWeek + weekIdx;
                    Integer curYear = startWeek + weekIdx > 12 ? endYear : startYear; // boi vi gioi han la 12 thang nen chi co 2 nam lien tiep nhau (vd 2018 - 2019)
                    long startUnix = this.weekToUnix(curYear, curWeek);
                    long endUnix = this.weekToUnix(curYear, curWeek + 1);
                    Integer[] cols = {
                            this.homeService.sumTotalAmount(startUnix, endUnix),
                            this.homeService.sumShipperEarn(startUnix, endUnix)
                    };
                    labels.add(String.format("%02d", curWeek) + "/" + curYear.toString());
                    orders.add(cols); // Gia su startMonth = endMonth

                }
                isSuccess = true;
            } else {
                errorMsg = "The number of weeks is out of range (Only accept less than or equal 52 weeks)!";
            }
        } else if (type == 2) { // neu la theo thang
            int months = diffMon(startCal, endCal);

            if (months <= 12) {
                for (Integer monIdx = 0; monIdx <= months; monIdx++) {
                    Integer curMon = startMonth + monIdx > 12 ? monIdx : startMonth + monIdx;
                    Integer curYear = startMonth + monIdx > 12 ? endYear : startYear; // boi vi gioi han la 12 thang nen chi co 2 nam lien tiep nhau (vd 2018 - 2019)
                    long startUnix = this.dateToUnix(curYear, curMon, 1, 0, 0, 0);
                    long endUnix = this.dateToUnix(curYear, curMon + 1, 1, 0, 0, 0);
                    Integer[] cols = {
                            this.homeService.sumTotalAmount(startUnix, endUnix),
                            this.homeService.sumShipperEarn(startUnix, endUnix)
                    };
                    labels.add(String.format("%02d", curMon) + "/" + curYear.toString());
                    orders.add(cols); // Gia su startMonth = endMonth

                }
                isSuccess = true;
            } else {
                errorMsg = "The number of months is out of range (Only accept less than or equal 12 months)!";
            }
        } else { // neu la theo nam
            if (endYear - startYear < 30) {
                for (Integer year = startYear; year <= endYear; year++) {
                    long startUnix = this.dateToUnix(year, 1, 1, 0, 0, 0);
                    long endUnix = this.dateToUnix(year + 1, 1, 1, 0, 0, 0);
                    Integer[] cols = {
                            this.homeService.sumTotalAmount(startUnix, endUnix),
                            this.homeService.sumShipperEarn(startUnix, endUnix)
                    };
                    labels.add(year.toString());
                    orders.add(cols); // Gia su startMonth = endMonth
                }
                isSuccess = true;
            } else {
                errorMsg = "The number of years is out of range (Only accept less than or equal 30 years)!";
            }
        }

        if (isSuccess) {
            chartData.setLabels(labels);
            chartData.setData(orders);

            response.setResponse(Response.STATUS_SUCCESS, "", chartData);
        } else {
            response.setResponse(Response.STATUS_FAIL, errorMsg);
        }

        return gson.toJson(response);
    }

//    @GetMapping(Fix.MAP_ANY + API + "/paidshipperchart")
//    public String paidShipper(@RequestParam("type") Integer type, @RequestParam("start") Long start, @RequestParam("end") Long end) {
//        /* type tuong ung 0-tuan, 1-thang, 2-nam */
//        /* start: kieu unix time dung de filter */
//        /* end: kieu unix time */
//        Response response = new Response<MdlChartData<Integer>>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
//        Boolean isSuccess = false;
//        String errorMsg = "";
//
//        Calendar startCal = unixToCalendar(start); // neu start la milisec thi khong can nhan 1000
//        int startYear = startCal.get(Calendar.YEAR);
//        int startMonth = startCal.get(Calendar.MONTH) + 1; // start from 0, mon 0 = thang 1
//        int startDay = startCal.get(Calendar.DAY_OF_MONTH);
//
//        Calendar endCal = unixToCalendar(end); // neu start la milisec thi khong can nhan 1000
//        int endYear = endCal.get(Calendar.YEAR);
//        int endMonth = endCal.get(Calendar.MONTH) + 1;
//        int endDay = endCal.get(Calendar.DAY_OF_MONTH);
//
//        MdlChartData<Integer> chartData = new MdlChartData<Integer>();
//        List<String> labels = new ArrayList<String>();
//        List<Integer> orders = new ArrayList<Integer>();
//
//        if (type == 0) { // neu la  theo ngay //ok
//            int days = diffDay(startCal, endCal);
//            if (days <= 31) {
//                Calendar dateIdx = (Calendar) startCal.clone();
//                for (; dateIdx.compareTo(endCal) <= 0; dateIdx.add(Calendar.DAY_OF_MONTH, 1)) {
//                    labels.add(formatDate(dateIdx, "dd/MM/yyyy"));
//                    long startUnix = dateIdx.getTimeInMillis();
//                    Calendar tempEnd = (Calendar) dateIdx.clone();
//                    tempEnd.add(Calendar.DAY_OF_MONTH, 1);
//                    long endUnix = tempEnd.getTimeInMillis();
//                    orders.add(this.homeService.sumShipperEarn(startUnix, endUnix)); // Gia su startMonth = endMonth
//                }
//                isSuccess = true;
//            } else {
//                errorMsg = "The number of days is out of range (Only accept less than or equal 31 days)!";
//            }
//        } else if (type == 1) {
//            // Neu la week
//            int weeks = diffWeek(startCal, endCal);
//            int startWeek = startCal.get(Calendar.WEEK_OF_YEAR);
//            if (weeks <= 52) {
//                for (Integer weekIdx = 0; weekIdx < weeks; weekIdx++) {
//                    Integer curWeek = startWeek + weekIdx > 52 ? weekIdx : startWeek + weekIdx;
//                    Integer curYear = startWeek + weekIdx > 12 ? endYear : startYear; // boi vi gioi han la 12 thang nen chi co 2 nam lien tiep nhau (vd 2018 - 2019)
//                    labels.add(String.format("%02d", curWeek) + "/" + curYear.toString());
//                    long startUnix = this.weekToUnix(curYear, curWeek);
//                    long endUnix = this.weekToUnix(curYear, curWeek + 1);
//                    orders.add(this.homeService.sumShipperEarn(startUnix, endUnix));
//                }
//                isSuccess = true;
//            } else {
//                errorMsg = "The number of weeks is out of range (Only accept less than or equal 52 weeks)!";
//            }
//        } else if (type == 2) { // neu la theo thang
//            int months = diffMon(startCal, endCal);
//
//            if (months <= 12) {
//                for (Integer monIdx = 0; monIdx <= months; monIdx++) {
//                    Integer curMon = startMonth + monIdx > 12 ? monIdx : startMonth + monIdx;
//                    Integer curYear = startMonth + monIdx > 12 ? endYear : startYear; // boi vi gioi han la 12 thang nen chi co 2 nam lien tiep nhau (vd 2018 - 2019)
//                    labels.add(String.format("%02d", curMon) + "/" + curYear.toString());
//                    long startUnix = this.dateToUnix(curYear, curMon, 1, 0, 0, 0);
//                    long endUnix = this.dateToUnix(curYear, curMon + 1, 1, 0, 0, 0);
//                    orders.add(this.homeService.sumShipperEarn(startUnix, endUnix));
//                }
//                isSuccess = true;
//            } else {
//                errorMsg = "The number of months is out of range (Only accept less than or equal 12 months)!";
//            }
//        } else { // neu la theo nam
//            if (endYear - startYear < 30) {
//                for (Integer year = startYear; year <= endYear; year++) {
//                    labels.add(year.toString());
//                    long startUnix = this.dateToUnix(year, 1, 1, 0, 0, 0);
//                    long endUnix = this.dateToUnix(year + 1, 1, 1, 0, 0, 0);
//                    orders.add(this.homeService.sumShipperEarn(startUnix, endUnix)); // Gia su startMonth = endMonth
//                }
//                isSuccess = true;
//            } else {
//                errorMsg = "The number of years is out of range (Only accept less than or equal 30 years)!";
//            }
//        }
//
//        if (isSuccess) {
//            chartData.setLabels(labels);
//            chartData.setData(orders);
//
//            response.setResponse(Response.STATUS_SUCCESS, "", chartData);
//        } else {
//            response.setResponse(Response.STATUS_FAIL, errorMsg);
//        }
//
//        return gson.toJson(response);
//
//    }

    @GetMapping(Fix.MAP_ANY + API + "/orderchart")
    public String showOrderChart(@RequestParam("type") Integer type, @RequestParam("start") Long start, @RequestParam("end") Long end) {
        /* type tuong ung 0-tuan, 1-thang, 2-nam */
        /* start: kieu unix time dung de filter */
        /* end: kieu unix time */
        Response response = new Response<MdlChartData<Integer>>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        Boolean isSuccess = false;
        String errorMsg = "";

        Calendar startCal = unixToCalendar(start); // neu start la milisec thi khong can nhan 1000
        int startYear = startCal.get(Calendar.YEAR);
        int startMonth = startCal.get(Calendar.MONTH) + 1; // start from 0, mon 0 = thang 1
        int startDay = startCal.get(Calendar.DAY_OF_MONTH);

        Calendar endCal = unixToCalendar(end); // neu start la milisec thi khong can nhan 1000
        int endYear = endCal.get(Calendar.YEAR);
        int endMonth = endCal.get(Calendar.MONTH) + 1;
        int endDay = endCal.get(Calendar.DAY_OF_MONTH);

        MdlChartData<Integer[]> chartData = new MdlChartData<Integer[]>();
        List<String> labels = new ArrayList<String>();
        List<Integer[]> orders = new ArrayList<Integer[]>();

        if (type == 0) { // neu la  theo ngay //ok
            int days = diffDay(startCal, endCal);
            if (days <= 31) {
                Calendar dateIdx = (Calendar) startCal.clone();
                for (; dateIdx.compareTo(endCal) <= 0; dateIdx.add(Calendar.DAY_OF_MONTH, 1)) {
                    long startUnix = dateIdx.getTimeInMillis();
                    Calendar tempEnd = (Calendar) dateIdx.clone();
                    tempEnd.add(Calendar.DAY_OF_MONTH, 1);
                    long endUnix = tempEnd.getTimeInMillis();
                    Integer[] cols = {
                            this.homeService.countOrders(Fix.ORD_NEW.index, startUnix, endUnix),
                            this.homeService.countOrders(Fix.ORD_ASS.index, startUnix, endUnix),
                            this.homeService.countOrders(Fix.ORD_BUY.index, startUnix, endUnix),
                            this.homeService.countOrders(Fix.ORD_COM.index, startUnix, endUnix),
                            this.homeService.countOrders(Fix.ORD_CXL.index, startUnix, endUnix)
                    };
                    labels.add(formatDate(dateIdx, "dd/MM/yyyy"));
                    orders.add(cols); // Gia su startMonth = endMonth
                }
                isSuccess = true;
            } else {
                errorMsg = "The number of days is out of range (Only accept less than or equal 31 days)!";
            }
        } else if (type == 1) {
            // Neu la week
            int weeks = diffWeek(startCal, endCal);
            int startWeek = startCal.get(Calendar.WEEK_OF_YEAR);
            if (weeks <= 52) {
                for (Integer weekIdx = 0; weekIdx < weeks; weekIdx++) {
                    Integer curWeek = startWeek + weekIdx > 52 ? weekIdx : startWeek + weekIdx;
                    Integer curYear = startWeek + weekIdx > 12 ? endYear : startYear; // boi vi gioi han la 12 thang nen chi co 2 nam lien tiep nhau (vd 2018 - 2019)
                    long startUnix = this.weekToUnix(curYear, curWeek);
                    long endUnix = this.weekToUnix(curYear, curWeek + 1);
                    Integer[] cols = {
                            this.homeService.countOrders(Fix.ORD_NEW.index, startUnix, endUnix),
                            this.homeService.countOrders(Fix.ORD_ASS.index, startUnix, endUnix),
                            this.homeService.countOrders(Fix.ORD_BUY.index, startUnix, endUnix),
                            this.homeService.countOrders(Fix.ORD_COM.index, startUnix, endUnix),
                            this.homeService.countOrders(Fix.ORD_CXL.index, startUnix, endUnix)
                    };
                    labels.add(String.format("%02d", curWeek) + "/" + curYear.toString());
                    orders.add(cols);
                }
                isSuccess = true;
            } else {
                errorMsg = "The number of weeks is out of range (Only accept less than or equal 52 weeks)!";
            }
        } else if (type == 2) { // neu la theo thang
            int months = diffMon(startCal, endCal);

            if (months <= 12) {
                for (Integer monIdx = 0; monIdx <= months; monIdx++) {
                    Integer curMon = startMonth + monIdx > 12 ? monIdx : startMonth + monIdx;
                    Integer curYear = startMonth + monIdx > 12 ? endYear : startYear; // boi vi gioi han la 12 thang nen chi co 2 nam lien tiep nhau (vd 2018 - 2019)
                    long startUnix = this.dateToUnix(curYear, curMon, 1, 0, 0, 0);
                    long endUnix = this.dateToUnix(curYear, curMon + 1, 1, 0, 0, 0);
                    Integer[] cols = {
                            this.homeService.countOrders(Fix.ORD_NEW.index, startUnix, endUnix),
                            this.homeService.countOrders(Fix.ORD_ASS.index, startUnix, endUnix),
                            this.homeService.countOrders(Fix.ORD_BUY.index, startUnix, endUnix),
                            this.homeService.countOrders(Fix.ORD_COM.index, startUnix, endUnix),
                            this.homeService.countOrders(Fix.ORD_CXL.index, startUnix, endUnix)
                    };
                    labels.add(String.format("%02d", curMon) + "/" + curYear.toString());
                    orders.add(cols);
                }
                isSuccess = true;
            } else {
                errorMsg = "The number of months is out of range (Only accept less than or equal 12 months)!";
            }
        } else { // neu la theo nam
            if (endYear - startYear < 30) {
                for (Integer year = startYear; year <= endYear; year++) {
                    long startUnix = this.dateToUnix(year, 1, 1, 0, 0, 0);
                    long endUnix = this.dateToUnix(year + 1, 1, 1, 0, 0, 0);
                    Integer[] cols = {
                            this.homeService.countOrders(Fix.ORD_NEW.index, startUnix, endUnix),
                            this.homeService.countOrders(Fix.ORD_ASS.index, startUnix, endUnix),
                            this.homeService.countOrders(Fix.ORD_BUY.index, startUnix, endUnix),
                            this.homeService.countOrders(Fix.ORD_COM.index, startUnix, endUnix),
                            this.homeService.countOrders(Fix.ORD_CXL.index, startUnix, endUnix)
                    };
                    labels.add(year.toString());
                    orders.add(cols); // Gia su startMonth = endMonth
                }
                isSuccess = true;
            } else {
                errorMsg = "The number of years is out of range (Only accept less than or equal 30 years)!";
            }
        }

        if (isSuccess) {
            chartData.setLabels(labels);
            chartData.setData(orders);

            response.setResponse(Response.STATUS_SUCCESS, "", chartData);
        } else {
            response.setResponse(Response.STATUS_FAIL, errorMsg);
        }

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

        try {
            List<MdlOrder> orders = this.homeService.getOrderList(status, start, end);
            response.setResponse(Response.STATUS_SUCCESS, "", orders);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }

        return gson.toJson(response);
    }

    @GetMapping(Fix.MAP_ANY + API + "/orderdetail")
    public String getOrderDetail(Integer orderId) {
        Response<MdlOrder> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            response = orderService.getOrderDetailAdm(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(Response.STATUS_SERVER_ERROR, Response.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    private long dateToUnix(int year, int month, int days, int hour, int min, int sec) {
//        if (month > 12) {
//            month = 1;
//            year++;
//        }
//
//        YearMonth yearMonthObject = YearMonth.of(year, month);
//        Integer daysInMonth = yearMonthObject.lengthOfMonth();
//
//        if (days > daysInMonth) {
//            days = 1;
//            if (month == 12) {
//                month = 1;
//                year++;
//            } else {
//                month++;
//            }
//        }
        LocalDateTime dateTime = LocalDateTime.of(year, month, days, hour, min, sec);
        return dateTime.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
    }

    private long weekToUnix(int year, int week) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDateTime ldt = LocalDateTime.now()
                .withYear(year)
                .with(weekFields.weekOfYear(), week)
                .with(weekFields.dayOfWeek(), 1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        return ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    private long dayToUnix() {
        LocalDateTime ldt = LocalDateTime.now()
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        System.out.println("Day" + ldt.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli());
        return ldt.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
    }

    private long MonthToUnix() {
        LocalDateTime ldt1 = LocalDateTime.now()
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        System.out.println("Month" + ldt1.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli());
        return ldt1.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
    }

    private Calendar unixToCalendar(Long timestamp) {
        Date date = new Date(timestamp); // neu start la milisec thi khong can nhan 1000
        Calendar calendar = Calendar.getInstance();
//        Calendar calendar=new Calendar() {
//            @Override
//            protected void computeTime() {
//
//            }
//
//            @Override
//            protected void computeFields() {
//
//            }
//
//            @Override
//            public void add(int field, int amount) {
//
//            }
//
//            @Override
//            public void roll(int field, boolean up) {
//
//            }
//
//            @Override
//            public int getMinimum(int field) {
//                return 0;
//            }
//
//            @Override
//            public int getMaximum(int field) {
//                return 0;
//            }
//
//            @Override
//            public int getGreatestMinimum(int field) {
//                return 0;
//            }
//
//            @Override
//            public int getLeastMaximum(int field) {
//                return 0;
//            }
//        }
        calendar.setTime(date);

        return calendar;
    }

    private int diffWeek(Calendar start, Calendar end) {
        int weeks = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 52;
        weeks -= start.get(Calendar.WEEK_OF_YEAR) - 1;
        weeks += end.get(Calendar.WEEK_OF_YEAR);
        return weeks <= 0 ? 0 : weeks;
    }

    private int diffMon(Calendar start, Calendar end) {
        int months = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12;
        months -= start.get(Calendar.MONTH) + 1;
        months += end.get(Calendar.MONTH) + 1;
        return months <= 0 ? 0 : months;
    }

    private int diffDay(Calendar start, Calendar end) {
        int oneDay = 24 * 60 * 60 * 1000; // hours*minutes*seconds*milliseconds
        return Math.round(Math.abs((end.getTimeInMillis() - start.getTimeInMillis()) / (oneDay)));
    }

    private String formatDate(Calendar cal, String format) {
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
