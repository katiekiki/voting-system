package com.disney.rest.api.voting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.*;

/**
 * A controller class to manage endpoints for the Disney's Entity Voting rest api.
 *
 * @author Katie Lee
 */
@RestController
public class VoteEntityResource {

    @Autowired
    private VoteEntityRepository voteEntityRepository;

    /**
     * A method to retrieve the list of entities order by creation date.
     *
     */
    @RequestMapping(value = "/entities", method = RequestMethod.GET)
    public List<VoteEntity> retrieveAllEntitiesByCreationDate() {
        List<VoteEntity> entities = voteEntityRepository.getAllEntitiesByCreationDate();

        return entities;
    }

    /**
     * A method to retrieve the total vote counts for an entity.
     * @param id the id of a specified entity.
     */
    @RequestMapping(value = "/entities/{id}/votes", method = RequestMethod.GET)
    public Votes retrieveEntityTotalVotes(@PathVariable long id) throws VoteEntityNotFoundException {
        Optional<VoteEntity> entity = voteEntityRepository.findById(id);

        if (!entity.isPresent())
            throw new VoteEntityNotFoundException("Entity with id : " + id + " not found");

        Votes votes = new Votes(entity.get().getId(),entity.get().getName(),entity.get().getUpvotes(),entity.get().getDownvotes());

        return votes;
    }

    /**
     * A method to increment the up-vote count for an entity.
     * @param id the id of a specified entity.
     */
    @RequestMapping(value = "/entities/{id}/upvote", method = RequestMethod.PUT)
    public ResponseEntity<Object> upVoteEntity(@PathVariable Long id) {

        Optional<VoteEntity> entity = voteEntityRepository.findById(id);

        if (!entity.isPresent())
            return ResponseEntity.notFound().build();

        else {
            voteEntityRepository.upvoteEntity(id);
            return ResponseEntity.ok().build();
        }
    }


    /**
     * A method to increment the down-vote count for an entity.
     * @param id the id of a specified entity.
     */
    @RequestMapping(value = "/entities/{id}/downvote", method = RequestMethod.PUT)
    public ResponseEntity<Object> downVoteEntity(@PathVariable Long id) {
        Optional<VoteEntity> entity = voteEntityRepository.findById(id);

        if (!entity.isPresent())
            return ResponseEntity.notFound().build();

        else {
            voteEntityRepository.downvoteEntity(id);
            return ResponseEntity.ok().build();
        }

    }


    /**
     * A method to increment the down-vote count for an entity.
     * @param voteEntity the attributes of the specified entity.
     */
    @RequestMapping(value = "/entities", method = RequestMethod.POST)
    public ResponseEntity<Object> createNewEntity(@RequestBody VoteEntity voteEntity) {
        VoteEntity saveVoteEntity = voteEntityRepository.save(voteEntity);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(saveVoteEntity.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

}



