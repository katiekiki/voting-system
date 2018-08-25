package com.disney.rest.api.voting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
/**
 * An entity repository class to query database for entity information.
 *
 * @author Katie Lee
 */
public interface VoteEntityRepository extends JpaRepository<VoteEntity, Long> {

    @Query("select new com.disney.rest.api.voting.VoteEntity(v.id, v.type, v.name, v.upvotes, v.downvotes, v.createdAt) from Vote_Entity v order by v.createdAt desc")
    List<VoteEntity> getAllEntitiesByCreationDate();

    @Query("update Vote_Entity v set v.upvotes = (v.upvotes + 1) where v.id = ?1")
    @Modifying
    @Transactional
    void upvoteEntity(Long id);

    @Query("update Vote_Entity v set v.downvotes = (v.downvotes + 1) where v.id = ?1")
    @Modifying
    @Transactional
    void downvoteEntity(Long id);

}
