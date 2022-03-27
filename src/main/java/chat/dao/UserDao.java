package chat.dao;

import chat.exception.DataProcessingException;
import chat.model.User;
import chat.util.ConnectionUtil;

import java.sql.*;
import java.util.Optional;

public class UserDao {

    public User create(String name){
        User user = new User(name);
        String query = "INSERT INTO users (name) "
                + "VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createUserStatement = connection.prepareStatement(query,
                         Statement.RETURN_GENERATED_KEYS)) {
            createUserStatement.setString(1, name);
            createUserStatement.executeUpdate();
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
            User user = null;
            if (resultSet.next()) {
                user = getUser(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get user", e);
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        User user = new User(name);
        user.setId(id);
        return user;
    }

    public Optional<User> get(Long id) {
        String query = "SELECT * FROM users WHERE id = (?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getUserStatement = connection.prepareStatement(query)) {
            getUserStatement.setLong(1, id);
            ResultSet resultSet = getUserStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = getUser(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get user by id: " + id, e);
        }
    }
}
