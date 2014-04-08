package com.bazaarvoice.polls.resource;

import com.bazaarvoice.polls.data.Poll;
import com.bazaarvoice.polls.data.PollDAO;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@Data
@Path("/polls")
@Produces(MediaType.APPLICATION_JSON)
public class PollsResource {
    private final PollDAO pollDAO;

    @POST
    @UnitOfWork
    public long createPoll(Poll poll) {
        checkNotNull(poll);
        log.debug("Creating poll: " + poll.toString());
        return pollDAO.save(poll);
    }

    @GET
    @Path("/{pollId}")
    @UnitOfWork
    public Poll getPoll(@PathParam("pollId") Long pollId) {
        return pollDAO.findById(pollId);
    }

    @POST
    @Path("/{pollId}/{yesOrNo}")
    @UnitOfWork
    public void voteOnPoll(@PathParam("pollId") Long pollId, @PathParam("yesOrNo") String vote) {
        // Find the poll entity
        Poll poll = pollDAO.findById(pollId);
        checkNotNull(poll);

        // Update its count
        if ("yes".equalsIgnoreCase(vote)) {
            int count = poll.getYesCount();
            poll.setYesCount(++count);

        } else if ("no".equalsIgnoreCase(vote)) {
            int count = poll.getNoCount();
            poll.setNoCount(++count);

        } else {
            throw new IllegalArgumentException("Invalid vote value: " + vote);
        }

        // And save the updated poll
        pollDAO.save(poll);
    }

    @GET
    @Path("/product/{productId}")
    @UnitOfWork
    public List<Poll> getAllPollsForProduct(@PathParam("productId") String productId) {
        return pollDAO.findByProduct(productId);
    }
}
