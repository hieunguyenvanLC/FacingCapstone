package capstone.fps.config;

import capstone.fps.common.Fix;
import capstone.fps.model.FlashMessage;
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
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginService loginService;

    @Autowired
    public WebSecurityConfig(LoginService loginService) {
        this.loginService = loginService;
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
        http
                .authorizeRequests()
                .antMatchers(Fix.MAP_ADM + "**")
                .hasRole("ADMIN")
                .antMatchers(Fix.MAP_SHP + "**")
                .hasRole("SHIPPER")
                .antMatchers(Fix.MAP_MEM + "**")
                .hasRole("MEMBER")
                .antMatchers(Fix.MAP_LOG + "**")
                .hasAnyRole("ADMIN", "MEMBER", "SHIPPER")
                .and()
                .formLogin()
                .loginPage("/loginPage")
                .loginProcessingUrl("/login")
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
                .permitAll()
                .logoutSuccessUrl("/loginPage")
                .and()
                .csrf()
                .and().rememberMe();
    }

    private AuthenticationSuccessHandler loginSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>) authentication.getAuthorities();
                String role = authorities.get(0).getAuthority();
//                switch (role) {
//                    case Fix.ROL_ADM:
//                        response.sendRedirect("/admin");
//                        return;
//                    case Fix.ROL_MEM:
//                        response.sendRedirect("/api/index");
//                        return;
//
//                    default:
//                        response.sendRedirect("/access_denied");
//                }
                Gson gson = new GsonBuilder().create();
                response.getWriter().append(gson.toJson(role));
            }
        };
    }

    private AuthenticationFailureHandler loginFailureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//                request.getSession().setAttribute("flash", new FlashMessage("Incorrect username and/or password. Please try again.", FlashMessage.Status.FAILURE));
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("dd/MM/yyyy HH:mm").create();
                response.getWriter().append(gson.toJson("Error"));
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
