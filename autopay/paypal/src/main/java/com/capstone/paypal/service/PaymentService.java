package com.capstone.paypal.service;

import com.capstone.paypal.common.Fix;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {


    private static String descriptionStr = "basis test";
    private static double orderPrice = 1.23;


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

    public String receivePaymentInput(double price, String description) {
        orderPrice = price;
        descriptionStr = description;
        return null;
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