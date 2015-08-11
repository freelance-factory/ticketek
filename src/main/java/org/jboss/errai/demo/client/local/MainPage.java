package org.jboss.errai.demo.client.local;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.inject.Inject;

@Page(role = DefaultPage.class)
@Templated("MainPage.html#app-template")
public class MainPage  extends Composite {

    @Inject
    @DataField
    private Button buttonTable;

    @Inject
    @DataField
    private Button buttonForm;

    @Inject
    private TransitionTo<TablePage> buttonTableButtonClicked;

    @Inject
    private TransitionTo<FormPage> buttonFormButtonClicked;

    @EventHandler("buttonTable")
    private void onButtonTable(ClickEvent e){
        buttonTableButtonClicked.go();
    }

    @EventHandler("buttonForm")
    private void onButtonForm(ClickEvent e){
        buttonFormButtonClicked.go();
    }
}
