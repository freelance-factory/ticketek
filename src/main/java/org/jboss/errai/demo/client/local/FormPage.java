package org.jboss.errai.demo.client.local;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.demo.client.shared.model.Ticket;
import org.jboss.errai.demo.client.shared.service.TicketService;
import org.jboss.errai.ui.nav.client.local.Page;
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

    private Ticket ticket;

    @Inject
    @DataField
    private TextArea description;

    @Inject
    @DataField
    private TextBox assignee;

    @Inject
    @DataField
    private ListBox status;

    @Inject
    @DataField
    private Button submitTicket;

    @Inject
    @DataField
    private Button cancelTicket;

    @Inject
    private Caller<TicketService> ticketService;

    @Inject
    private TransitionTo<TablePage> submitButtonClicked;

    private ListBox fillListBox(ListBox listBox) {
        listBox.addItem("Open");
        listBox.addItem("In Progress");
        listBox.addItem("Closed");
        return listBox;
    }

    @EventHandler("submitTicket")
    private void onSubmit(ClickEvent e){
        boolean isNew = ticket.getId() == null;
        if(isNew){
            create();
            submitButtonClicked.go();
        } else {
//            update();
            Window.alert("Hola");
            submitButtonClicked.go();
        }
    }

    @EventHandler("cancelTicket")
    private void onCancel(ClickEvent e){
        description.setText("");
        assignee.setText("");
    }

    private void create() {
        ticketService.call(new RemoteCallback<Void>(){
            @Override
            public void callback(Void aVoid) {
            }
        }).create(ticket);
    }

}
