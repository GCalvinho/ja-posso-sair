package pt.i9.template;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pt.i9.template.DB.Entities.User;
import pt.i9.template.DB.Repository.UsersRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryEntitiesTests {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void whenFindingCustomerById_thenCorrect() {
        usersRepository.save(new User("Goncalo", "goncalo.oliveira@domain.com", "1234"));
        assertThat(usersRepository.findById("goncalo.oliveira@sap.com")).isInstanceOf(Optional.class);
    }

    @Test
    public void whenFindingAllCustomers_thenCorrect() {
        usersRepository.save(new User("Goncalo", "goncalo@domain.com", "1234"));
        usersRepository.save(new User("Oliveira", "oliveira@domain.com", "1234"));
        assertThat(usersRepository.findAll()).isInstanceOf(List.class);
    }

    //TODO tests for all webservice methods
}