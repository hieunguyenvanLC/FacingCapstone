package capstone.fps.config;

import capstone.fps.model.AppData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class WebConfig {

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION)
    public AppData pref() {
        return new AppData();
    }

}