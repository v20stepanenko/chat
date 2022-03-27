package chat.controller.users;

import chat.model.User;
import chat.service.AuthenticationService;
import chat.service.AuthenticationServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final AuthenticationService authenticationService = new AuthenticationServiceImpl();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String name = req.getParameter("name");
        try {
            User user = authenticationService.login(name);
            HttpSession session = req.getSession();
            session.setAttribute(USER_ID, user.getId());
            resp.sendRedirect("/chat");
        } catch (IOException e) {
            req.setAttribute("errorMsg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
