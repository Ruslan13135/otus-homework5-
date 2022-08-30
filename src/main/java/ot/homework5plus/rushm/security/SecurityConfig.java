package ot.homework5plus.rushm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/", "/auth/*").permitAll()
            .and()
            .antMatcher("/api/**").authorizeRequests()
            .antMatchers(HttpMethod.GET, "/api/book").permitAll()
            .antMatchers(HttpMethod.POST, "/api/book", "/api/comment").hasAnyRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/api/book/**", "/api/comment/**").hasAnyRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/api/book/**", "/api/comment/**").hasAnyRole("ADMIN")
            .antMatchers("/api/author", "/api/author/**", "/api/genre", "/api/genre/**", "/api/instance", "/api/instance/**", "/api/role", "/api/role/**").hasAnyRole("ADMIN")
            .antMatchers("/api/comment", "/api/comment/book/**", "/api/order", "/api/order/**").hasAnyRole("ADMIN", "USER")
            .and()
            .logout()
            .logoutUrl("/auth/logout")
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .deleteCookies("JSESSIONID")
            .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
