package chat.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import chat.dto.SendMessageDto;
import chat.model.Message;
import chat.model.User;
import chat.service.MessageService;
import chat.service.UserService;

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
    private final MessageService messageService = new MessageService();
    private final UserService userService = new UserService();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println(session.getId() + " opened a connection");
        List<SendMessageDto> sendMessageDtos = messageService.loadMessages(50);
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
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Message message = objectMapper.readValue(json, Message.class);
            messageService.save(message);
            User user = userService.findById(message.getOwnerId());
            SendMessageDto sendMessageDto = new SendMessageDto(message.getText(), user.getName());
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

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }
}