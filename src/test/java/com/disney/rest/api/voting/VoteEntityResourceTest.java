package com.disney.rest.api.voting;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * A test class for VoteEntityResource
 *
 * @author Katie Lee
 */
@RunWith(MockitoJUnitRunner.class)
public class VoteEntityResourceTest {

    @Mock
    VoteEntityRepository voteEntityRepository;

    @Mock
    ServletUriComponentsBuilder servletUriComponentsBuilder;


    @InjectMocks
    VoteEntityResource voteEntityResource;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private MockHttpServletRequest request;

    @Mock
    private RequestAttributes attrs;


    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testRetrieveAllEntitiesByCreationDate() throws ParseException {

        VoteEntity voteEntity1 = new VoteEntity(new Long(1),"youtube video", "youtube video 1", 3, 4,formatter.parse("2018-08-01 18:47:52"));
        VoteEntity voteEntity2 = new VoteEntity(new Long(2),"youtube video", "youtube video 2", 5, 8,formatter.parse("2018-07-29 06:56:11"));
        VoteEntity voteEntity3 = new VoteEntity(new Long(3),"youtube video", "youtube video 3", 6, 7,formatter.parse("2018-07-14 14:08:28"));
        VoteEntity voteEntity4 = new VoteEntity(new Long(4),"youtube video", "youtube video 4", 7, 6,formatter.parse("2018-07-11 09:16:48"));

        List<VoteEntity> entities = new ArrayList<>();
        entities.add(voteEntity1);
        entities.add(voteEntity2);
        entities.add(voteEntity3);
        entities.add(voteEntity4);

        when(voteEntityRepository.getAllEntitiesByCreationDate()).thenReturn(entities);

        List<VoteEntity> returnedEntities = voteEntityResource.retrieveAllEntitiesByCreationDate();

        assertEquals(returnedEntities.size(),4);
        assertEquals(entities.get(0).getId(), new Long(1));
        assertEquals(entities.get(0).getUpvotes(), 3);
        assertEquals(entities.get(0).getDownvotes(), 4);
        assertEquals(entities.get(0).getName(), "youtube video 1");
        assertEquals(entities.get(0).getType(), "youtube video");
        assertEquals(entities.get(0).getCreatedAt(), formatter.parse("2018-08-01 18:47:52"));


    }

    @Test
    public void testRetrieveEntityTotalVotes() throws ParseException, VoteEntityNotFoundException {
        VoteEntity voteEntity1 = new VoteEntity(new Long(1),"election candidates", "candidate A", 3, 4,formatter.parse("2018-08-01 18:47:52"));

        Long testId = new Long(1);

        when(voteEntityRepository.findById(new Long(1))).thenReturn(Optional.of(voteEntity1));
        assertEquals(voteEntityResource.retrieveEntityTotalVotes(testId).getId(),new Long(1));
        assertEquals(voteEntityResource.retrieveEntityTotalVotes(testId).getName(),"candidate A");
        assertEquals(voteEntityResource.retrieveEntityTotalVotes(testId).getUpvotes(),3);
        assertEquals(voteEntityResource.retrieveEntityTotalVotes(testId).getDownvotes(),4);

    }

    @Test (expected = VoteEntityNotFoundException.class)
    public void testRetrieveEntityTotalVotesNotFoundException() throws ParseException, VoteEntityNotFoundException {
        VoteEntity voteEntity1 = new VoteEntity(new Long(1),"election candidates", "candidate A", 3, 4,formatter.parse("2018-08-01 18:47:52"));

        Long testId = new Long(2);

        when(voteEntityRepository.findById(testId)).thenReturn(Optional.empty());
        voteEntityResource.retrieveEntityTotalVotes(testId);

    }

    @Test
    public void testUpVoteEntity() throws ParseException {
        VoteEntity voteEntity1 = new VoteEntity(new Long(1),"election candidates", "candidate A", 3, 4,formatter.parse("2018-08-01 18:47:52"));

        Long testId = new Long(1);

        when(voteEntityRepository.findById(testId)).thenReturn(Optional.of(voteEntity1));
        assertEquals(voteEntityResource.upVoteEntity(testId),ResponseEntity.ok().build());
    }

    @Test
    public void testUpVoteEntityNotFound() throws ParseException {
        VoteEntity voteEntity1 = new VoteEntity(new Long(1),"election candidates", "candidate A", 3, 4,formatter.parse("2018-08-01 18:47:52"));

        Long testId = new Long(2);

        when(voteEntityRepository.findById(testId)).thenReturn(Optional.empty());
        assertEquals(voteEntityResource.upVoteEntity(testId),ResponseEntity.notFound().build());
    }

    @Test
    public void testDownVoteEntity() throws ParseException {
        VoteEntity voteEntity1 = new VoteEntity(new Long(1),"election candidates", "candidate A", 3, 4,formatter.parse("2018-08-01 18:47:52"));

        Long testId = new Long(1);

        when(voteEntityRepository.findById(testId)).thenReturn(Optional.of(voteEntity1));
        assertEquals(voteEntityResource.downVoteEntity(testId),ResponseEntity.ok().build());
    }

    @Test
    public void testDownVoteEntityNotFound() throws ParseException {
        VoteEntity voteEntity1 = new VoteEntity(new Long(1),"election candidates", "candidate A", 3, 4,formatter.parse("2018-08-01 18:47:52"));

        Long testId = new Long(2);

        when(voteEntityRepository.findById(testId)).thenReturn(Optional.empty());
        assertEquals(voteEntityResource.upVoteEntity(testId),ResponseEntity.notFound().build());
    }

    @Test
    public void testCreateNewEntity() throws URISyntaxException, ParseException {
        VoteEntity voteEntity = new VoteEntity(null,"election candidates", "candidate A", 3, 4, null);
        VoteEntity voteEntitySaved = new VoteEntity(new Long(1),"election candidates", "candidate A", 3, 4,formatter.parse("2018-08-01 18:47:52"));

        URI mockURI = new URI("http://localhost:8080/entities/1");

        this.request = new MockHttpServletRequest();
        this.request.setScheme("http");
        this.request.setServerName("localhost");
        this.request.setServerPort(8080);
        this.request.setRequestURI("/entities");
        this.request.setContextPath("/entities");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(this.request));

        when(voteEntityRepository.save(voteEntity)).thenReturn(voteEntitySaved);
        assertEquals(voteEntityResource.createNewEntity(voteEntity),ResponseEntity.created(mockURI).build());
        assertEquals(voteEntitySaved.getName(),voteEntity.getName());
        assertNotNull(voteEntitySaved.getId());
        assertNotNull(voteEntitySaved.getCreatedAt());

    }
}
