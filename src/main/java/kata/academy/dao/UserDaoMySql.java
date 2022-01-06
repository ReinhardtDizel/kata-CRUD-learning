package kata.academy.dao;

import kata.academy.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoMySql implements UserDao {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    @Override
    public User findUserByUserName(String s) {
        Query query = em.createQuery("select user from User user where user.email =:param");
        query.setParameter("param", s);
        User exist = null;
        try {
            exist = (User) query.getSingleResult();
        } catch (NoResultException ignored) {
        }
        return exist;
    }

    @Override
    public void updateUser(long id, User user) {
        try {
            User updated = em.find(User.class, id);
            updated.setName(user.getName());
            updated.setEmail(user.getEmail());
            updated.setRoles(user.getRoles());
            em.merge(updated);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> getById(long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return em.createQuery("select user from User user").getResultList();
    }

    @Override
    public void saveUser(User user) {
        try {
            if (!em.contains(user)) {
                if (findUserByUserName(user.getEmail()) == null) {
                    em.persist(user);
                } else {
                    throw new EntityExistsException();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(long id) {
        try {
            User deleted = em.find(User.class, id);
            em.remove(deleted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
