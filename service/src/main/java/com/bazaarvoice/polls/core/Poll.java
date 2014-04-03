package com.bazaarvoice.polls.core;

import lombok.Data;

@Data
public class Poll {
    private int id;
    private String productId;
    private String questionText;
    private int yesCount;
    private int noCount;
}