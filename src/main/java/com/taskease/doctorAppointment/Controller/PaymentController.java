package com.taskease.doctorAppointment.Controller;


import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.EphemeralKey;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.EphemeralKeyCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @PostMapping("/create-payment-intent")
    public Map<String, String> createPaymentIntent(@RequestBody Map<String, Object> request) throws StripeException {
        // Extract request parameters
        long amount = Long.parseLong(request.get("amount").toString());
        String currency = request.get("currency").toString();

        // Create a new customer
        CustomerCreateParams customerParams = CustomerCreateParams.builder().build();
        Customer customer = Customer.create(customerParams);

        EphemeralKeyCreateParams keyParams = EphemeralKeyCreateParams.builder()
                .setCustomer(customer.getId())
                .setStripeVersion("2022-11-15")
                .build();
        EphemeralKey ephemeralKey = EphemeralKey.create(keyParams);

        // Create a Payment Intent
        PaymentIntentCreateParams intentParams = PaymentIntentCreateParams.builder()
                .setAmount(amount) // Amount in cents (e.g., $10 = 1000)
                .setCurrency(currency)
                .setCustomer(customer.getId())
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
                )
                .build();
        PaymentIntent paymentIntent = PaymentIntent.create(intentParams);

        Map<String, String> response = new HashMap<>();
        response.put("customerId", customer.getId());
        response.put("ephemeralKey", ephemeralKey.getSecret());
        response.put("clientSecret", paymentIntent.getClientSecret());
        return response;
    }
}
