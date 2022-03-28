package chat.model;

import java.util.Objects;

public class Message {
    private Long id;
    private String text;
    private User owner;

    public Message(String text, User owner){
        this.text = text;
        this.owner = owner;
    }

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return text.equals(message.text) && owner.equals(message.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, owner);
    }
}
