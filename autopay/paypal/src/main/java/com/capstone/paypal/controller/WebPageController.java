package com.capstone.paypal.controller;

import com.capstone.paypal.common.Fix;
import com.capstone.paypal.model.PaymentData;
import com.capstone.paypal.service.PaymentService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebPageController {

    private static final String URL_PAYPAL_SUCCESS = Fix.MAP_PAY + "/success";
    private static final String URL_PAYPAL_CANCEL = Fix.MAP_PAY + "/cancel";
    private final PaymentService paymentService;

    public WebPageController(PaymentService paymentService) {
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

    @GetMapping(Fix.MAP_PAY + "/init")
    public String initPay() {
        return paymentService.initPayment(URL_PAYPAL_CANCEL, URL_PAYPAL_SUCCESS);
    }

    @GetMapping(Fix.MAP_PAY )
    public String sPay() {
        System.out.println("?");
        return "fail";
    }


    @GetMapping(URL_PAYPAL_CANCEL)
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(URL_PAYPAL_SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        return paymentService.executePayment(paymentId, payerId);
    }


}