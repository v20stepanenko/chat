package chat.service;

import chat.model.User;

public class AuthenticationServiceImpl implements AuthenticationService {

    UserService userService = new UserService();

    public User login(String name){
        return userService.createOrGet(name);
    }
}

