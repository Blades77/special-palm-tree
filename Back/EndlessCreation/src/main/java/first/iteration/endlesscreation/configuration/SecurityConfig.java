package first.iteration.endlesscreation.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
//import first.iteration.endlesscreation.filter.CustomAuthTokenFilter;
import first.iteration.endlesscreation.filter.CustomAuthorizationFilter;
import org.hibernate.jdbc.Expectation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration @EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    private final CustomLogoutHandler customLogoutHandler;
    private final CustomAuthorizationFilter customAuthorizationFilter;

    public SecurityConfig(UserDetailsService userDetailsService,ObjectMapper objectMapper,CustomLogoutHandler customLogoutHandler,CustomAuthorizationFilter customAuthorizationFilter) {
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
        this.customLogoutHandler = customLogoutHandler;
        this.customAuthorizationFilter = customAuthorizationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.sessionManagement().sessionCreationPolicy(STATELESS);
//        http.authorizeRequests().antMatchers("/login/**","user/refresh?**").permitAll();
//        http.authorizeRequests().antMatchers("/swagger-ui/#/**").permitAll();
//        http.authorizeRequests().antMatchers(GET,"/book/**").hasAnyAuthority("ADMIN");
////        http.authorizeRequests().anyRequest().authenticated();
////        http.addFilter(new CustomAuthTokenFilter(authenticationManagerBean(),objectMapper));
//        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
////        http.addLogoutHandler(customLogoutHandler);
////        http.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK));
        http.cors().and().csrf().disable()
                .authorizeRequests()
                    .antMatchers("/login/**","user/refresh?**").permitAll()
                    .antMatchers("/swagger-ui/#/**").permitAll()
//                    .antMatchers("/tile/**").authenticated()
                    .antMatchers(GET,"/book/**").hasAnyAuthority("ADMIN")
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin().disable()
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                .addLogoutHandler(customLogoutHandler)
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                });


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
