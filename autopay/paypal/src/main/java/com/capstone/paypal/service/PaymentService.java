package com.capstone.paypal.service;

import com.capstone.paypal.common.Fix;
import com.capstone.paypal.common.Simulator;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PaymentService {


    private static String descriptionStr = "basis test";
    private static double orderPrice = 1.23;
    public static int paymentResult;


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

    public String receivePaymentInput(String username, String password, double price, String description) {
        paymentResult = 0;
        orderPrice = price;
        descriptionStr = description;


        int billDarkBlue = 0xFF002069;
        int loginDarkBlue = 0xFF003087;


        Simulator s = null;


        try {
            s = new Simulator();

            Simulator.PixelColor loginMark = s.createPixelColor(1, 870,260, 900,290, loginDarkBlue, 5);
            Simulator.PixelColor billMark = s.createPixelColor(1, 870,260, 900,290, loginDarkBlue, 5);


            s.clickInBox(200, 500, 10, 10);





            // logout
            s.waitForPixel(585, 232, billDarkBlue);
            s.delayRandomShort();
            s.type('\t');
            s.delayRandomShort();
            s.type('\t');
            s.delayRandomShort();
            s.type('\n');

            // login
            s.waitForPixel(880, 267, loginDarkBlue);
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

            // confirm bill
            s.waitForPixel(585, 232, billDarkBlue);
            s.delayRandomMedium();
            s.delay(1000);
            s.moveAndClickInBox(650, 840, 200, 30);


        } catch (AWTException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        return paymentResult + "";
    }

    public String initPayment(String cancelUrl, String successUrl) {
        try {

//            Payment payment = createPayment(cancelUrl, successUrl);


            String priceStr = String.format("%.2f", orderPrice);
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
            details.setSubtotal(priceStr);
            details.setTax("0");
//        Payment amount
            Amount amount = new Amount();
            amount.setCurrency(Fix.DEF_CURRENCY);
//        Total must be equal to sum of shipping, tax and subtotal.
            amount.setTotal(priceStr);
            amount.setDetails(details);

// Transaction information
            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setDescription(descriptionStr);

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

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }
}