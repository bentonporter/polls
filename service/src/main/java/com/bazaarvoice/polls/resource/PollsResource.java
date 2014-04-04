package com.bazaarvoice.polls.resource;

import com.bazaarvoice.polls.core.Poll;
import com.bazaarvoice.polls.core.PollDAO;
import com.google.common.base.Preconditions;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Slf4j
@Data
@Path("/polls")
@Produces(MediaType.APPLICATION_JSON)
public class PollsResource {
    private final PollDAO pollDAO;

    @POST
    @UnitOfWork
    public long createPoll(Poll poll, @Context HttpServletResponse response) {
        setHeaders(response);
        Preconditions.checkNotNull(poll);
        log.debug("Creating poll: " + poll.toString());
        return pollDAO.save(poll);
    }

    @GET
    @Path("/{pollId}")
    @UnitOfWork
    public Poll getPoll(@PathParam("pollId") Long pollId, @Context HttpServletResponse response) {
        setHeaders(response);
        return pollDAO.findById(pollId);
    }

    @GET
    @Path("/{pollId}/{yesOrNo}")
    @UnitOfWork
    public void vote(@PathParam("pollId") Long pollId, @PathParam("yesOrNo") String vote, @Context HttpServletResponse response) {
        setHeaders(response);

        // Find the poll entity
        Poll poll = pollDAO.findById(pollId);
        Preconditions.checkNotNull(poll);               // TODO could make this a 404

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

        // And save it
        pollDAO.save(poll);
    }

    @GET
    @Path("/product/{productId}")
    @UnitOfWork
    // API endpoint for polls by productid
    public List<Poll> getPollsByProduct(@PathParam("productId") String productId, @Context HttpServletResponse response) {
        setHeaders(response);
        return pollDAO.findByProduct(productId);
    }

    private void setHeaders(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.addHeader("Access-Control-Allow-Methods", "POST, OPTIONS, GET");
    }
}
