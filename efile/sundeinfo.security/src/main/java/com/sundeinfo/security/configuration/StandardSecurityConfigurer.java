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
                // 删除csrf
                .csrf().disable()
                //.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 允许swagger页面展示
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**","/swagger-resources/configuration/ui","/swagge‌​r-ui.html").permitAll()
                // 允许对于网站静态资源的无授权访问
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
                // 对于获取token的rest api要允许匿名访问
                .antMatchers("/auth/**").permitAll()
                // 对于批处理目录允许匿名访问
                .antMatchers("/relay/**").permitAll()
                .antMatchers("/relay/process/**").permitAll()
                // solr部分接口允许匿名访问
                .antMatchers("/solr/refresh").permitAll()
                .antMatchers("/solr/file/*").permitAll()
                // 对于临时目录允许匿名访问
                .antMatchers("/tmp/file/**").permitAll()
                // 文件下载Api允许匿名访问
                .antMatchers("/user/file/download/**").permitAll()
                //.antMatchers("/abstractAdmin/**").hasIpAddress("127.0.0.1")
                .antMatchers("/abstractAdmin/**").access("hasAnyRole('ROLE_SUPER','ROLE_ADMIN')")
                .antMatchers("/setting/getLogoImg").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        //.addFilterAt(filterSecurityInterceptor(),FilterSecurityInterceptor.class);
        // 禁用缓存
        httpSecurity.headers().cacheControl();
        httpSecurity.headers().frameOptions().disable();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/login","/css/**","/font-awesome/**","/fonts/**","/js/**","/img/**","/url/**").permitAll()
//                //其他地址的访问均需验证权限
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                //指定登录页是"/login"
//                .loginPage("/login")
//                .defaultSuccessUrl("/")//登录成功后默认跳转到"/"
//                .failureUrl("/login?error")
//                .permitAll()
////                .and()//开启cookie储存用户信息，并设置有效期为14天，指定cookie中的密钥
////                .rememberMe().tokenValiditySeconds(1209600).key("key")
////                //使用自定义授权策略
////                .anyRequest().access("@doSecurity.check(authentication,request)");
//                .and()
//                .logout()
//                .logoutSuccessUrl("/login")//退出登录后的默认url是"/login"
//                .permitAll();
//        http.addFilterBefore(sessionFilterSecurityInterceptor(), FilterSecurityInterceptor.class);
//    }
}

