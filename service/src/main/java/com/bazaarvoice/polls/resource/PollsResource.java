package com.bazaarvoice.polls.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/polls")
public class PollsResource {

    public PollsResource() {
    }

    @POST
    // this works with: http://localhost:4000/polls/prod001
    @Path("/{productId}")
    public String getPolls(@PathParam("productId") String productId) {
        return "hello world! For product: " + productId;
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
