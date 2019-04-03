package com.capstone.paypal.controller;

import com.capstone.paypal.common.Fix;
import com.capstone.paypal.service.PayPalService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PayPalController extends AbstractController {

    private PayPalService paymentService;

    public PayPalController(PayPalService paymentService) {
        this.paymentService = paymentService;
    }


    @PostMapping(Fix.MAP_API + Fix.MAP_PAY + "/input")
    public String receivePaymentInput(String username, String password, String price, String description) {
        System.out.println(username);
        System.out.println(password);
        System.out.println(price);
        System.out.println(description);
        try {
            return gson.toJson(paymentService.receivePaymentInput(username, password, price, description));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(0);
    }


}
