package org.jboss.errai.demo.client.shared.model;

public enum Status {
    OPEN("Open"),
    IN_PROGRESS("In Progress"),
    CLOSED("Closed");

    private String state;

    Status(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state;
    }
}