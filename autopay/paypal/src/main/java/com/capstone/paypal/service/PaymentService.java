package com.capstone.paypal.service;

import com.capstone.paypal.common.Fix;
import com.capstone.paypal.common.Simulator;
import com.capstone.paypal.model.PaymentData;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {


//    private static String descriptionStr = "basis test";
//    private static String orderPrice = "1.23";
//    public static int paymentResult;

    @Autowired
    private PaymentData paymentData;

    private APIContext apiContext;


    public PaymentService(APIContext apiContext) {
        this.apiContext = apiContext;
    }

//    private Payment createPayment(String cancelUrl, String successUrl) {
//
//        String priceStr = String.format("%.2f", orderPrice);
////        Set payer details
//        Payer payer = new Payer();
//        payer.setPaymentMethod("paypal");
//
////        Set redirect URLs
//        RedirectUrls redirectUrls = new RedirectUrls();
//        redirectUrls.setCancelUrl(cancelUrl);
//        redirectUrls.setReturnUrl(successUrl);
//
////        Set payment details
//        Details details = new Details();
//        details.setShipping("0");
//        details.setSubtotal(priceStr);
//        details.setTax("0");
////        Payment amount
//        Amount amount = new Amount();
//        amount.setCurrency(Fix.DEF_CURRENCY);
////        Total must be equal to sum of shipping, tax and subtotal.
//        amount.setTotal(priceStr);
//        amount.setDetails(details);
//
//// Transaction information
//        Transaction transaction = new Transaction();
//        transaction.setAmount(amount);
//        transaction.setDescription(descriptionStr);
//
//// Add transaction to a list
//        List<Transaction> transactions = new ArrayList<>();
//        transactions.add(transaction);
//
//// Add payment details
//        Payment payment = new Payment();
//        payment.setIntent("sale");
//        payment.setPayer(payer);
//        payment.setRedirectUrls(redirectUrls);
//        payment.setTransactions(transactions);
//
//        return payment;
//    }

    public String receivePaymentInput(String username, String password, String price, String description) {

        paymentData.setResult(null);
        paymentData.setPrice(price);
        paymentData.setDescription(description);
//        paymentResult = 0;
//        orderPrice = price;
//        descriptionStr = description;


        int billDarkBlue = 0xFF002069;

        int billLightBlue = 0xFF7F86DD;

        int loginDarkBlue = 0xFF003087;
        int logFailPink = 0xFFFFF7F7;

        int logFailRed = 0xFFC72E2E;

        try {
            Simulator s = new Simulator();

            Simulator.PixelColor pixLog = s.createPixelColor(1, 880, 267, 880, 267, loginDarkBlue, 1);
            Simulator.PixelColor pixBil = s.createPixelColor(2, 585, 232, 585, 232, billDarkBlue, 1);
//            Simulator.PixelColor pixBil = s.createPixelColor(2, 1150, 320, 1150, 320, billLightBlue, 1);
            Simulator.PixelColor pixErr = s.createPixelColor(3, 764, 391, 764, 391, logFailRed, 1);

            s.clickInBox(200, 500, 10, 10);
            int id = s.waitForMultiPixel(pixLog, pixBil);
            System.out.println("step 1 - " + id);
            if (id == pixBil.id) {
                // logout
                s.delayRandomShort();
                s.type('\t');
                s.delayRandomShort();
                s.type('\t');
                s.delayRandomShort();
                s.type('\n');

                s.waitForPixel(880, 267, loginDarkBlue);
            }

            // login
            s.delayRandomShort();
            s.type('\t');
            s.delayRandomShort();
            s.copyParseString(username);
            s.delayRandomMedium();
            s.type('\t');
            s.delayRandomMedium();
            s.copyParseString(password);
            s.delay(1000);
            s.moveAndClickInBox(860, 550, 120, 30);

            id = s.waitForMultiPixel(pixErr, pixBil);
            System.out.println("step 2 - " + id);

            if (id == pixBil.id) {
                // confirm bill
                s.delayRandomMedium();
                s.delay(2000);
                s.moveAndClickInBox(650, 840, 200, 30);
                while (paymentData.getResult() == null) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                    }
                }
                return paymentData.getResult();
            } else {
                s.clickInBox(1500, 50, 0, 0);
                s.copyParseString(Fix.LOCAL_URL);
                s.type('\n');
                return "fail";
            }


//            // logout
//            s.waitForPixel(585, 232, billDarkBlue);
//            s.delayRandomShort();
//            s.type('\t');
//            s.delayRandomShort();
//            s.type('\t');
//            s.delayRandomShort();
//            s.type('\n');
//
//            // login
//            s.waitForPixel(880, 267, loginDarkBlue);
//            s.delayRandomShort();
//            s.type('\t');
//            s.delayRandomShort();
//            s.copyParseString(username);
//            s.delayRandomMedium();
//            s.type('\t');
//            s.delayRandomMedium();
//            s.copyParseString(password);
//            s.delay(1000);
//            s.moveAndClickInBox(860, 550, 120, 30);
//
//            // confirm bill
//            s.waitForPixel(585, 232, billDarkBlue);
//            s.delayRandomMedium();
//            s.delay(1000);
//            s.moveAndClickInBox(650, 840, 200, 30);


        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }


        return "0s";
    }

    public String initPayment(String cancelUrl, String successUrl) {
        try {

//        Set payer details
            Payer payer = new Payer();
            payer.setPaymentMethod("paypal");

//        Set redirect URLs
            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setCancelUrl(Fix.LOCAL_URL + cancelUrl);
            redirectUrls.setReturnUrl(Fix.LOCAL_URL + successUrl);

//        Set payment details
            Details details = new Details();
            details.setShipping("0");
            details.setSubtotal(paymentData.getPrice());
            details.setTax("0");
//        Payment amount
            Amount amount = new Amount();
            amount.setCurrency(Fix.DEF_CURRENCY);
//        Total must be equal to sum of shipping, tax and subtotal.
            amount.setTotal(paymentData.getPrice());
            amount.setDetails(details);

// Transaction information
            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setDescription(paymentData.getDescription());

// Add transaction to a list
            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);

// Add payment details
            Payment payment = new Payment();
            payment.setIntent("sale");
            payment.setPayer(payer);
            payment.setRedirectUrls(redirectUrls);
            payment.setTransactions(transactions);


            Payment createdPayment = payment.create(apiContext);
            for (Links links : createdPayment.getLinks()) {
                if (links.getRel().equalsIgnoreCase("approval_url")) {
                    return "redirect:" + links.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    public String executePayment(String paymentId, String payerId) {
        try {
            Payment payment = new Payment();
            payment.setId(paymentId);
            PaymentExecution paymentExecute = new PaymentExecution();
            paymentExecute.setPayerId(payerId);
            Payment executePayment = payment.execute(apiContext, paymentExecute);
            if (executePayment.getState().equals("approved")) {
                paymentData.setResult(paymentId);
                return "success";
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        paymentData.setResult("fail");
        return "redirect:/";
    }
}