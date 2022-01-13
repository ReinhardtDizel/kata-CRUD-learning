package kata.academy.dao;

import kata.academy.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleDaoMySql implements RoleDao {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    @Override
    public Role getRoleByName(String name) {
        try {
            Query query = em.createQuery("select role from Role role where role.name =:param");
            query.setParameter("param", name);
            return (Role) query.getSingleResult();
        } catch (NoResultException ignored) {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> getAll() {
        return em.createQuery("select role from Role role").getResultList();
    }

    @Override
    public void save(Role role) {
        try {
            em.persist(role);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
