package capstone.fps.config;

import capstone.fps.common.Fix;
import com.paypal.base.rest.APIContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalConfig {


    @Bean
    public APIContext apiContext() {
        return new APIContext(Fix.PAYPAL_CLIENT_ID, Fix.PAYPAL_CLIENT_SECRET, Fix.PAYPAL_MODE);
    }
}