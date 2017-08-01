package co.ke.sart.config;

import co.ke.sart.site.service.UserService;
import co.ke.sart.site.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

@EnableWebMvcSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
@ComponentScan(basePackages = "co.ke.sart.site.service")
@Import({co.ke.sart.site.service.UserService.class})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserServiceInterface userServiceInterface;

    //@Bean
    // AuthenticationService getAuthenticationService(){
    //     return new AuthenticationService();
    // }
    @Bean
    protected SessionRegistry sessionRegistryImpl() {
        return new SessionRegistryImpl();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder builder) {
        builder.authenticationProvider(this.userServiceInterface);
    }

    @Override
    public void configure(WebSecurity security) {
        security.ignoring().antMatchers("/resources/**", "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login").failureUrl("/login?loginFailed")
                .defaultSuccessUrl("/patient/")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and().logout()
                .logoutUrl("/logout").logoutSuccessUrl("/login?loggedOut")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .permitAll()
                .and().sessionManagement()
                .sessionFixation().changeSessionId()
                .maximumSessions(1).maxSessionsPreventsLogin(true)
                .sessionRegistry(this.sessionRegistryImpl())
                .and().and().csrf()
                .and().exceptionHandling().accessDeniedPage("/accessdenied");
    }
}
