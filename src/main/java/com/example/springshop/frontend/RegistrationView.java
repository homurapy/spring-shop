package com.example.springshop.frontend;

import com.example.springshop.config.security.CustomUserDetailsService;
import com.example.springshop.model.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

import java.util.Optional;
import java.util.stream.Collectors;


@Route("registration")
@PageTitle("registration")

public class RegistrationView extends VerticalLayout {
    private final CustomUserDetailsService service;
    Binder<User> binder = new Binder<>();
    User userBinder = new User();

    public RegistrationView(CustomUserDetailsService service) {
        this.service = service;
        FormLayout layoutWithFormItems = new FormLayout();

        TextField name = new TextField();
        name.setValueChangeMode(ValueChangeMode.EAGER);

        TextField lastName = new TextField();
        lastName.setValueChangeMode(ValueChangeMode.EAGER);

        TextField phone = new TextField();
        phone.setValueChangeMode(ValueChangeMode.EAGER);

        EmailField email = new EmailField();
        email.setValueChangeMode(ValueChangeMode.EAGER);

        TextField login = new TextField();
        login.setValueChangeMode(ValueChangeMode.EAGER);

        PasswordField password = new PasswordField();
        password.setValueChangeMode(ValueChangeMode.EAGER);

        layoutWithFormItems.addFormItem(name, "First name");
        layoutWithFormItems.addFormItem(lastName, "Last name");
        layoutWithFormItems.addFormItem(email, "E-mail");
        layoutWithFormItems.addFormItem(phone, "phone");
        layoutWithFormItems.addFormItem(login, "login");
        layoutWithFormItems.addFormItem(password, "password");
        Label infoLabel = new Label();
        var save = new Button("Registration");
        var reset = new Button("Reset");
        var toLoginForm = new Button("Back to login form", item -> {
            UI.getCurrent().navigate("login");
        });


        // Button bar
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, reset,toLoginForm);
        save.getStyle().set("marginRight", "10px");

// Both phone and email cannot be empty
        SerializablePredicate<String> phoneOrEmailPredicate = value -> !phone
                .getValue().trim().isEmpty()
                || !email.getValue().trim().isEmpty();

// E-mail and phone have specific validators
        Binder.Binding<User, String> emailBinding = binder.forField(email)
                .withNullRepresentation("")
                .withValidator(phoneOrEmailPredicate,
                        "Please specify your email")
                .withValidator(new EmailValidator("Incorrect email address"))
                .bind(User::getEmail, User::setEmail);

        Binder.Binding<User, String> phoneBinding = binder.forField(phone)
                .withValidator(phoneOrEmailPredicate,
                        "Please specify your phone")
                .bind(User::getPhone, User::setPhone);

// Trigger cross-field validation when the other field is changed
        email.addValueChangeListener(event -> phoneBinding.validate());
        phone.addValueChangeListener(event -> emailBinding.validate());

// Name is a required field
        name.setRequiredIndicatorVisible(true);
        binder.forField(name)
                .withValidator(new StringLengthValidator(
                        "Please add the name", 1, null))
                .bind(User::getName, User::setName);

        // Last name is a required field
        lastName.setRequiredIndicatorVisible(true);
        binder.forField(lastName)
                .withValidator(new StringLengthValidator(
                        "Please add the last name", 1, null))
                .bind(User::getLastName, User::setLastName);

        // Login is a required field
        login.setRequiredIndicatorVisible(true);
        binder.forField(login)
                .withValidator(new StringLengthValidator(
                        "Please add login", 1, null))
                .bind(User::getLogin, User::setLogin);

        // Password  is a required field
        password.setRequiredIndicatorVisible(true);
        binder.forField(password)
                .withValidator(new StringLengthValidator(
                        "Please add password", 1, null))
                .bind(User::getPassword, User::setPassword);

// Click listeners for the buttons
        save.addClickListener(event -> {
            if (binder.writeBeanIfValid(userBinder)) {
                if (service.saveUser(userBinder) == true) {
                    infoLabel.setText("Registration complied");
                    toLoginForm.setVisible(true);
                }else {
                    infoLabel.setText("This login is exist, please enter new login");
                    login.clear();
                }

            } else {
                BinderValidationStatus<User> validate = binder.validate();
                String errorText = validate.getFieldValidationStatuses()
                        .stream().filter(BindingValidationStatus::isError)
                        .map(BindingValidationStatus::getMessage)
                        .map(Optional::get).distinct()
                        .collect(Collectors.joining(", "));
                infoLabel.setText("There are errors: " + errorText);
            }
        });
        reset.addClickListener(event -> {
            // clear fields by setting null
            binder.readBean(null);
            infoLabel.setText("");
        });
        add(new H1("Creat account"),layoutWithFormItems, actions, infoLabel);
    }
}


