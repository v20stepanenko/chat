package chat.org.controller;

import chat.org.dto.MessageDto;
import chat.org.model.User;
import chat.org.service.MessageService;
import chat.org.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatController extends HttpServlet {
    public static final String USER_ID = "user_id";
    public static UserService userService = new UserService();
    public static MessageService messageService = new MessageService();
    private static final Logger logger = LogManager.getLogger(ChatController.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        User user = userService.findById(userId);
        logger.info("СhatController doGet was called by user: " + user.getName());
        List<MessageDto> messages = messageService.loadMessages(50);
        req.setAttribute("user", user);
        req.setAttribute("messages", messages);
        req.getRequestDispatcher("/WEB-INF/views/chat.jsp").forward(req, resp);
    }
}
