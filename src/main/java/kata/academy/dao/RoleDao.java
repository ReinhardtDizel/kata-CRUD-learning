package kata.academy.dao;

import kata.academy.model.Role;

import java.util.List;

public interface RoleDao {
    Role getRoleByName(String name);

    List<Role> getAll();

    void save(Role role);
}
