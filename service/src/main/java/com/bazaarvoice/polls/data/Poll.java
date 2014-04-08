package com.bazaarvoice.polls.data;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "poll")
@NamedQueries({
        @NamedQuery(
                name = "com.bazaarvoice.polls.core.Poll.findAll",
                query = "SELECT p FROM Poll p"
        ),
        @NamedQuery(
            name = "com.bazaarvoice.polls.core.Poll.findByProduct",
            query = "SELECT p FROM Poll p WHERE p.productId = :productId"
        )
})
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "productId", nullable = false)
    private String productId;

    @Column(name = "questionText", nullable = false)
    private String questionText;

    @Column(name = "yesCount", nullable = false)
    private int yesCount;

    @Column(name = "noCount", nullable = false)
    private int noCount;
}