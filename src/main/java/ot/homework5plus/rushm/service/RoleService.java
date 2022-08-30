package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.models.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAll();

    Role getRole(Long roleId);

    List<Role> getRoles(List<String> roleIds);

    Role addRole(Role role);

    Role updateRole(Role role);

    void deleteRole(Long roleId);

    long getCount();
}
