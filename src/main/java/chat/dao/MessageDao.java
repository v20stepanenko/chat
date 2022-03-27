package chat.dao;

import chat.dto.SendMessageDto;
import chat.exception.DataProcessingException;
import chat.model.Message;
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
            createMessageStatement.setLong(1, message.getOwnerId());
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

    public List<SendMessageDto> loadMessages(int limit) {
        String selectQuery = "SELECT name, text"
                + " FROM messages m"
                + " JOIN users u on m.owner_id = u.id"
                + " ORDER BY m.timestamp"
                + " LIMIT ?";
        List<SendMessageDto> sendMessageDtos = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement loadMessagesStatement =
                     connection.prepareStatement(selectQuery)) {
            loadMessagesStatement.setLong(1, limit);
            ResultSet resultSet = loadMessagesStatement.executeQuery();
            while (resultSet.next()) {
                sendMessageDtos.add(parseMessagesDto(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't load pack messages from db", e);
        }
        return sendMessageDtos;
    }

    public SendMessageDto parseMessagesDto(ResultSet resultSet) throws SQLException {
        return new SendMessageDto(resultSet.getString(1),
                resultSet.getString((2)));
    }
}
