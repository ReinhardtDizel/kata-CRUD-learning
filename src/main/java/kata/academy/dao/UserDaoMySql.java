package kata.academy.dao;

import kata.academy.dto.UserDto;
import kata.academy.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoMySql implements UserDao {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    @Override
    public void updateUser(UserDto userDto) {
        User updated = em.find(User.class, userDto.id);
        updated.setName(userDto.getName());
        updated.setEmail(userDto.getEmail());
        em.merge(updated);
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
