package com.capstone.paypal.controller;

import com.capstone.paypal.common.Fix;
import com.capstone.paypal.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PaymentController extends AbstractController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @PostMapping(Fix.MAP_API + Fix.MAP_PAY + "/input")
    public String receivePaymentInput(String username, String password, double price, String description) {
        try {
            return gson.toJson(paymentService.receivePaymentInput(username, password, price, description));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(0);
    }


}
