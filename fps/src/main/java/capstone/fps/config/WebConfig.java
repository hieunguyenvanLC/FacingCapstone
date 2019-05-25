package capstone.fps.config;

import capstone.fps.model.ordermatch.OrderMap;
import capstone.fps.model.ordermatch.ShipperWait;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class WebConfig {

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION)
    public OrderMap pref() {
        return new OrderMap();
    }


//    @Bean
//    @Scope("singleton")
//    public ShipperWait shipperWait() {
//        return new ShipperWait();
//    }
}