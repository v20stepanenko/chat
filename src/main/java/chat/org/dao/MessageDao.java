package chat.org.dao;

import chat.org.exception.DataProcessingException;
import chat.org.model.Message;
import chat.org.model.User;
import chat.org.util.ConnectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {

    private static final Logger logger = LogManager.getLogger(MessageDao.class);

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
            logger.info("MessageDao.save was called SQL query: " + insertQuery);
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
            logger.info("MessageDao.loadMessages(limit) was called SQL query: " + selectQuery);
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
