package com.sundeinfo.security.configuration;


import com.sundeinfo.security.strategy.token.AuthenticationTokenFilter;
import com.sundeinfo.security.strategy.AnyUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class StandardSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(anyUserService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public Md5PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
    }

    @Bean
    public AnyUserService anyUserService(){
        return new AnyUserService();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new AuthenticationTokenFilter();
    }

//    @Bean()
//    public SessionFilterSecurityInterceptor sessionFilterSecurityInterceptor(){
//        return new SessionFilterSecurityInterceptor();
//    }

//    private QQAuthenticationFilter qqAuthenticationFilter(){
//        QQAuthenticationFilter authenticationFilter = new QQAuthenticationFilter("/login/qq");
//        //SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
//        //successHandler.setAlwaysUseDefaultTargetUrl(true);
//        //successHandler.setDefaultTargetUrl("/user");
//        //MyAuthenticationSuccessHandler successHandler = new MyAuthenticationSuccessHandler();
//        authenticationFilter.setAuthenticationManager(new QQAuthenticationManager());
//        //authenticationFilter.setAuthenticationSuccessHandler(successHandler);
//        return authenticationFilter;
//    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // ??????csrf
                .csrf().disable()
                //.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // ??????token??????????????????session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // ??????swagger????????????
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**","/swagger-resources/configuration/ui","/swagge??????r-ui.html").permitAll()
                // ????????????????????????????????????????????????
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/pdf/**",
                        "/fonts/**",
                        "/iconfont/**",
                        "/images/**",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                // ????????????token???rest api?????????????????????
                .antMatchers("/auth/**").permitAll()
                // ???????????????????????????????????????
                .antMatchers("/relay/**").permitAll()
                .antMatchers("/relay/process/**").permitAll()
                // solr??????????????????????????????
                .antMatchers("/solr/refresh").permitAll()
                .antMatchers("/solr/file/*").permitAll()
                // ????????????????????????????????????
                .antMatchers("/tmp/file/**").permitAll()
                // ????????????Api??????????????????
                .antMatchers("/user/file/download/**").permitAll()
                //.antMatchers("/abstractAdmin/**").hasIpAddress("127.0.0.1")
                .antMatchers("/abstractAdmin/**").access("hasAnyRole('ROLE_SUPER','ROLE_ADMIN')")
                .antMatchers("/setting/getLogoImg").permitAll()
                // ???????????????????????????????????????????????????
                .anyRequest().authenticated();
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        //.addFilterAt(filterSecurityInterceptor(),FilterSecurityInterceptor.class);
        // ????????????
        httpSecurity.headers().cacheControl();
        httpSecurity.headers().frameOptions().disable();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/login","/css/**","/font-awesome/**","/fonts/**","/js/**","/img/**","/url/**").permitAll()
//                //???????????????????????????????????????
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                //??????????????????"/login"
//                .loginPage("/login")
//                .defaultSuccessUrl("/")//??????????????????????????????"/"
//                .failureUrl("/login?error")
//                .permitAll()
////                .and()//??????cookie??????????????????????????????????????????14????????????cookie????????????
////                .rememberMe().tokenValiditySeconds(1209600).key("key")
////                //???????????????????????????
////                .anyRequest().access("@doSecurity.check(authentication,request)");
//                .and()
//                .logout()
//                .logoutSuccessUrl("/login")//????????????????????????url???"/login"
//                .permitAll();
//        http.addFilterBefore(sessionFilterSecurityInterceptor(), FilterSecurityInterceptor.class);
//    }
}

