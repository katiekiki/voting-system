package com.disney.rest.api.voting;

public class VoteEntityNotFoundException extends Exception {

    // Parameterless Constructor
    public VoteEntityNotFoundException() {}

    // Constructor that accepts a message
    public VoteEntityNotFoundException(String message)
    {
        super(message);
    }


}
