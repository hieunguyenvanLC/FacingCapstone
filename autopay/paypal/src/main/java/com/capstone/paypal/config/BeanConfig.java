package com.capstone.paypal.config;

import com.capstone.paypal.model.PaymentData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConfig {


    @Bean
    @Scope("singleton")
    public PaymentData paymentData() {
        return new PaymentData();
    };

}
