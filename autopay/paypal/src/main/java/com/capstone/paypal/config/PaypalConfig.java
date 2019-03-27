package com.capstone.paypal.config;

import com.capstone.paypal.common.Fix;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaypalConfig {

//    @Bean
//    public Map<String, String> paypalSdkConfig() {
//        Map<String, String> sdkConfig = new HashMap<>();
//        sdkConfig.put("mode", Fix.PAYPAL_MODE);
//        return sdkConfig;
//    }
//
//    @Bean
//    public OAuthTokenCredential authTokenCredential() {
//        return new OAuthTokenCredential(Fix.PAYPAL_CLIENT_ID, Fix.PAYPAL_CLIENT_SECRET, paypalSdkConfig());
//    }

    @Bean
    public APIContext apiContext() {
//        APIContext apiContext = new APIContext(clientId, clientSecret, mode);
//        apiContext.setConfigurationMap(paypalSdkConfig());
        return new APIContext(Fix.PAYPAL_CLIENT_ID, Fix.PAYPAL_CLIENT_SECRET, Fix.PAYPAL_MODE);
    }
}