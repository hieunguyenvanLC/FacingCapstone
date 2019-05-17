package capstone.fps.service;

import capstone.fps.model.Response;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PayPalService {

    private APIContext apiContext;

    public PayPalService(APIContext apiContext) {
        this.apiContext = apiContext;
    }


    public String initPayment(String priceStr, String description, String cancelUrl, String successUrl) {
        try {

            System.out.println("init");


//        Set payer details
            Payer payer = new Payer();
            payer.setPaymentMethod("paypal");
//        Set redirect URLs
            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setCancelUrl(cancelUrl);
            redirectUrls.setReturnUrl(successUrl);
//        Set payment details
            Details details = new Details();
            details.setShipping("0");
            details.setSubtotal(priceStr);
            details.setTax("0");
//        Payment amount
            Amount amount = new Amount();
            amount.setCurrency("USD");
//        Total must be equal to sum of shipping, tax and subtotal.
            amount.setTotal(priceStr);
            amount.setDetails(details);
// Transaction information
            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setDescription(description);
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

    public Response<String> executePayment(String paymentId, String payerId) {
        Response<String> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        try {
            Payment payment = new Payment();
            payment.setId(paymentId);
            PaymentExecution paymentExecute = new PaymentExecution();
            paymentExecute.setPayerId(payerId);
            Payment executePayment = payment.execute(apiContext, paymentExecute);
            if (executePayment.getState().equals("approved")) {
                response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, paymentId);
                return response;
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return response;
    }

}
