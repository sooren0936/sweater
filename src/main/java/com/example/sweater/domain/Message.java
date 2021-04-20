package com.example.sweater.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String text;

    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Message() {
    }

    public Message(final String text, final String tag, final User user) {
        this.text = text;
        this.tag = tag;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(final String tag) {
        this.tag = tag;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User author) {
        this.user = author;
    }

    public String getUserName() {
        return user != null ? user.getUsername() : "null";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Message message = (Message) o;
        return Objects.equals(id, message.id) &&
            Objects.equals(text, message.text) &&
            Objects.equals(tag, message.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, tag);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Message.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("text='" + text + "'")
            .add("tag='" + tag + "'")
            .toString();
    }
}
