package ot.homework5plus.rushm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ot.homework5plus.rushm.models.entity.Role;
import ot.homework5plus.rushm.repositories.RoleRepository;
import ot.homework5plus.rushm.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAll() {
        log.debug("The application got in method - getAll()");
        return roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRole(Long roleId) {
        log.debug("The application got in method - getRole");
        return roleRepository.getOne(roleId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getRoles(List<String> roleIds) {
        log.debug("The application got in method - getRoles");
        List<Long> ids = roleIds.stream()
            .map(Long::parseLong)
            .collect(Collectors.toList());
        return roleRepository.findAllById(ids);
    }

    @Override
    @Transactional
    public Role addRole(Role role) {
        log.debug("The application got in method - addRole");
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public Role updateRole(Role role) {
        log.debug("The application got in method - updateRole");
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public void deleteRole(Long roleId) {
        log.debug("The application got in method - deleteRole");
        roleRepository.deleteById(roleId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getCount() {
        log.debug("The application got in method - getCount");
        return roleRepository.count();
    }
}
