package chat.dto;

public class SendMessageDto {
    public String text;
    public String userName;

    public SendMessageDto(String text, String userName) {
        this.text = text;
        this.userName = userName;
    }
}
