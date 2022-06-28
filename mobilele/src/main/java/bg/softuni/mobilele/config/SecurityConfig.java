package bg.softuni.mobilele.config;

import bg.softuni.mobilele.model.enums.UserRoleEnum;
import bg.softuni.mobilele.repository.UserRepository;
import bg.softuni.mobilele.service.MobileleUserDetailService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // http exposes api that allows us configure the web security
    http.
        // which pages will be authorized?
            authorizeRequests().
        // allow CSS at "common" static location (static/css)
            requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
        // permit home page, login and registration pages for anyone
            antMatchers("/", "/users/login", "/users/register").permitAll().
        // allow for moderators
            antMatchers("/pages/moderators").hasRole(UserRoleEnum.MODERATOR.name()).
        // allow for admins
            antMatchers("/pages/admins").hasRole(UserRoleEnum.ADMIN.name()).
        // any remaining reqests should be authenticated
            anyRequest().
        authenticated().
        and().
        formLogin().
        loginPage("/users/login").
        usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
        passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
        defaultSuccessUrl("/").
        failureForwardUrl("/users/login-error").
        and().
        logout().
        logoutUrl("/users/logout").
        logoutSuccessUrl("/").
        invalidateHttpSession(true).
        deleteCookies("JSESSIONID");

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    return new MobileleUserDetailService(userRepository);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new Pbkdf2PasswordEncoder();
  }

}
