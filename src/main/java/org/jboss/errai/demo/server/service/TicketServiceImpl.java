package org.jboss.errai.demo.server.service;

import org.jboss.errai.bus.server.annotations.Service;
import org.jboss.errai.demo.client.shared.model.Ticket;
import org.jboss.errai.demo.client.shared.service.TicketService;
import org.jboss.errai.demo.server.dao.TicketsDao;

import javax.inject.Inject;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{

    @Inject
    private TicketsDao ticketsDao;

    @Override
    public List<Ticket> getTableInfo() {
        return ticketsDao.getAll();
    }

    @Override
    public void save(Ticket ticket) {
        ticketsDao.create(ticket);
    }

    @Override
    public void delete(Long id) {
        ticketsDao.delete(id);
    }
}
