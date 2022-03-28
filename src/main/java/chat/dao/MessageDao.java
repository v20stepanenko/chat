package chat.dao;

import chat.exception.DataProcessingException;
import chat.model.Message;
import chat.model.User;
import chat.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    public Message save(Message message) {
        String insertQuery = "INSERT INTO messages (owner_id, text)"
                + "VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createMessageStatement =
                        connection.prepareStatement(
                             insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            createMessageStatement.setLong(1, message.getOwner().getId());
            createMessageStatement.setString(2, message.getText());
            createMessageStatement.executeUpdate();
            ResultSet resultSet = createMessageStatement.getGeneratedKeys();
            if (resultSet.next()) {
                message.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create message " + message, e);
        }
        return message;
    }

    public List<Message> loadMessages(int limit) {
        String selectQuery = "SELECT name, text, owner_id"
                + " FROM messages m"
                + " JOIN users u on m.owner_id = u.id"
                + " ORDER BY m.timestamp"
                + " LIMIT ?";
        List<Message> sendMessages = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement loadMessagesStatement =
                     connection.prepareStatement(selectQuery)) {
            loadMessagesStatement.setLong(1, limit);
            ResultSet resultSet = loadMessagesStatement.executeQuery();
            while (resultSet.next()) {
                sendMessages.add(parseMessage(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't load pack messages from db", e);
        }
        return sendMessages;
    }

    public Message parseMessage(ResultSet resultSet) throws SQLException {
        User owner = new User(resultSet.getString("name"));
        owner.setId(resultSet.getLong("owner_id"));
        return new Message(resultSet.getString("text"), owner);
    }
}
