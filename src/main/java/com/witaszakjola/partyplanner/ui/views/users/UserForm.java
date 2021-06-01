package com.witaszakjola.partyplanner.ui.views.users;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToBooleanConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;
import com.witaszakjola.partyplanner.backend.domain.UserDto;
import com.witaszakjola.partyplanner.backend.service.UserService;

public class UserForm extends FormLayout {

    private final UserService userService;
    private UserDto userDto;

    TextField id = new TextField("User id");
    TextField username = new TextField("Name");
    EmailField email = new EmailField("Email");
    TextField phone = new TextField("Phone");
    TextField attending_party = new TextField("Attending Party?");

    Button saveButton = new Button("Save");
    Button updateButton = new Button(("Update"));
    Button deleteButton = new Button("Delete");
    Button closeButton = new Button("Close");

    Binder<UserDto> binder = new Binder<>(UserDto.class);


    public UserForm(UserService userService) {
        this.userService = userService;

        addClassName("user-form");

        id.setClearButtonVisible(true);

        username.setClearButtonVisible(true);
        username.setPlaceholder("first name & last name");
        username.setValueChangeMode(ValueChangeMode.EAGER);

        email.setClearButtonVisible(true);
        email.setValueChangeMode(ValueChangeMode.EAGER);

        phone.setClearButtonVisible(true);
        phone.setValueChangeMode(ValueChangeMode.EAGER);

        attending_party.setClearButtonVisible(true);
        attending_party.setPlaceholder("true, false or null");

        initBinder();

        add(
                id,
                username,
                email,
                phone,
                attending_party,
                createButtonsLayout());
    }

    private void initBinder() {
        // userId
        binder.forField(id).withConverter(
                new StringToLongConverter("Insert correct id")
        ).bind(UserDto::getId, UserDto::setId);

        // name
        binder.forField(username).withValidator(firstName -> firstName.length() > 1,
                "The name must contains at least 2 characters").asRequired()
                .bind(UserDto::getUsername, UserDto::setUsername);

        // email
        binder.forField(email).withValidator(
                new EmailValidator("This doesn't look like a valid email address")
        ).bind(UserDto::getEmail, UserDto::setEmail);

        // phone
        binder.forField(phone).withConverter(
                new StringToIntegerConverter("Not a number")
        ).bind(UserDto::getPhone, UserDto::setPhone);

        // attending_party
        binder.forField(attending_party).withConverter(
                new StringToBooleanConverter("It must be true or false")
        ).bind(UserDto::getAttending_party, UserDto::setAttending_party);

        //null representation for textFields: id, username, email, phone, attending_party
        binder.forField(id).withNullRepresentation("").withConverter(
                new StringToLongConverter("Insert correct id")).bind(UserDto::getId, UserDto::setId);

        binder.forField(username).withNullRepresentation("").bind(UserDto::getUsername, UserDto::setUsername);

        binder.forField(email).withNullRepresentation("").bind(UserDto::getEmail, UserDto::setEmail);

        binder.forField(phone).withNullRepresentation("").withConverter(
                new StringToIntegerConverter("Not a number")).bind(UserDto::getPhone, UserDto::setPhone);

        binder.forField(attending_party).withNullRepresentation("").withConverter(
                new StringToBooleanConverter("It must be true or false"))
                .bind(UserDto::getAttending_party, UserDto::setAttending_party);
    }

    private Component createButtonsLayout() {
        saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        updateButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        saveButton.addClickShortcut(Key.ENTER);
        updateButton.addClickShortcut(Key.BACKSPACE);
        closeButton.addClickShortcut(Key.ESCAPE);
        deleteButton.addClickShortcut(Key.DELETE);

        saveButton.addClickListener(event -> validateAndSave());
        updateButton.addClickListener(event -> validateAndSave());
        deleteButton.addClickListener(event -> fireEvent(new DeleteEvent(this, userDto)));
        closeButton.addClickListener(event -> fireEvent(new CloseEvent(this)));


        binder.addStatusChangeListener(e -> saveButton.setEnabled(binder.isValid()));
        return new HorizontalLayout(saveButton, updateButton, deleteButton, closeButton);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(userDto);
            fireEvent(new SaveEvent(this, userDto));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
        binder.readBean(userDto);
    }

    // Events
    public static abstract class UserFormEvent extends ComponentEvent<UserForm> {
        private UserDto userDto;

        protected UserFormEvent(UserForm source, UserDto userDto) {
            super(source, false);
            this.userDto = userDto;
        }

        public UserDto getUserDto() {
            return userDto;
        }
    }

    public static class SaveEvent extends UserFormEvent {
        SaveEvent(UserForm source, UserDto userDto) {
            super(source, userDto);
        }
    }

    public static class UpdateEvent extends UserFormEvent {
        UpdateEvent(UserForm source, UserDto userDto) {super(source, userDto);}
    }

    public static class DeleteEvent extends UserFormEvent {
        DeleteEvent(UserForm source, UserDto userDto) {
            super(source, userDto);
        }

    }

    public static class CloseEvent extends UserFormEvent {
        CloseEvent(UserForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
