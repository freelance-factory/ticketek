package org.jboss.errai.demo.client.local;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.inject.Inject;

@Page
@Templated("TablePage.html#app-template")
public class TablePage extends Composite{

    @Inject
    @DataField
    private Button addNew;

    @Inject
    private TransitionTo<FormPage> addNewButtonClicked;

    @EventHandler("addNew")
    private void onSubmit(ClickEvent e){
        addNewButtonClicked.go();
    }
}
