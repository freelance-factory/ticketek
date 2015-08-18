package org.jboss.errai.demo.client.local;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.demo.client.shared.model.Status;
import org.jboss.errai.demo.client.shared.model.Ticket;
import org.jboss.errai.demo.client.shared.service.TicketService;
import org.jboss.errai.ui.nav.client.local.HistoryToken;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShowing;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

@Page
@Templated("FormPage.html#app-template")
public class FormPage extends Composite {

    private Ticket ticket = new Ticket();

    @Inject
    @DataField
    public TextArea description;

    @Inject
    @DataField
    public TextBox assignee;

    @Inject
    @DataField
    public ListBox listBox;

    @Inject
    @DataField
    private Button submitTicket;

    @Inject
    @DataField
    private Button cancelTicket;

    @Inject
    private Caller<TicketService> ticketService;

    @Inject
    private TransitionTo<TablePage> returnToTablePage;

    @Inject
    private TransitionTo<MainPage> returnToMainPage;

    @PostConstruct
    private void init() {
        createListbox();

    }

    @PageShowing
    public void rowData(HistoryToken historyToken) {
        System.out.println("Hola");
        System.out.println(historyToken.getState().get("Test"));
    }

    @EventHandler("submitTicket")
    private void onSubmit(ClickEvent e) {
        createTicketFromWidgets();
        System.out.println(ticket.toString());
        boolean isNew = ticket.getId() == null;
        if (isNew) {
            save();
        } else {
//            update();
        }
        returnToTablePage.go();
    }

    @EventHandler("cancelTicket")
    private void onCancel(ClickEvent f){
        returnToMainPage.go();
        description.setText("");
        assignee.setText("");
        listBox.setSelectedIndex(0);
        if(ticket.getId()==null) {
        } else {
            returnToTablePage.go();
        }
    }

    private void save() {
        ticketService.call(new RemoteCallback<Void>(){
            @Override
            public void callback(Void aVoid) {
            }
        }).save(ticket);
    }

    private void createTicketFromWidgets() {
        ticket.setAssignee(assignee.getText());
        ticket.setDescription(description.getText());
        ticket.setStatus(Status.valueOf(listBox.getSelectedItemText()));
    }

    private void createListbox() {
        listBox.addItem(String.valueOf(Status.CLOSED));
        listBox.addItem(String.valueOf(Status.IN_PROGRESS));
        listBox.addItem(String.valueOf(Status.OPEN));
    }
}
