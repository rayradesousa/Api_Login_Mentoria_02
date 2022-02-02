package com.mentorias.login;

import com.mentorias.login.repository.UTilizadorDosRepositorios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.transaction.Transactional;


@Transactional
@Configuration
@EnableWebSecurity

public class SecuriyConfiguration extends WebSecurityConfigurerAdapter {

         @Bean
         public static BCryptPasswordEncoder passwordEncoder(){
             return new BCryptPasswordEncoder();
         }

         @Autowired
         private IUserDetailsService userDetailsService;

         @Autowired
         private UTilizadorDosRepositorios  userRepository;

         @Override
         public UserDetailsService userDetailsServiceBean() throws Exception{
             return new IUserDetailsService(userRepository);

         }

         @Override
    protected void configure(HttpSecurity http) throws Exception{
              http.authorizeRequests()
              .antMatchers("/").access("hasAnyAuthority('Users','ADMIN')")
              .antMatchers("/admin").access("hasAuthority('ADMIN')")
              .anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll()
              .and()
              .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
              .logoutSuccessUrl("login").permitAll()
              .and()
              .httpBasic();

               http.csrf().disable();
               http.headers().frameOptions().disable();


         }

       @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
          /*   auth.inMemoryAuthentication()
             .withUser("Carol").password(passwordEncoder().encode("mundo21"))
             .authorities("ADMIN")
             .and()
             .withUser("Luiz")
             .password(passwordEncoder().encode("java81")).authorities("USER");*/

              auth.userDetailsService(userDetailsServiceBean())
                      .passwordEncoder(passwordEncoder());
       }

}
