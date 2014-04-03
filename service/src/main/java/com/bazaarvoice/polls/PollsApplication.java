package com.bazaarvoice.polls;

import com.bazaarvoice.polls.config.PollsConfiguration;
import com.bazaarvoice.polls.data.Poll;
import com.bazaarvoice.polls.resource.PollsResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class PollsApplication extends Application<PollsConfiguration> {

    public static void main(String[] args) throws Exception {
        new PollsApplication().run(args);
    }

    @Override
    public String getName() {
        return "polls";
    }

    @Override
    public void initialize(Bootstrap<PollsConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(PollsConfiguration configuration, Environment environment) {
        environment.jersey().register(new PollsResource());
    }

    private final HibernateBundle<PollsConfiguration> hibernate = new HibernateBundle<PollsConfiguration>(Poll.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(PollsConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };
}