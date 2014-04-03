package com.bazaarvoice.polls;

import com.bazaarvoice.polls.config.PollsConfiguration;
import com.bazaarvoice.polls.core.Poll;
import com.bazaarvoice.polls.core.PollDAO;
import com.bazaarvoice.polls.resource.PollsResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
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
        bootstrap.addBundle(migration);
    }

    @Override
    public void run(PollsConfiguration configuration, Environment environment) {
        environment.jersey().register(createPollsResource());
    }

    private PollsResource createPollsResource() {
        PollDAO pollDAO = new PollDAO(hibernate.getSessionFactory());
        return new PollsResource(pollDAO);
    }

    private final HibernateBundle<PollsConfiguration> hibernate = new HibernateBundle<PollsConfiguration>(Poll.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(PollsConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    private final MigrationsBundle<PollsConfiguration> migration = new MigrationsBundle<PollsConfiguration>() {
        @Override
        public DataSourceFactory getDataSourceFactory(PollsConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };
}