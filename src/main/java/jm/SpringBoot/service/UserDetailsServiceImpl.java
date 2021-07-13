package jm.SpringBoot.service;

import jm.SpringBoot.dao.UserDao;
import jm.SpringBoot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {



@Autowired
private EntityManager entityManager;
    public UserDetailsServiceImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    // «Пользователь» – это просто Object. В большинстве случаев он может быть
    //  приведен к классу UserDetails.
    // Для создания UserDetails используется интерфейс UserDetailsService, с единственным методом:
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException { try {
        User user = entityManager.createQuery("SELECT u FROM User u where u.name = :name", User.class)
                .setParameter("name", s)
                .getSingleResult();
        return user;
    } catch (NoResultException ex) {
        return null;
    }
    }
}
