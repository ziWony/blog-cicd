package shop.mtcoding.blogv2.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserQueryRepository {
    private final EntityManager em;

    // insert
    public User save(User user){
        em.persist(user);
        return user;
    }

    // select
    public User findById(int id){
        return em.find(User.class, id);
    }

    // delete
    public void deleteById(int id){
        User user = em.find(User.class, id);
        em.remove(user);
    }

    // select all (JPQL)
    public List<User> findAll(){
        Query query = em.createQuery("select u from User u order by u.id");
        return query.getResultList();
    }
}
