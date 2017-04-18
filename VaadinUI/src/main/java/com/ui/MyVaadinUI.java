package com.ui;

import com.ui.views.HomeView;
import com.ui.views.LoginView;
import com.ui.views.RegisterView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.components.grid.Footer;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

@Theme("valo")
@SpringUI
public class MyVaadinUI extends UI {

    final VerticalLayout root = new VerticalLayout();

    // we can use either constructor autowiring or field autowiring
    @Autowired
    private SpringViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest request) {
        setBasicLayout();
        createHeaderLayout();
        createMenuBarLayout();
        createContainerLayout();
    }

    private void setBasicLayout() {
        root.setSizeFull();
        root.addStyleName("mystyle");
        root.setMargin(true);
        root.setSpacing(true);
        setContent(root);
    }

    private void createHeaderLayout() {
        HorizontalLayout headerLayout = new HorizontalLayout();
        Label header = new Label("Survey Creator");
        Button userButton = new Button();
        userButton.setIcon(new ThemeResource("src/main/resources/images/user.png"));
        header.setStyleName(ValoTheme.LABEL_H1);

        root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        root.addComponent(headerLayout);
        headerLayout.addComponents(header,userButton);
    }

    private void createMenuBarLayout() {
        MenuBar menuBar = new MenuBar();
        VerticalLayout menuBarAllignment = new VerticalLayout();
        menuBarAllignment.setSpacing(false);
        menuBarAllignment.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        MenuBar.MenuItem home = menuBar.addItem("Home", navigateToSpecficPage(HomeView.VIEW_NAME));
        MenuBar.MenuItem login = menuBar.addItem("Login", navigateToSpecficPage(LoginView.VIEW_NAME));
        MenuBar.MenuItem register = menuBar.addItem("Register", navigateToSpecficPage(RegisterView.VIEW_NAME));
        MenuBar.MenuItem contact = menuBar.addItem("Contact", null);

        menuBarAllignment.addComponent(menuBar);
        root.addComponent(menuBarAllignment);

    }

    private void createContainerLayout() {
        final Panel viewContainer = new Panel();
        viewContainer.setSizeFull();
        root.addComponent(viewContainer);
        root.setExpandRatio(viewContainer, 1.0f);

        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addProvider(viewProvider);
    }

    private Command navigateToSpecficPage(String viewName) {
        return a -> getNavigator().navigateTo(viewName);
    }
}