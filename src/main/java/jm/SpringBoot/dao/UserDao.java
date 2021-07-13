package jm.SpringBoot.dao;

import jm.SpringBoot.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;
@Repository
public interface UserDao extends CrudRepository<User,Long>  {

}
