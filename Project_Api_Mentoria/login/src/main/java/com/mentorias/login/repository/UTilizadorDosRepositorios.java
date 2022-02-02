package com.mentorias.login.repository;

import com.mentorias.login.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UTilizadorDosRepositorios extends CrudRepository <User, Long> {
  User findByUsername(String username);
}
