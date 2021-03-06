package kata.academy.config;

import kata.academy.config.handler.CustomAuthenticationFailureHandler;
import kata.academy.config.handler.LoginSuccessHandler;
import kata.academy.config.init.TablesStartInitializer;
import kata.academy.dao.UserDao;
import kata.academy.security.UserSecurityDetailsService;
import kata.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@PropertySource(value = "classpath:db.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserSecurityDetailsService securityDetailsService;

    private Environment environment;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Autowired
    public void setSecurityDetailsService(UserSecurityDetailsService securityDetailsService) {
        this.securityDetailsService = securityDetailsService;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(securityDetailsService);
        return daoAuthenticationProvider;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/scripts/**")
                .antMatchers("/styles/**")
                .antMatchers("/images/**")
                .antMatchers("/fonts/**")
                .antMatchers("/signup");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                // ?????????????????? ???????????????? ?? ???????????? ????????????
                .loginPage("/login")
                //?????????????????? ???????????? ?????????????????? ?????? ????????????
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new CustomAuthenticationFailureHandler())
                // ?????????????????? action ?? ?????????? ????????????
                .loginProcessingUrl("/login")
                // ?????????????????? ?????????????????? ???????????? ?? ???????????? ?? ?????????? ????????????
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                // ???????? ???????????? ?? ?????????? ???????????? ????????
                .permitAll();

        http.logout()
                // ?????????????????? ???????????? ???????????? ????????
                .permitAll()
                // ?????????????????? URL ??????????????
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // ?????????????????? URL ?????? ?????????????? ??????????????
                .logoutSuccessUrl("/?logout")
                //???????????????? ?????????????????????????? ?????????????????????? (???? ?????????? ???????????????? ??????????????)
                .and().csrf().disable();

        http
                // ???????????? ???????????????? ?????????????????????? ?????????????????????? ?????? ???????????????????????????????? ??????????????????????????
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/signup").anonymous()
                //???????????????? ?????????????????????????? ???????????????? ????????
                .antMatchers("/login").anonymous()
                // ???????????????????? URL
                .antMatchers("/admin").access("hasAnyRole('ROLE_ADMIN')").anyRequest().authenticated();
    }

    @Bean
    public void TablesStartInitializer() {
        TablesStartInitializer tablesStartInitializer = new TablesStartInitializer();
        try {
            tablesStartInitializer.setUserDao(userService);
            tablesStartInitializer.setAdminPassword(environment.getRequiredProperty("app_admin_password"));
            tablesStartInitializer.setAdminName(environment.getRequiredProperty("app_admin_name"));
            tablesStartInitializer.setAdminLogin(environment.getRequiredProperty("app_admin_login"));
            tablesStartInitializer.setAdminRole(environment.getRequiredProperty("app_admin_role"));
            tablesStartInitializer.setUserRole(environment.getRequiredProperty("app_user_role"));
            tablesStartInitializer.init();
        } catch (IllegalStateException exception) {
            exception.printStackTrace();
        }
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


}
