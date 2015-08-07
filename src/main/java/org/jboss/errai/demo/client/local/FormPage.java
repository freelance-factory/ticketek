package org.jboss.errai.demo.client.local;

import javax.inject.Inject;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.demo.client.shared.model.Ticket;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

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
    private TextBox asignee;

    @Inject
    @DataField
    private ListBox status;

    private ListBox fillListBox(ListBox listBox) {
        listBox.addItem("Open");
        listBox.addItem("In Progress");
        listBox.addItem("Closed");
        return listBox;
    }

}
