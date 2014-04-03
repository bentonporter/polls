package com.bazaarvoice.polls.core;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class PollDAO extends AbstractDAO<Poll> {

    public PollDAO(SessionFactory factory) {
        super(factory);
    }

    public Poll findById(Long id) {
        return get(id);
    }

    public long create(Poll poll) {
        return persist(poll).getId();
    }

    public List<Poll> findAll() {
        return list(namedQuery("com.bazaarvoice.polls.data.Poll.findAll"));
    }

//    public Poll findByProductId(String productId) {
//        throw new IllegalStateException("this needs to be implemented");
//    }
}
