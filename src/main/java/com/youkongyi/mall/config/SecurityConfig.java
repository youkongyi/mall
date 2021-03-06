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
            throw new UsernameNotFoundException("????????????????????????");
        };
    }

    /**
      * @description??? ??????AuthenticationManager(???????????????),?????????????????????
      *     com.youkongyi.mall.config.SecurityConfig.authenticationManager
      * @param??? authenticationConfiguration (org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration)
      * @return??? org.springframework.security.authentication.AuthenticationManager
      * @author??? Aimer
      * @crateDate??? 2022/06/29 15:33
      */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
      * @description??? ????????????
      *     com.youkongyi.mall.config.SecurityConfig.filterChain
      * @param??? http (org.springframework.security.config.annotation.web.builders.HttpSecurity) 
      * @return??? org.springframework.security.web.SecurityFilterChain          
      * @author??? Aimer
      * @crateDate??? 2022/06/28 14:35
      */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()//??????csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//??????session
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
                                        "/admin/login")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                // ?????? JWT ????????????JWT ????????????????????????????????????????????????
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService())
                .exceptionHandling()// ?????????????????????
                .accessDeniedHandler(restfulAccessDeniedHandler())// ?????????????????????
                .authenticationEntryPoint(restAuthenticationEntryPoint())// ??????????????????
                .and()
                .build();
    }

    /**
      * @description??? ??????????????????
      *     com.youkongyi.mall.config.SecurityConfig.restfulAccessDeniedHandler
      * @return??? com.youkongyi.mall.component.RestfulAccessDeniedHandler
      * @author??? Aimer
      * @crateDate??? 2022/06/29 16:26
      */
    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler(){
        return new RestfulAccessDeniedHandler();
    }

    /**
      * @description??? ???????????????
      *     com.youkongyi.mall.config.SecurityConfig.restAuthenticationEntryPoint
      * @return??? com.youkongyi.mall.component.RestAuthenticationEntryPoint
      * @author??? Aimer
      * @crateDate??? 2022/06/29 16:26
      */
    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint(){
        return new RestAuthenticationEntryPoint();
    }

    /**
      * @description??? ????????????????????????????????????????????????????????????jwt???token??????????????????token?????????????????????
      *     com.youkongyi.mall.config.SecurityConfig.jwtAuthenticationTokenFilter

      * @return??? JwtAuthenticationTokenFilter
      * @author??? Aimer
      * @crateDate??? 2022/06/29 15:37
      */
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    /**
      * @description??? ??????????????????
      *     com.youkongyi.mall.config.SecurityConfig.passwordEncoder
      * @return??? PasswordEncoder
      * @author??? Aimer
      * @crateDate??? 2022/06/28 14:34
      */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
      * @description??? ??????????????????(CORS)
      *     com.youkongyi.mall.config.SecurityConfig.corsConfigurationSource
      * @return??? org.springframework.web.cors.CorsConfigurationSource
      * @author??? Aimer
      * @crateDate??? 2022/06/28 14:34
      */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
