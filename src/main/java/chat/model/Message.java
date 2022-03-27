package chat.model;

import java.util.Objects;

public class Message {
    private Long id;
    private String text;
    private Long ownerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return text.equals(message.text) && ownerId.equals(message.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, ownerId);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }
}
