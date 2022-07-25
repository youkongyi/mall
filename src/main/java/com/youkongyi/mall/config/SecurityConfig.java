package com.youkongyi.mall.config;

import com.youkongyi.mall.component.RestAuthenticationEntryPoint;
import com.youkongyi.mall.component.RestfulAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.youkongyi.mall.component.JwtAuthenticationTokenFilter;
import com.youkongyi.mall.dto.AdminUserDetails;
import com.youkongyi.mall.service.IUmsAdminService;

@Configuration
public class SecurityConfig {

    private IUmsAdminService adminService;

    @Lazy
    @Autowired
    public void setAdminService(IUmsAdminService adminService) {
        this.adminService = adminService;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            AdminUserDetails admin = adminService.getAdminByUsername(username);
            if (admin != null) {
                return admin;
            }
            throw new UsernameNotFoundException("用户名或密码错误");
        };
    }

    /**
      * @description： 获取AuthenticationManager(认证管理器),登录时认证使用
      *     com.youkongyi.mall.config.SecurityConfig.authenticationManager
      * @param： authenticationConfiguration (org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration)
      * @return： org.springframework.security.authentication.AuthenticationManager
      * @author： Aimer
      * @crateDate： 2022/06/29 15:33
      */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
      * @description： 配置过滤
      *     com.youkongyi.mall.config.SecurityConfig.filterChain
      * @param： http (org.springframework.security.config.annotation.web.builders.HttpSecurity) 
      * @return： org.springframework.security.web.SecurityFilterChain          
      * @author： Aimer
      * @crateDate： 2022/06/28 14:35
      */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()//关闭csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//关闭session
                .and()
                .authorizeHttpRequests(auth ->
                        auth.mvcMatchers("/resources/**",
                                        "/webjars/**",
                                        "/swagger-ui/**",
                                        "/swagger-resources/**",
                                        "/v3/api-docs/**",
                                        "/v2/api-docs/**",
                                        "/*.ico",
                                        "/*.html",
                                        "/admin/register",
                                        "/admin/login")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                // 添加 JWT 过滤器，JWT 过滤器在用户名密码认证过滤器之前
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService())
                .exceptionHandling()// 自定义异常处理
                .accessDeniedHandler(restfulAccessDeniedHandler())// 访问被拒绝异常
                .authenticationEntryPoint(restAuthenticationEntryPoint())// 身份验证方案
                .and()
                .build();
    }

    /**
      * @description： 没有权限异常
      *     com.youkongyi.mall.config.SecurityConfig.restfulAccessDeniedHandler
      * @return： com.youkongyi.mall.component.RestfulAccessDeniedHandler
      * @author： Aimer
      * @crateDate： 2022/06/29 16:26
      */
    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler(){
        return new RestfulAccessDeniedHandler();
    }

    /**
      * @description： 未登录异常
      *     com.youkongyi.mall.config.SecurityConfig.restAuthenticationEntryPoint
      * @return： com.youkongyi.mall.component.RestAuthenticationEntryPoint
      * @author： Aimer
      * @crateDate： 2022/06/29 16:26
      */
    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint(){
        return new RestAuthenticationEntryPoint();
    }

    /**
      * @description： 在用户名和密码校验前添加的过滤器，如果有jwt的token，会自行根据token信息进行登录。
      *     com.youkongyi.mall.config.SecurityConfig.jwtAuthenticationTokenFilter

      * @return： JwtAuthenticationTokenFilter
      * @author： Aimer
      * @crateDate： 2022/06/29 15:37
      */
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    /**
      * @description： 配置加密方式
      *     com.youkongyi.mall.config.SecurityConfig.passwordEncoder
      * @return： PasswordEncoder
      * @author： Aimer
      * @crateDate： 2022/06/28 14:34
      */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
      * @description： 配置跨源访问(CORS)
      *     com.youkongyi.mall.config.SecurityConfig.corsConfigurationSource
      * @return： org.springframework.web.cors.CorsConfigurationSource
      * @author： Aimer
      * @crateDate： 2022/06/28 14:34
      */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
