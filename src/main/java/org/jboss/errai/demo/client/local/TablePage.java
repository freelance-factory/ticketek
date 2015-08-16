package org.jboss.errai.demo.client.local;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.demo.client.shared.model.Ticket;
import org.jboss.errai.demo.client.shared.service.TicketService;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

@Page
@Templated("TablePage.html#app-template")
public class TablePage extends Composite{

    @Inject
    @DataField
    private Button addNew;

    @Inject
    private TransitionTo<FormPage> addNewButtonClicked;

    @Inject
    @DataField
    private FlexTable table = new FlexTable();

    @Inject
    private Caller<TicketService> ticketService;

    @EventHandler("addNew")
    private void onAddNew(ClickEvent e){
        addNewButtonClicked.go();
    }

    @PostConstruct
    private void init() {
        buildTable();
    }

    private void buildTable() {
        ticketService.call(new RemoteCallback<List<Ticket>>() {
            @Override
            public void callback(List<Ticket> tickets) {
                setTableHeader();
                int j = 0;
                for (final Ticket ticket : tickets) {
                    Button edit = new Button("Edit");
                    edit.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent clickEvent) {
                            addNewButtonClicked.go();
                            FormPage.ticket.setId(ticket.getId());
                            FormPage.ticket.setStatus(ticket.getStatus());
                            FormPage.ticket.setDescription(ticket.getDescription());
                            FormPage.ticket.setAssignee(ticket.getAssignee());
                        }
                    });
                    Button delete = new Button("Delete");
                    delete.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent clickEvent) {
                            ticketService.call().delete(ticket.getId());
                            table.clear();
                            buildTable();
                        }
                    });
                    buildRows(j, ticket, edit, delete);
                    j++;
                }
            }
        }).getTableInfo();
    }

    private void buildRows(int j, Ticket ticket, Button edit, Button delete) {
        table.setWidget(j+1,0, new Label(ticket.getId().toString()));
        table.setWidget(j+1,1, new Label(ticket.getDescription()));
        table.setWidget(j+1,2, new Label(ticket.getStatus().toString()));
        table.setWidget(j+1,3, new Label(ticket.getAssignee()));
        table.setWidget(j+1,4, edit);
        table.setWidget(j+1,5, delete);
    }

    private void setTableHeader() {
        table.setWidget(0,0, new Label("Id"));
        table.setWidget(0,1, new Label("Description"));
        table.setWidget(0,2, new Label("Status"));
        table.setWidget(0,3, new Label("Assignee"));
    }
}
