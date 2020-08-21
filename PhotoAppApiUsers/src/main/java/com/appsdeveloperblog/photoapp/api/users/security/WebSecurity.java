package com.appsdeveloperblog.photoapp.api.users.security;


import com.appsdeveloperblog.photoapp.api.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // @Configuration already
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private Environment env;
    private UsersService usersService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebSecurity(Environment env, UsersService userService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.env = env;
        this.usersService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,"/users")
                .hasIpAddress(env.getProperty("gateway.ip"))
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated() // any other non-matched request will need to authenticated
                .and()
                .addFilter(getAuthenticationFilter()) // use filter defined below with 1) attemptAuthentication and 2) successfulAuthentication
                .addFilter(new AuthorizationFilter(authenticationManager(),env));
        http.headers().frameOptions().disable(); // so we can see h2 console
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception{
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService, env, authenticationManager());
        authenticationFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));
        return authenticationFilter;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);
    }
}
