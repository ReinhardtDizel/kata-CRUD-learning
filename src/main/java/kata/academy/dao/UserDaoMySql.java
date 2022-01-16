package kata.academy.dao;

import kata.academy.dto.UserDto;
import kata.academy.model.Role;
import kata.academy.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;

@Repository
public class UserDaoMySql implements UserDao {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    @Override
    public User getUserByLogin(String s) {
        try {
            Query query = em.createQuery("select user from User user where user.login =:param");
            query.setParameter("param", s);
            return (User) query.getSingleResult();
        } catch (NoResultException ignored) {
            return null;
        }
    }

    @Override
    public void updateUser(UserDto user, List<Role> roles) {
        try {
            User updated = em.find(User.class, user.getId());
            updated.setName(user.getName());
            updated.setLogin(user.getLogin());
            updated.setRoles(new HashSet<>(roles));
            em.merge(updated);
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getById(long id) {
        return em.find(User.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return em.createQuery("select user from User user").getResultList();
    }

    @Override
    public void saveUser(User user) {
        try {
            em.persist(user);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(long id) {
        try {
            User deleted = em.find(User.class, id);
            em.remove(deleted);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

}
