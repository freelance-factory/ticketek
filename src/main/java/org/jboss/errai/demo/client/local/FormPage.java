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

//    private long id;

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
    public void bringTablePageData(HistoryToken historyToken) {
//        System.out.println("Hola");
//        System.out.println(historyToken.getState().get("Assignee"));
        assignee.setText(String.valueOf(historyToken.getState().get("Assignee")).replace("[", "").replace("]", ""));
        description.setText(String.valueOf(historyToken.getState().get("Description")).replace("[", "").replace("]", ""));
        if(!String.valueOf(historyToken.getState().get("StatusIndex")).replace("[", "").replace("]", "").equals("")) {
            listBox.setSelectedIndex(Integer.parseInt(String.valueOf(historyToken.getState().get("StatusIndex")).replace("[", "").replace("]", "")));
        }
        if(!String.valueOf(historyToken.getState().get("Id")).replace("[", "").replace("]", "").equals("")) {
            ticket.setId(Long.parseLong(String.valueOf(historyToken.getState().get("Id")).replace("[", "").replace("]", "")));
        }
//        ticket.setId(Long.parseLong(String.valueOf(historyToken.getState().get("Id"))));
//        ticket.setId(Long.valueOf(String.valueOf(historyToken.getState().get("Id"))));
    }

    @EventHandler("submitTicket")
    private void onSubmit(ClickEvent e) {
        createTicketFromWidgets();
//        System.out.println(ticket.toString());
//        ticket.setId(id);
        boolean isNew = ticket.getId() == null;
        if (isNew) {
            save();
        } else {
            update();
            System.out.println(ticket.getAssignee() + ticket.getDescription() + ticket.getStatus());
        }
        returnToTablePage.go();
    }

    @EventHandler("cancelTicket")
    private void onCancel(ClickEvent f){
        returnToMainPage.go();
        cleanDatafields();
        if(ticket.getId()!= null) {
            returnToTablePage.go();
        }
    }

    private void cleanDatafields() {
        description.setText("");
        assignee.setText("");
        listBox.setSelectedIndex(0);
    }

    private void createListbox() {
        listBox.addItem(String.valueOf(Status.CLOSED));
        listBox.addItem(String.valueOf(Status.IN_PROGRESS));
        listBox.addItem(String.valueOf(Status.OPEN));
    }

    private void createTicketFromWidgets() {
        ticket.setAssignee(assignee.getText());
        ticket.setDescription(description.getText());
        ticket.setStatus(Status.valueOf(listBox.getSelectedItemText()));
    }

    private void save() {
        ticketService.call(new RemoteCallback<Void>(){
            @Override
            public void callback(Void aVoid) {
            }
        }).save(ticket);
    }

    private void update() {
        ticketService.call(new RemoteCallback<Void>(){
            @Override
            public void callback(Void aVoid) {
            }
        }).update(ticket.getId(), ticket);
    }
}
