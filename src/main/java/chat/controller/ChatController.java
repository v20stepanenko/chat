package chat.controller;

import chat.model.User;
import chat.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChatController extends HttpServlet {
    public static final String USER_ID = "user_id";
    public static UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        User user = userService.findById(userId);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/chat.jsp").forward(req, resp);
    }
}
