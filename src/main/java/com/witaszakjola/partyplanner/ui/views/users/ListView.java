package com.witaszakjola.partyplanner.ui.views.users;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.witaszakjola.partyplanner.backend.domain.Mail;
import com.witaszakjola.partyplanner.backend.domain.UserDto;
import com.witaszakjola.partyplanner.backend.service.MailService;
import com.witaszakjola.partyplanner.backend.service.UserService;
import com.witaszakjola.partyplanner.ui.MainLayout;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Users | Party Fun & Spontan Planner")
public class ListView extends VerticalLayout {

    private final UserService userService;
    private final MailService mailService;
    private final UserForm form;
    private final MailForm mailForm;

    Grid<UserDto> grid = new Grid<>(UserDto.class);
    TextField filterText = new TextField();

    public ListView(UserService userService, MailService mailService, Mail mail) {
        this.userService = userService;
        this.mailService = mailService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form = new UserForm(userService);
        form.addListener(UserForm.SaveEvent.class, this::saveUserDto);
        form.addListener(UserForm.UpdateEvent.class, this::updateUserDto);
        form.addListener(UserForm.DeleteEvent.class, this::deleteUserDto);
        form.addListener(UserForm.CloseEvent.class, e -> closeEditor());

        mailForm = new MailForm(mailService);
        mailForm.addListener(MailForm.SendEvent.class, this::sendEmail);
        setMail(mail);

        Div content = new Div(grid, form);
        content.setClassName("content");
        content.setSizeFull();

        add(getToolBar(), content, mailForm);

        updateList();
        closeEditor();
    }

    private void saveUserDto(UserForm.SaveEvent event) {
        userService.save(event.getUserDto());
        updateList();
        closeEditor();
    }

    private void updateUserDto(UserForm.UpdateEvent event) {
        userService.update(event.getUserDto());
        updateList();
        closeEditor();
    }

    private void deleteUserDto(UserForm.DeleteEvent event) {
        userService.removeUser(event.getUserDto());
        updateList();
        closeEditor();
    }

    private void addNewUser() {
        grid.asSingleSelect().clear();
        editUserDto(new UserDto());
    }

    public void editUserDto(UserDto userDto) {
        if (userDto == null) {
            closeEditor();
        } else {
            form.setUserDto(userDto);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void setMail(Mail mail) {
        mailForm.setMail(mail);
    }

    private void editEmail(UserDto userDto) {
        mailForm.mailTo.setValue(userDto.getEmail());
    }

    private void closeEditor() {
        form.setUserDto(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private Notification sendEmail(MailForm.SendEvent event) {
        String response =  mailService.sendEmail(event.getMail());
        return Notification.show(response);
    }

    private void updateList() {
        grid.setItems(userService.findAll());
    }

    private void updateFilteredList() {
        grid.setItems(userService.findAll(filterText.getValue()));
    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateFilteredList());

        Button addNewUserButton = new Button("Add new user");
        addNewUserButton.setVisible(true);
        addNewUserButton.addClickListener(click -> {
            addNewUser();
        });

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addNewUserButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void configureGrid() {
        grid.addClassName("users-grid");
        grid.setSizeFull();
        grid.setColumns("id", "username", "email", "phone", "attending_party");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> {
            editUserDto(event.getValue());
            editEmail(event.getValue());
        });
    }
}
