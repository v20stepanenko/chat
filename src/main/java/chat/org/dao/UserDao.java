package chat.org.dao;

import chat.org.controller.users.LoginController;
import chat.org.exception.DataProcessingException;
import chat.org.model.User;
import chat.org.util.ConnectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Optional;

public class UserDao {

    private static final Logger logger = LogManager.getLogger(UserDao.class);

    public User create(String name){
        User user = new User(name);
        String query = "INSERT INTO users (name) "
                + "VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createUserStatement = connection.prepareStatement(query,
                         Statement.RETURN_GENERATED_KEYS)) {
            createUserStatement.setString(1, name);
            createUserStatement.executeUpdate();
            logger.info("UserDao.create was called SQL query: " + query);
            ResultSet resultSet = createUserStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getObject(1, Long.class));
            }
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create "
                    + user + ". ", e);
        }
    }

    public Optional<User> findByName(String name) {
        String query = "SELECT * FROM users WHERE name = (?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getUserStatement = connection.prepareStatement(query)) {
            getUserStatement.setString(1, name);
            ResultSet resultSet = getUserStatement.executeQuery();
            logger.info("UserDao.findByName was called SQL query: " + query);
            User user = null;
            if (resultSet.next()) {
                user = getUser(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get user", e);
        }
    }

    public Optional<User> get(Long id) {
        String query = "SELECT * FROM users WHERE id = (?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getUserStatement = connection.prepareStatement(query)) {
            getUserStatement.setLong(1, id);
            ResultSet resultSet = getUserStatement.executeQuery();
            logger.info("UserDao.get was called SQL query: " + query);
            User user = null;
            if (resultSet.next()) {
                user = getUser(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get user by id: " + id, e);
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        User user = new User(name);
        user.setId(id);
        return user;
    }
}
