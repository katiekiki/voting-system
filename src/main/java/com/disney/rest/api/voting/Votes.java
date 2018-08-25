package com.disney.rest.api.voting;


/**
 * A POJO class to return the upvotes and downvotes for an entity.
 *
 * @author Katie Lee
 */
public class Votes {

    private Long id;
    private String name;
    private int upvotes;
    private int downvotes;

    public Votes(Long id, String name, int upvotes, int downvotes) {
        this.id = id;
        this.name = name;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
