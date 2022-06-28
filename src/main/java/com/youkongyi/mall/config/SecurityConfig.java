package com.youkongyi.mall.config;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
      * @description： 认证管理器
      *     com.youkongyi.mall.config.SecurityConfig.authenticationManager
      * @param： authenticationConfiguration (org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration)
      * @return： org.springframework.security.authentication.AuthenticationManager
      * @author： Aimer
      * @crateDate： 2022/06/28 14:35
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
                .authorizeRequests(auth ->
                        auth.antMatchers("/**").permitAll()
                                .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService()).build();
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
