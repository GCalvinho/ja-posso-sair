package pt.i9.template.DB.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.i9.template.DB.Entities.User;

@Repository
public interface UsersRepository extends CrudRepository<User, String> {}