package com.witaszakjola.partyplanner.ui.views.users;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;
import com.witaszakjola.partyplanner.backend.domain.Mail;
import com.witaszakjola.partyplanner.backend.service.MailService;

public class MailForm extends FormLayout {

    private MailService mailService;
    private Mail mail;

    EmailField mailTo = new EmailField("Mail to...");
    TextField subject = new TextField("Subject");
    TextArea message = new TextArea("Message") ;

    Button sendButton = new Button("Send");

    Binder<Mail> binder;


    public MailForm(MailService mailService) {
        this.mailService = mailService;
        setClassName("email-form");

        mailTo.setClearButtonVisible(true);

        subject.setClearButtonVisible(true);
        subject.setValueChangeMode(ValueChangeMode.EAGER);

        message.setClearButtonVisible(true);
        message.setValueChangeMode(ValueChangeMode.EAGER);

        binder = new Binder<>(Mail.class);
        binder.bindInstanceFields(this);
        binder.setBean(mail);

        sendButton.addClickListener(event -> validateAndSend());

        add(
                mailTo,
                message,
                subject,
                sendButton);
    }

    private void validateAndSend() {
        try {
            binder.writeBean(mail);
            fireEvent(new SendEvent(this, mail));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setMail(Mail mail) {
        this.mail = mail;
        binder.readBean(mail);
    }

    // Event
    public static abstract class MailFormEvent extends ComponentEvent<MailForm> {
        private final Mail mail;

        protected MailFormEvent(MailForm source, Mail mail) {
            super(source, false);
            this.mail = mail;
        }

        public Mail getMail() {
            return mail;
        }
    }

    public static class SendEvent extends MailFormEvent {
        SendEvent(MailForm source, Mail mail) {
            super(source, mail);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
