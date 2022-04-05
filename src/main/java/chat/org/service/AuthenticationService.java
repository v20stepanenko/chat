package chat.org.service;

import chat.org.model.User;

public interface AuthenticationService {
    User login(String name);
}
