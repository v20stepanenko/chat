package chat.org.server;

import chat.org.service.MessageService;
import chat.org.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import chat.org.dto.MessageDto;
import chat.org.model.Message;
import chat.org.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ServerEndpoint("/chat")
public class ChatServer {
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final MessageService messageService = new MessageService();
    private final UserService userService = new UserService();
    private static final Logger logger = LogManager.getLogger(ChatServer.class);

    @OnOpen
    public void onOpen(Session session) {
        logger.info("ChatServer.onOpen was called");
        logger.info("WebSocket session " + session.getId() + " opened a connection");
        List<MessageDto> sendMessageDtos = messageService.loadMessages(50);
        ObjectMapper objectMapper = new ObjectMapper();
        String toJson = "";
        try {
            toJson = objectMapper.writeValueAsString(sendMessageDtos);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        sessions.add(session);
        try {
            session.getBasicRemote().sendText(toJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String json, Session session) {
        try {
            Message message = parseToMessage(json);
            logger.info("chatServer onMessage was called, session: " + session
                    + "message: " + message);
            messageService.save(message);
            MessageDto sendMessageDto = new MessageDto(message.getText(), message.getOwner().getName());
            String sendJson = objectMapper.writeValueAsString(sendMessageDto);
            sessions.forEach(sessionElement -> {
                try {
                    sessionElement.getBasicRemote().sendText(sendJson);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private Message parseToMessage(String json) throws JsonProcessingException {
        JsonNode jsonNodeRoot = objectMapper.readTree(json);
        String messageText = jsonNodeRoot.get("text").asText();
        User owner = userService.findById(jsonNodeRoot.get("ownerId").asLong());
        return new Message(messageText, owner);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }
}