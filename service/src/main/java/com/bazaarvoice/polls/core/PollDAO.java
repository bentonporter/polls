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

    public long save(Poll poll) {
        return persist(poll).getId();
    }

    public List<Poll> findByProduct(String productId) {
        return list(namedQuery("com.bazaarvoice.polls.core.Poll.findByProduct").setParameter("productId", productId));
    }

}
