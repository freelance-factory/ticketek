package org.jboss.errai.demo.client.local;

import javax.inject.Inject;

import org.jboss.errai.demo.client.shared.model.Ticket;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

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
    private Button submit;

    private ListBox fillListBox(ListBox listBox) {
        listBox.addItem("Open");
        listBox.addItem("In Progress");
        listBox.addItem("Closed");
        return listBox;
    }

    @EventHandler("submit")
    private void onSubmit(ClickEvent e){
        boolean isNew = ticket.getId() == null;
        if(isNew){
            create()
        } else {
            update()
        }
    }

}
