package com.esmt.misi2.lms.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "loan_requests")
public class LoanRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private boolean approved;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestStatus status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {this.approved = approved;}

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {this.status = status;}

}

