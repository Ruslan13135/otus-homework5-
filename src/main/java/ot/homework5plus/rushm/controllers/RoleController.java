package ot.homework5plus.rushm.controllers;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ot.homework5plus.rushm.models.entity.Role;
import ot.homework5plus.rushm.service.RoleService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class RoleController {

    private static final String ROLE_SERVICE = "roleService";

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // Получить все роли
    @GetMapping("/api/role")
    @Bulkhead(name = ROLE_SERVICE, fallbackMethod = "bulkHeadGetAllRole", type = Bulkhead.Type.SEMAPHORE)
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<>(roleService.getAll(), HttpStatus.OK);
    }

    // Получить роль
    @GetMapping("/api/role/{id}")
    @Bulkhead(name = ROLE_SERVICE, fallbackMethod = "bulkHeadGetRole", type = Bulkhead.Type.SEMAPHORE)
    public ResponseEntity<Role> getRoles(@PathVariable("id") Long roleId) {
        if (roleId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Role role  = roleService.getRole(roleId);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    // Создать новую роль
    @PostMapping("/api/role")
    @Bulkhead(name = ROLE_SERVICE, fallbackMethod = "bulkHeadGetRole", type = Bulkhead.Type.SEMAPHORE)
    public ResponseEntity<Role> addRole(@RequestBody Role requestRole) {
        if (requestRole == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        requestRole.setId(null);
        Role role = roleService.addRole(requestRole);
        if (role == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    // Обновить роль
    @PutMapping("/api/role/{id}")
    @Bulkhead(name = ROLE_SERVICE, fallbackMethod = "bulkHeadGetRole", type = Bulkhead.Type.SEMAPHORE)
    public ResponseEntity<Role> updateRole(@PathVariable("id") Long id, @RequestBody Role requestRole) {
        if (requestRole == null || requestRole.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        requestRole.setId(id);
        Role role = roleService.getRole(requestRole.getId());
        if (role == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        role = roleService.updateRole(requestRole);
        if (role == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    // Удалить роль
    @DeleteMapping("/api/role/{id}")
    @Bulkhead(name = ROLE_SERVICE, fallbackMethod = "bulkHeadDelRole", type = Bulkhead.Type.SEMAPHORE)
    public ResponseEntity<String> deleteRole(@PathVariable("id") Long roleId) {
        if (roleId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Role role = roleService.getRole(roleId);
        if (role == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        roleService.deleteRole(roleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<Role>> bulkHeadGetAllRole(Exception t) {
        log.info("bulkHeadGetAllRole");
        List<Role> role = new ArrayList<>();
        role.add(new Role(1L, "Роль"));
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    public ResponseEntity<Role> bulkHeadGetRole(Exception t) {
        log.info("bulkHeadGetRole");
        Role role = new Role(1L, "Роль");
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    public ResponseEntity<String> bulkHeadDelRole(Exception t) {
        log.info("bulkHeadDelRole");
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
