package chat.org.service;

import chat.org.model.User;

public class AuthenticationServiceImpl implements AuthenticationService {

    UserService userService = new UserService();

    public User login(String name){
        return userService.createOrGet(name);
    }
}

