package com.bazaarvoice.polls.core;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class PollDAO extends AbstractDAO<Poll> {

    public PollDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Poll findById(String id) {
        return get(id);
    }

    public long create(Poll poll) {
        return persist(poll).getId();
    }

    public List<Poll> findAll() {
        return list(namedQuery("com.bazaarvoice.polls.data.Poll.findAll"));
    }
}
