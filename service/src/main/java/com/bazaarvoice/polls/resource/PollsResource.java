package com.bazaarvoice.polls.resource;

import com.bazaarvoice.polls.core.Poll;
import com.bazaarvoice.polls.core.PollDAO;
import com.google.common.base.Preconditions;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Slf4j
@Data
@Path("/polls")
@Produces(MediaType.APPLICATION_JSON)
public class PollsResource {
    private final PollDAO pollDAO;

    @POST
    @UnitOfWork
    public long createPoll(Poll poll) {
        Preconditions.checkNotNull(poll);
        log.debug("Creating poll: " + poll.toString());
        return pollDAO.create(poll);
    }

    @GET
    @Path("/{pollId}")
    @UnitOfWork
    public Poll getPollbyId(@PathParam("pollId") Long pollId) {
        return pollDAO.findById(pollId);
    }

    @GET
    @Path("/product/{productId}")
    @UnitOfWork
    public Poll getPollsByProduct(@PathParam("productId") String productId) {
        return null;
//        return pollDAO.findByProductId(productId);
    }
}
