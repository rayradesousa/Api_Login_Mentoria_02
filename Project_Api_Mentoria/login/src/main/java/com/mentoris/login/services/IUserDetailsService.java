package com.mentoris.login.services;


import com.mentorias.login.model.Role;
import com.mentorias.login.model.User;
import com.mentorias.login.repository.UTilizadorDosRepositorios;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class IUserDetailsService implements UserDetailsService {

    private UTilizadorDosRepositorios userRepository;

    public  IUserDetailsService (UTilizadorDosRepositorios username){
        this.userRepository= userRepository;
    }

         @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
             try{

         User user = userRepository.findByUsername(username);
                 if (user==null){
                     return null;
                 }

     return new org.springframework.security.core.userdetails.User(user.getUsuario(), user.getSenha(), getAuthories(user));

             }catch (Exception e){

                 throw new UsernameNotFoundException("Usuário não existe");
             }

         }

         private Set<GrantedAuthority> getAuthories(User user){
             Set<GrantedAuthority> authorities = new HashSet<>();
            for (Role role: user.getRoles()){
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
                authorities.add(grantedAuthority);
            }
              return authorities;
         }
}
