package chat.service;

import chat.model.User;

public interface AuthenticationService {
    User login(String name);
}
