package com.witaszakjola.partyplanner.ui.views.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;
import com.witaszakjola.partyplanner.backend.domain.EventDto;
import com.witaszakjola.partyplanner.backend.service.PartyService;

import java.time.LocalDate;

public class PartyForm extends FormLayout{

        private final PartyService partyService;
        private EventDto eventDto;

        TextField id = new TextField("Party id");
        TextField name = new TextField("Name");
        DatePicker startDate = new DatePicker("Start date");
        TimePicker startTime = new TimePicker("Start time");
        DatePicker endDate = new DatePicker("End date");
        TextArea description = new TextArea("Description");


        Button saveButton = new Button("Save");
        Button updateButton = new Button(("Update"));
        Button deleteButton = new Button("Delete");
        Button closeButton = new Button("Close");

        Binder<EventDto> binder = new Binder<>(EventDto.class);


        public PartyForm(PartyService partyService) {
            this.partyService = partyService;

            addClassName("party-form");

            id.setClearButtonVisible(true);
            id.setMaxLength(25);
            name.setClearButtonVisible(true);
            name.setValueChangeMode(ValueChangeMode.EAGER);
            name.setAutofocus(true);
            startDate.setInitialPosition(LocalDate.now());
            startTime.setAutoOpen(true);
            endDate.setInitialPosition(LocalDate.now());
            description.setClearButtonVisible(true);

            initBinder();

            add(
                    id,
                    name,
                    startDate,
                    startTime,
                    endDate,
                    description,
                    createButtonsLayout());
        }

        private void initBinder() {
            // partyId
            binder.forField(id)
                    .withNullRepresentation("")
                    .withConverter(new StringToLongConverter("Insert correct id"))
                    .bind(EventDto::getId, EventDto::setId);

            // name
            binder.forField(name)
                    .asRequired("Is required")
                    .withNullRepresentation("")
                    .withValidator(name -> name.length() > 1, "The name must contains at least 2 characters")
                    .bind(EventDto::getName, EventDto::setName);

            // startDate
            binder.forField(startDate)
                    .bind(EventDto::getStartDate, EventDto::setStartDate);

            // startTime
            binder.forField(startTime)
                    .bind(EventDto::getStartTime, EventDto::setStartTime);

            // endDate
            binder.forField(endDate)
                    .bind(EventDto::getEndDate, EventDto::setEndDate);

            // description
            binder.forField(description)
                    .withNullRepresentation("")
                    .bind(EventDto::getDescription, EventDto::setDescription);
        }

        private Component createButtonsLayout() {
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            updateButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
            closeButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            deleteButton.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);

            saveButton.addClickShortcut(Key.ENTER);
            updateButton.addClickShortcut(Key.BACKSPACE);
            closeButton.addClickShortcut(Key.ESCAPE);
            deleteButton.addClickShortcut(Key.DELETE);

            saveButton.addClickListener(event -> validateAndSave());
            updateButton.addClickListener(event -> validateAndUpdate());
            deleteButton.addClickListener(event -> fireEvent(new PartyForm.DeleteEvent(this, eventDto)));
            closeButton.addClickListener(event -> fireEvent(new PartyForm.CloseEvent(this)));


            binder.addStatusChangeListener(e -> saveButton.setEnabled(binder.isValid()));
            return new HorizontalLayout(saveButton, updateButton, deleteButton, closeButton);
        }

        private void validateAndUpdate() {
            try {
                binder.writeBean(eventDto);
                fireEvent(new PartyForm.UpdateEvent(this, eventDto));
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        }

        private void validateAndSave() {
            try {
                binder.writeBean(eventDto);
                fireEvent(new PartyForm.SaveEvent(this, eventDto));
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        }

        public void setEventDto(EventDto eventDto) {
            this.eventDto = eventDto;
            binder.readBean(eventDto);
        }

        // Events
        public static abstract class PartyFormEvent extends ComponentEvent<PartyForm> {
            private EventDto eventDto;

            protected PartyFormEvent(PartyForm source, EventDto eventDto) {
                super(source, false);
                this.eventDto = eventDto;
            }

            public EventDto getEventDto() {
                return eventDto;
            }
        }

        public static class SaveEvent extends PartyFormEvent {
            SaveEvent(PartyForm source, EventDto eventDto) {
                super(source, eventDto);
            }
        }

        public static class UpdateEvent extends PartyForm.PartyFormEvent {
            UpdateEvent(PartyForm source, EventDto eventDto) {super(source, eventDto);}
        }

        public static class DeleteEvent extends PartyForm.PartyFormEvent {
            DeleteEvent(PartyForm source, EventDto eventDto) {
                super(source, eventDto);
            }

        }

        public static class CloseEvent extends PartyForm.PartyFormEvent {
            CloseEvent(PartyForm source) {
                super(source, null);
            }
        }

        public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                      ComponentEventListener<T> listener) {
            return getEventBus().addListener(eventType, listener);
        }
}
