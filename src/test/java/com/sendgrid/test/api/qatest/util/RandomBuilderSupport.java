package com.sendgrid.test.api.qatest.util;

import com.sendgrid.test.api.qatest.models.RandomUserBuilder;

import java.util.UUID;

public class RandomBuilderSupport {

    public UUID uuid() {
        return UUID.randomUUID();
    }

    public RandomUserBuilder user() {
        return new RandomUserBuilder();
    }
}
