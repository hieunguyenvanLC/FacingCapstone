package com.capstone.paypal.config;

import com.capstone.paypal.model.PayPalData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class BeanConfig {


    @Bean
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION)
    public PayPalData paymentData() {
        return new PayPalData();
    };

}
