package pl.pytlak.photoart.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebSecurity
@EnableSwagger2
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final FailureHandler failureHandler;
    private final SuccessHandler successHandler;
    private final SuccessHandlerRegister successHandlerRegister;
    private final ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic()
                .and()
                .cors()
                .and()
                .logout()
                .logoutUrl("/logout")
                .and()
                .authorizeRequests()
                .antMatchers(
                "/uploadBuffer",
                        "/login",
                        "/userPhotos/**",
                        "/userPhotosPull",
                        "/userAlbums/**",
                        "/register",
                        "/user/getUserInformation/*",
                        "/check",
                        "/user/search",
                        "/images/**",
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**")
                .permitAll()
                .anyRequest().authenticated()
                .and().addFilter(registerFilter())
                .addFilter(filter()).exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public JsonObjectAuthenticationFilter filter() throws Exception {

        JsonObjectAuthenticationFilter filter = new JsonObjectAuthenticationFilter(objectMapper);
        filter.setAuthenticationFailureHandler(failureHandler);
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Bean
    public JsonRegisterAuthenticationFilter registerFilter() throws Exception {
        JsonRegisterAuthenticationFilter registerFilter = new JsonRegisterAuthenticationFilter();
        registerFilter.setAuthenticationSuccessHandler(successHandlerRegister);
        registerFilter.setAuthenticationManager(authenticationManager());

        return registerFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Tika tika() {
        return new Tika();
    }
}
