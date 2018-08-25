package com.disney.rest.api.voting;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * An entity class that holds entity votes information.
 *
 * @author Katie Lee
 */
@javax.persistence.Entity(name = "Vote_Entity")
@Table
public class VoteEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String type;
    private String name;
    private int upvotes;
    private int downvotes;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    public VoteEntity(Long id, String type, String name, int upvotes, int downvotes, Date createdAt) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.createdAt = createdAt;
    }

    public VoteEntity() {
    }


    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }


}
