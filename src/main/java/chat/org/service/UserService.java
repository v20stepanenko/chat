package chat.org.service;

import chat.org.dao.UserDao;
import chat.org.model.User;

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
