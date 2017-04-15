package com.ui.views;

import com.models.User;
import com.services.LoginService;
import com.services.UserService;
import com.vaadin.data.Binder;
import com.vaadin.data.Binder.Binding;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringView(name = RegisterView.VIEW_NAME)
public class RegisterView extends VerticalLayout implements View {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterView.class);
    public static final String VIEW_NAME = "register";

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @PostConstruct
    void init() {
        setMargin(true);
        setSpacing(true);

        registerForm();
    }

    private void registerForm() {
        HorizontalLayout form = new HorizontalLayout();
        addComponents(form);

        TextField firstName = new TextField("First Name");
        TextField lastName = new TextField("Last Name");
        TextField email = new TextField("e-mail");
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        Button register = new Button("Sign-up");
        register.setStyleName(ValoTheme.BUTTON_PRIMARY);

        firstName.setPlaceholder("first name");
        lastName.setPlaceholder("last name");
        email.setPlaceholder("e-mail");
        username.setPlaceholder("Username");
        password.setPlaceholder("Password");

        Map<TextField, Integer> textFieldValidationMap = new HashMap<>();
        textFieldValidationMap.put(username,4);
        textFieldValidationMap.put(password,4);

        Map<TextField, Binding<User, String>> map = this.validateRegisterUserFields(textFieldValidationMap);



        register.addClickListener(a -> {

    //            BindingValidationStatus usernameValidator = validateFieldLength(username, 4).validate();
    //            if (usernameValidator.isError()) {
    //                Notification.show("You have some Errors");
    //                LOGGER.info("Error in UsernameField Register Form: " + usernameValidator.getMessage().get());
    //                return;
    //            }

            final int[] userFieldsErrorNum = {0};
            map.forEach((textField,binding)->{
                BindingValidationStatus currentUserFieldValidator = binding.validate();

                if (currentUserFieldValidator.isError()) {
                    Notification.show(textField.getValue() + " is Not accepted for " + textField.getCaption());

//                    Notification error = new Notification(textField.getValue() + " is Not accepted for " + textField.getCaption(),null, Notification.Type.ERROR_MESSAGE,true);
//                    error.setPosition(Position.ASSISTIVE);
//                    Notification.show
                    LOGGER.info("Error in: " + textField.getCaption()+" - " + currentUserFieldValidator.getMessage().get());
                    userFieldsErrorNum[0]++;
                }
            });
            if(userFieldsErrorNum[0]>0)
                return;

            User registerFormUser = new User(username.getValue(), password.getValue(), email.getValue());
            User validationUserByUsername = this.userService.getUser(registerFormUser.getUsername());
            User validationUserByEmail = this.userService.getUserByEmail(registerFormUser.getEmail());

            if (validationUserByEmail != null) {
                Notification.show("This e-mail is already registered");
            } else if (validationUserByUsername != null) {
                Notification.show("This Username is taken");
            } else {
                registerFormUser.setFirstName(firstName.getValue());
                registerFormUser.setLastName(lastName.getValue());
                this.userService.addUser(registerFormUser);
                getUI().getNavigator().navigateTo(HomeView.VIEW_NAME);
            }
        });

        addComponents(firstName, lastName, email, username, password, register);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    private Binding<User, String> validateFieldLength(TextField field,int minLength ){
        return new Binder<User>().forField(field)
                .withValidator(str -> str.length() >= minLength, "at least " +minLength+" characters")
                .bind(User::getUsername,User::setUsername);
    }

    private Map<TextField, Binding<User,String>> validateRegisterUserFields(Map<TextField,Integer> validators){
        Map<TextField,Binding<User,String>> validatorsResult = new HashMap<>();

        validators.forEach((a,b)-> validatorsResult.put(a, validateFieldLength(a,b)));

        return validatorsResult;
    }
}