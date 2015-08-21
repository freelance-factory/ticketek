package org.jboss.errai.demo.client.shared.model;

public enum Status {
    OPEN("Open"),
    IN_PROGRESS("In Progress"),
    CLOSED("Closed");

    private String state;

    private Status(String state) {
        this.state = state;
    }
}