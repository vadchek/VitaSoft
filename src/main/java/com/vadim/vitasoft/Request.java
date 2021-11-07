package com.vadim.vitasoft;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "textofrequest")
    private String text;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private RequestStatus status;
    @Column(name = "authorname")
    private String authorName;
    @Column(name = "timeofcreation")
    private LocalDateTime timeOfCreation;

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public LocalDateTime getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(LocalDateTime timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }
}
