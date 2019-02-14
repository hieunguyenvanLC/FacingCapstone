package tuhoc.lan3.service;


import org.springframework.stereotype.Service;
import tuhoc.lan3.entity.User;
import tuhoc.lan3.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        List<User> users = this.userRepository.findAll();
        return users;
    }

    public User findById(Integer id){
        return userRepository.findById(id).get();
    }

    public void createUser(String username, String fullname, boolean gender, String password) {
        User userEnity=new User();
        userEnity.setUsername(username);
        userEnity.setFullname(fullname);
        userEnity.setGender(gender);
        userEnity.setPassword(password);
        userRepository.save(userEnity);
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }
}
