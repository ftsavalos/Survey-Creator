package com.ui.views;

import com.models.User;
import com.services.LoginService;
import com.services.UserService;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = LoginView.VIEW_NAME)
public class LoginView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "login";

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @PostConstruct
    void init() {
        setMargin(true);
        setSpacing(true);
        loginForm();
    }

    private void loginForm() {
        HorizontalLayout form = new HorizontalLayout();

        Label signInHeader = new Label("Sign In");
        signInHeader.setStyleName(ValoTheme.LABEL_H2);
        addComponent(signInHeader);
        addComponents(form);

        TextField username = new TextField("Username or email");
        PasswordField password = new PasswordField("Password");

        Button loginButton = new Button("Login");
        loginButton.setStyleName(ValoTheme.BUTTON_PRIMARY);

        loginButton.addClickListener(event -> {
            String usernameOrEmail = username.getValue();

            User loginFormUser = new User();
            loginFormUser.setPassword(password.getValue());

            if (usernameOrEmail.contains("@")) {
                loginFormUser.setEmail(usernameOrEmail);
            } else {
                loginFormUser.setUsername(usernameOrEmail);
            }

            User validationUser = this.loginService.checkUserExists(loginFormUser);

            if (validationUser != null) {
                getUI().getNavigator().navigateTo(HomeView.VIEW_NAME);
            }
        });

        form.addComponents(username, password);
        addComponent(loginButton);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // the view is constructed in the init() method()
    }
}
