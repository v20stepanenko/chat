package chat.org.service;

import chat.org.dao.MessageDao;
import chat.org.dto.MessageDto;
import chat.org.model.Message;

import java.util.List;
import java.util.stream.Collectors;

public class MessageService {
    MessageDao messageDao = new MessageDao();
    public Message save(Message message){
        return messageDao.save(message);
    }

    public List<MessageDto> loadMessages(int limit) {
        return messageDao.loadMessages(limit).stream()
                .map(message -> new MessageDto(message.getText(), message.getOwner().getName()))
                .collect(Collectors.toList());
    }
}
