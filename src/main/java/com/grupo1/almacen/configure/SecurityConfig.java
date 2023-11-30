package com.grupo1.almacen.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    public   CustomAuthSucessHandler sucessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService getDetailsService(){
        return new CustomUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider getAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(getDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests().requestMatchers("/marcas/**","/user/**","/categorias/**","/productos/**","/existencias/**","/backoffice/**","/pedidos/**","/medidas/**","/tipoalmacen/**","/proveedores/**","/report/").hasRole("USER")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/","/register","/signin","/saveUser").permitAll()
                .requestMatchers("/styles/**", "/scripts/**").permitAll()
                .requestMatchers("/uploads/**").permitAll()
                /*.requestMatchers("/user/**").authenticated()*/
                .and()
                .formLogin().loginPage("/signin").loginProcessingUrl("/userLogin")
                /*.defaultSuccessUrl("/user/profile").permitAll();*/
                .successHandler(sucessHandler)
                /*.and().logout().permitAll();*/
                .permitAll();
        return http.build();
    }

}
