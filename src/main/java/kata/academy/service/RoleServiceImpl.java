package kata.academy.service;

import kata.academy.dao.RoleDao;
import kata.academy.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }

    @Override
    public List<Role> getAll() {
        return roleDao.getAll();
    }

    @Override
    @Transactional
    public void save(Role role) {
        roleDao.save(role);
    }
}
