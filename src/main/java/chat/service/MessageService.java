package chat.service;

import chat.dao.MessageDao;
import chat.dto.SendMessageDto;
import chat.model.Message;

import java.util.List;

public class MessageService {
    MessageDao messageDao = new MessageDao();
    public Message save(Message message){
        return messageDao.save(message);
    }

    public List<SendMessageDto> loadMessages(int limit) {
        return messageDao.loadMessages(limit);
    }
}
