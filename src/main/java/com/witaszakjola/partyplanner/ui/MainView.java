package com.witaszakjola.partyplanner.ui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.witaszakjola.partyplanner.backend.domain.UserDto;
import com.witaszakjola.partyplanner.backend.service.UserService;

@Route("")
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {

    private UserService userService;
    private UserForm form;

    Grid<UserDto> grid = new Grid<>(UserDto.class);
    TextField filterText = new TextField();

    public MainView(UserService userService) {
        this.userService = userService;

        addClassName("list-view");
        setSizeFull();
        configureFilter();
        configureGrid();

        form = new UserForm(userService);
        form.addListener(UserForm.SaveEvent.class, this::saveUserDto);
        form.addListener(UserForm.DeleteEvent.class, this::deleteUserDto);
        form.addListener(UserForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.setClassName("content");
        content.setSizeFull();

        add(filterText, content);

        updateList();
        closeEditor();
    }

    private void saveUserDto(UserForm.SaveEvent event) {
        userService.save(event.getUserDto());
        updateList();
        closeEditor();
    }

    private void deleteUserDto(UserForm.DeleteEvent event) {
        userService.removeUser(event.getUserDto());
        updateList();
        closeEditor();
    }

    private void updateList() {
        grid.setItems(userService.findAll());
    }

    private void configureFilter() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateFilteredList());
    }

    private void updateFilteredList() {
        grid.setItems(userService.findAll(filterText.getValue()));
    }

    private void configureGrid() {
        grid.addClassName("users-grid");
        grid.setSizeFull();
        grid.setColumns("id", "username", "email", "phone", "attending_party");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editUserDto(event.getValue()));
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

    private void closeEditor() {
        form.setUserDto(null);
        form.setVisible(false);
        removeClassName("editing");
    }
}
