package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> listRoles();

    void add(Role role);

    Role getRoleByID(long id);

    void update(Role role);

    void delete(long id);
}
