package com.bazaarvoice.polls;

import com.bazaarvoice.polls.resource.PollsResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.Environment;

public class PollsService extends Service<Configuration> {


    public static void main(String[] args)
            throws Exception {
        new PollsService().run(args);
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        bootstrap.setName("polls");
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        environment.addResource(new PollsResource());
    }
}