package com.mentorias.login.repository;

import com.mentorias.login.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepositorio extends CrudRepository<Role, Long> {

    Role findByRoles(String role);
}
