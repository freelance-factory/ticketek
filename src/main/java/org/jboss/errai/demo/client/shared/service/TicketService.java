package org.jboss.errai.demo.client.shared.service;

import org.jboss.errai.bus.server.annotations.Remote;
import org.jboss.errai.demo.client.shared.model.Ticket;

import java.util.List;

@Remote
public interface TicketService {

    public List<Ticket> getTableInfo();
    public void save(Ticket ticket);
    public void delete(Long id);
    public void update(Long id, Ticket ticket);

}
