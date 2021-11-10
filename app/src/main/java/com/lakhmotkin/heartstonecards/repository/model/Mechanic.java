package com.lakhmotkin.heartstonecards.repository.model;

import java.io.Serializable;

/**
 * Created by Igor Lakhmotkin on 24.02.2018, for HeartstoneAssessment.
 */

public class Mechanic implements Serializable{
    private String name;

    public Mechanic(String name) {
        this.name = name;
    }

    public Mechanic() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
