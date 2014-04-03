package com.bazaarvoice.polls.resource;

import com.bazaarvoice.polls.core.Poll;
import lombok.Data;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Data
@Path("/polls")
public class PollsResource {

    @POST
    public String getPolls(Poll poll) {
        if (poll == null) {
            return "hello world! For NO PRODUCT ";
        }
        String productId = poll.getProductId();
        String questionText = poll.getQuestionText();

        String retString = "Create poll for: \n" +
                " productId: " + productId + "\n" +
                " questionText: " + questionText;

        return retString;

    }

    @GET
    @Path("/{pollId}")
    public String getPollbyId() {
        return "hello world by PollId!";
    }

    @GET
    @Path("/product/{productId}")
    public String getPollsByProduct() {
        return "hello world by ProductId!";
    }
}
