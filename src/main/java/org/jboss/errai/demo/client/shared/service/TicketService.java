package org.jboss.errai.demo.client.shared.service;

import org.jboss.errai.bus.server.annotations.Remote;
import org.jboss.errai.demo.client.shared.model.Ticket;

@Remote
public interface TicketService {

    public void create(Ticket ticket);

}
