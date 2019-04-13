package capstone.fps.config;

import capstone.fps.common.Fix;
import capstone.fps.model.Response;
import capstone.fps.service.AccountService;
import capstone.fps.service.LoginService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.spel.spi.EvaluationContextExtension;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginService loginService;
    private AccountService accountService;


    @Autowired
    public WebSecurityConfig(LoginService loginService, AccountService accountService) {
        this.loginService = loginService;
        this.accountService = accountService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()

                .authorizeRequests()
                .antMatchers(Fix.MAP_ADM + "/**")
                .hasRole("ADMIN")
                .antMatchers(Fix.MAP_SHP + "/**")
                .hasRole("SHIPPER")
                .antMatchers(Fix.MAP_MEM + "/**")
                .hasRole("MEMBER")
                .antMatchers(Fix.MAP_LOG + "/**")
                .hasAnyRole("ADMIN", "MEMBER", "SHIPPER")
                .and()
                .formLogin()
                .loginPage("/loginPage")
                .loginProcessingUrl("/sign_in")
                .usernameParameter("phoneNumber")
                .passwordParameter("password")
                .permitAll()
                .successHandler(loginSuccessHandler())
                .failureHandler(loginFailureHandler())
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access_denied")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/sign_out", "GET"))
                .permitAll()
                .logoutSuccessUrl("/logout_success")
                .and()
                .cors().and().rememberMe();

    }

    private AuthenticationSuccessHandler loginSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>) authentication.getAuthorities();
                String role = authorities.get(0).getAuthority();
                Response<String> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
                response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, role);
//                String phoneNumber = authentication.getName();

//                FRAccount account = accountService.findByPhone(phoneNumber);

//                List<String> result = new ArrayList<>();
//                result.add("Role: " + role);
//                result.add("Account ID: " + account.getId());


//                Response<List<String>> responseObj = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
//                responseObj.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, result);


//                Thread t = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        for (int i = 0; i < 1000; i++) {
//                            //new Date();
//                            System.out.println("i - " + i + " - ");
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });
//                t.start();
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("dd/MM/yyyy HH:mm").create();
                httpServletResponse.getWriter().append(gson.toJson(response));
//                System.out.println("Result: " + result.toString());
            }
        };
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Access-Control-Allow-Credentials"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    private AuthenticationFailureHandler loginFailureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                Response<String> responseObj = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
                responseObj.setResponse(Response.STATUS_FAIL, Response.MESSAGE_FAIL, "Error");
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("dd/MM/yyyy HH:mm").create();
                response.getWriter().append(gson.toJson(responseObj));
            }
        };
    }

    @Bean
    public EvaluationContextExtension securityExtension() {
        return new EvaluationContextExtension() {
            @Override
            public String getExtensionId() {
                return "security";
            }

            @Override
            public Object getRootObject() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return new SecurityExpressionRoot(authentication) {
                };
            }
        };
    }
}
