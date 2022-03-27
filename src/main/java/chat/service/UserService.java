package chat.service;

import chat.dao.UserDao;
import chat.model.User;

import java.util.Optional;

public class UserService {
    UserDao userDao = new UserDao();

    public User createOrGet(String name){
        Optional<User> user = userDao.findByName(name);
        return user.orElseGet(() -> userDao.create(name));
    }

    public User findById(Long id){
        Optional<User> user = userDao.get(id);
        return user.orElseThrow();
    }
}
