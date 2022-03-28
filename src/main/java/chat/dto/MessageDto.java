package chat.dto;

public class MessageDto {
    public String text;
    public String userName;

    public MessageDto(String text, String userName) {
        this.text = text;
        this.userName = userName;
    }
}
