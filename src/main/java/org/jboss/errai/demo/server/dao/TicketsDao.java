package org.jboss.errai.demo.server.dao;

import org.jboss.errai.demo.client.shared.model.Ticket;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TicketsDao {

    @PersistenceContext(unitName = "forge-default")
    private EntityManager em;

    public Long create(Ticket entity) {
        em.persist(entity);
        return entity.getId();
    }

    public List<Ticket> getAll() {
        TypedQuery<Ticket> query = em.createNamedQuery("allTickets", Ticket.class);
        return query.getResultList();
    }

    public void delete(Long id) {
        Ticket uc = em.find(Ticket.class, id);
        em.remove(uc);
    }
}
