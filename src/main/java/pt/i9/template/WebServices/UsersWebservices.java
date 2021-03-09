package pt.i9.template.WebServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pt.i9.template.DB.Entities.User;
import pt.i9.template.DB.Repository.UsersRepository;

import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
public class UsersWebservices {
    private static final Logger log = LoggerFactory.getLogger(UsersWebservices.class);

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/createUser")
    public String createUser(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ){
        try {
            User user = new User(name, email, password);
            usersRepository.save(user);
        }catch (Exception e){
            log.error(e.getMessage(), "No user created!");
            return "Error! No user created.";
        }
        return "User created!";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/updateUser/{email}")
    public String updateUser(
            @PathVariable String email
    ){
        Optional<User> user = usersRepository.findById(email);
        usersRepository.save(user.get());
        return "User updated!";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/deleteUser/{email}")
    public String deleteUser(
            @PathVariable String email
    ){
        Optional<User> user = usersRepository.findById(email);
        usersRepository.delete(user.get());
        return "User deleted!";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/changePassword/{email}")
    public String changePassword(
            @PathVariable String email,
            @RequestParam("password") String password
    ){
        Optional<User> user = usersRepository.findById(email);
        user.get().setPassword(password);
        usersRepository.save(user.get());
        return "Password Updated!";
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/allUsers")
    public String allUsers(){
        return usersRepository.findAll().toString();
    }

}