package org.jboss.errai.demo.client.local;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.*;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.demo.client.shared.model.UserComplaint;
import org.jboss.errai.demo.client.shared.service.TestService;
import org.jboss.errai.ui.client.widget.ValueImage;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Model;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.Window;
import com.googlecode.gwtphonegap.client.camera.Camera;
import com.googlecode.gwtphonegap.client.camera.PictureCallback;
import com.googlecode.gwtphonegap.client.camera.PictureOptions;

import java.util.ArrayList;

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
    @Inject
    @Model
    private UserComplaint userComplaint;

    @Inject
    @Bound
    @DataField
    private TextBox name;

    @Inject
    @Bound
    @DataField
    private TextBox email;

    @Inject
    @Bound
    @DataField
    private TextArea text;

    @Inject
    @Bound
    @DataField
    private ValueImage image;

    @Inject
    @DataField
    private FlexTable table = new FlexTable();

    @Inject
    @DataField
    private Button submit;


    @Inject
    private Caller<TestService> testService;

    private int i = 1;

    /**
     * Errai's Cordova integration module makes native device hardware injectable
     * into your code.
     */
    @Inject
    private Camera camera;

    @Inject
    @DataField
    private Button takePicture;

    /**
     * This method is registered as an event handler for click events on the
     * submit button of the complaint form.
     *
     * @param e the click event.
     */
    @EventHandler("submit")
    private void onSubmit(ClickEvent e) {
        // Execute the REST call to store the complaint on the server
        testService.call(new RemoteCallback<ArrayList<String>>() {
            @Override
            public void callback(ArrayList<String> response) {
                table.setWidget(0,0,new Label ("Name"));
                table.setWidget(0,1,new Label ("Email"));
                table.setWidget(0,2,new Label ("Complaint"));
                table.setWidget(i, 0, new Label(response.get(0)));
                table.setWidget(i, 1, new Label(response.get(1)));
                table.setWidget(i, 2, new Label(response.get(2)));
                i++;
            }
        }).test(name.getText(),email.getText(),text.getText());
    }

    /**
     * This method is registered as an event handler for click events on the
     * takePicture button of the complaint form. When running on devices that have
     * camera support, it will cause the camera app to open and allow you to take
     * a picture.
     *
     * @param e the click event.
     */
    @EventHandler("takePicture")
    private void onTakePictureClick(ClickEvent e) {
        PictureOptions options = new PictureOptions(25);
        options.setDestinationType(PictureOptions.DESTINATION_TYPE_DATA_URL);
        options.setSourceType(PictureOptions.PICTURE_SOURCE_TYPE_CAMERA);

        camera.getPicture(options, new PictureCallback() {

            @Override
            public void onSuccess(String data) {
                // On success, we will store the image as a data URL
                // (https://en.wikipedia.org/wiki/Data_URI_scheme) in our JPA entity.
                image.setVisible(true);
                image.setValue("data:image/jpeg;base64," + data, true);
            }

            @Override
            public void onFailure(String error) {
                Window.alert("Could not take picture: " + error);
            }
        });
    }
}