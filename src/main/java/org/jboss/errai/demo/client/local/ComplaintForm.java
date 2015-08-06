package org.jboss.errai.demo.client.local;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.demo.client.shared.model.UserComplaint;
import org.jboss.errai.demo.client.shared.service.UserComplaintService;
import org.jboss.errai.enterprise.client.jaxrs.api.RestClient;
import org.jboss.errai.ui.client.widget.ValueImage;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

/**
 * This is the companion Java class of the complaint form as specified by
 * {@link Templated}. It refers to a data field called "app-template" in
 * ComplaintForm.html as its root and gains access to all data fields in the
 * template to add dynamic behavior (e.g. event handlers, data bindings, page
 * transitions).
 * <p/>
 * The {@link Page} annotation declares this form as a bookmarkable page that
 * can be transitioned to by other pages of this application. Further the
 * specified role (DefaultPage.class) make this page appear by default when the
 * application is started.
 */
@Page(role = DefaultPage.class)
@Templated("ComplaintForm.html#app-template")
public class ComplaintForm extends Composite {

    /**
     * Errai's data binding module will automatically bind the injected instance
     * of {@link UserComplaint} to all fields annotated with {@link Bound}. If not
     * specified otherwise, the bindings occur based on matching field names (e.g.
     * userComplaint.name will automatically be kept in sync with the data-field
     * "name")
     */
    private UserComplaint userComplaint;

    @Inject
    @DataField
    private TextBox name;

    @Inject
    @DataField
    private TextBox email;

    @Inject
    @DataField
    private TextArea text;

    @Inject
    @DataField
    private ValueImage image;

    @Inject
    @DataField
    private FlexTable table = new FlexTable();

    @Inject
    @DataField
    private Button submit;

    @Inject
    private Caller<UserComplaintService> testService;

    @PostConstruct
    public void init() {
        // RestClient.setApplicationRoot("http://10.15.16.207:8080/errai-tutorial/rest");
        RestClient.setApplicationRoot("rest");
        clear();
    }

    /**
     * This method is registered as an event handler for click events on the
     * submit button of the complaint form.
     *
     * @param e the click event.
     */

    @EventHandler("submit")
    private void onSubmit(ClickEvent e) {
        boolean isNew = userComplaint.getId() == null;
        buildUserComplaintFromWidgets();
        if (isNew) {
            save();
            clear();
        } else {
            update();
            clear();
        }
    }

    private void buildUserComplaintFromWidgets() {
        userComplaint.setName(name.getText());
        userComplaint.setText(text.getText());
        userComplaint.setEmail(email.getText());
    }

    private void buildWidgetsFromUserComplaint(UserComplaint complaint) {
        name.setText(complaint.getName());
        email.setText(complaint.getEmail());
        text.setText(complaint.getText());
    }

    private void save() {
        testService.call(new RemoteCallback<Void>() {
            @Override
            public void callback(Void aVoid) {
                buildTable();
            }
        }).save(userComplaint);
    }

    private void update() {
        testService.call(new RemoteCallback<Void>() {
            @Override
            public void callback(Void aVoid) {
                buildTable();
            }
        }).update(userComplaint.getId(), userComplaint);
    }

    private void clear() {
        userComplaint = new UserComplaint();
        name.setText("");
        email.setText("");
        text.setText("");
    }

    private void buildTable() {
        testService.call(new RemoteCallback<List<UserComplaint>>() {
            @Override
            public void callback(List<UserComplaint> userComplaints) {
                setTableHeader();
                int j = 0;
                for (final UserComplaint complaint : userComplaints) {
                    Button edit = new Button("Edit");
                    edit.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent clickEvent) {
                            userComplaint = complaint;
                            buildWidgetsFromUserComplaint(complaint);
                        }
                    });
                    Button delete = new Button("Delete");
                    delete.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent clickEvent) {
                            testService.call().delete(complaint.getId());
                            table.clear();
                            buildTable();
                        }
                    });
                    buildRows(j, complaint, edit, delete);
                    j++;
                }
            }
        }).getTableInfo();
    }

    private void buildRows(int j, UserComplaint complaint, Button edit, Button delete) {
        table.setWidget(j + 1, 0, new Label(complaint.getId().toString()));
        table.setWidget(j+1,1, new Label(complaint.getName()));
        table.setWidget(j+1,2, new Label(complaint.getEmail()));
        table.setWidget(j+1,3, new Label(complaint.getText()));
        table.setWidget(j+1,4, edit);
        table.setWidget(j+1,5, delete);
    }

    private void setTableHeader() {
        table.setWidget(0,0, new Label("Id"));
        table.setWidget(0,1, new Label("Name"));
        table.setWidget(0,2, new Label("Email"));
        table.setWidget(0,3, new Label("Complaint"));
    }
}