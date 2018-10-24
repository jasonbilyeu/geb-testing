package com.sendgrid.test.api.qatest.models;

import static com.sendgrid.test.api.qatest.util.ARandom.aRandom;

public class RandomUserBuilder extends User.UserBuilder {
    public RandomUserBuilder() {
        super();
        this
                .id((long)aRandom.getNumberBetween(10000, 1000000))
                .username(aRandom.getRandomText(3, 11))
                .email(aRandom.getEmailAddress())
                .password(aRandom.getRandomText(8, 20) + aRandom.getNumberText(4));
    }
}
