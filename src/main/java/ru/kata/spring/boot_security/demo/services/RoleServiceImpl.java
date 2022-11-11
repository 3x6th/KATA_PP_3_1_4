package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;


import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleServiceImpl() {
    }

    @Override
    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void add(Role role) {
        roleRepository.saveAndFlush(role);
    }

    @Override
    public Role getRoleByID(long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Role role) {
        roleRepository.saveAndFlush(role);
    }

    @Override
    public void delete(long id) {
        roleRepository.findById(id).ifPresent(role -> roleRepository.delete(role));
    }
}
