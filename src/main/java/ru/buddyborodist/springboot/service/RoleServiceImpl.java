package ru.buddyborodist.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.buddyborodist.springboot.dao.RoleRepository;
import ru.buddyborodist.springboot.model.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void saveRoles(Iterable<Role> roles) {
        roleRepository.saveAll(roles);
    }

    @Override
    public void deleteRoleId(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public void updateRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

}
