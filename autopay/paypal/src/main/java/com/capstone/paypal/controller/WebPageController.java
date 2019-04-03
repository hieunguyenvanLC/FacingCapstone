package com.capstone.paypal.controller;

import com.capstone.paypal.common.Fix;
import com.capstone.paypal.service.PayPalService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebPageController {

    private static final String URL_PAY_PAL_SUCCESS = Fix.MAP_PAY + "/success";
    private static final String URL_PAY_PAL_CANCEL = Fix.MAP_PAY + "/cancel";
    private final PayPalService paymentService;

    public WebPageController(PayPalService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/index")
    public String index() {
//        System.out.println(1);
        return "index";
    }

    @GetMapping("/")
    public String index2() {
//        System.out.println(2);
        return "index";
    }

//    @GetMapping("/")
//    public String viewProductList2() {
//        return "indexPage";
//    }
//
//    @GetMapping("/index")
//    public String viewProductList() {
//        return "indexPage";
//    }

    @GetMapping(Fix.MAP_PAY)
    public String initPay() {
        return paymentService.initPayment(Fix.LOCAL_URL + URL_PAY_PAL_CANCEL, Fix.LOCAL_URL + URL_PAY_PAL_SUCCESS);
    }

    @GetMapping(URL_PAY_PAL_CANCEL)
    public String cancelPay() {
        return "redirect:/";
    }

    @GetMapping(URL_PAY_PAL_SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        return paymentService.executePayment(paymentId, payerId);
    }


}