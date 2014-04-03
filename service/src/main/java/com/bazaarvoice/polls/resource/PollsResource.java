package com.bazaarvoice.polls.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/polls")
public class PollsResource {

    @GET
    public String getPolls() {
        return "hello world!";
    }

}
