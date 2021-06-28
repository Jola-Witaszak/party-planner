package com.witaszakjola.partyplanner.ui.views.events;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.witaszakjola.partyplanner.backend.domain.EventDto;
import com.witaszakjola.partyplanner.backend.service.PartyService;
import com.witaszakjola.partyplanner.ui.MainLayout;

@PageTitle("Party | Party Fun & Spontan Planner")
@Route(value = "party", layout = MainLayout.class)
public class PartyView extends VerticalLayout {

        private PartyService partyService;
        private PartyForm form;

        Grid<EventDto> grid = new Grid<>(EventDto.class);
        TextField filterText = new TextField();

        public PartyView(PartyService partyService) {
            this.partyService = partyService;

            addClassName("party-view");
            setSizeFull();
            configureGrid();

            form = new PartyForm(partyService);
            form.addListener(PartyForm.SaveEvent.class, this::savePartyDto);
            form.addListener(PartyForm.UpdateEvent.class, this::updatePartyDto);
            form.addListener(PartyForm.DeleteEvent.class, this::deletePartyDto);
            form.addListener(PartyForm.CloseEvent.class, e -> closeEditor());

            Div content = new Div(grid, form);
            content.setClassName("content");
            content.setSizeFull();

            add(getToolBar(), content);

            updateList();
            closeEditor();
        }

        private void savePartyDto(PartyForm.SaveEvent event) {
            partyService.save(event.getEventDto());
            updateList();
            closeEditor();
        }

        private void updatePartyDto(PartyForm.UpdateEvent event) {
            partyService.update(event.getEventDto());
            updateList();
            closeEditor();
        }

        private void deletePartyDto(PartyForm.DeleteEvent event) {
            partyService.removeUser(event.getEventDto());
            updateList();
            closeEditor();
        }

        private void addNewParty() {
            grid.asSingleSelect().clear();
            editEventDto(new EventDto());
        }

        public void editEventDto(EventDto eventDto) {
            if (eventDto == null) {
                closeEditor();
            } else {
                form.setEventDto(eventDto);
                form.setVisible(true);
                addClassName("editing");
            }
        }

        private void closeEditor() {
            form.setEventDto(null);
            form.setVisible(false);
            removeClassName("editing");
        }

        private void updateList() {
            grid.setItems(partyService.findAll());
        }

        private void updateFilteredList() {
            grid.setItems(partyService.findAll(filterText.getValue()));
        }

        private HorizontalLayout getToolBar() {
            filterText.setPlaceholder("Filter by name...");
            filterText.setClearButtonVisible(true);
            filterText.setValueChangeMode(ValueChangeMode.LAZY);
            filterText.addValueChangeListener(e -> updateFilteredList());

            Button addNewPartyButton = new Button("Create new Party!");
            addNewPartyButton.setVisible(true);
            addNewPartyButton.addClickListener(click -> {
                addNewParty();
            });

            HorizontalLayout toolbar = new HorizontalLayout(filterText, addNewPartyButton);
            toolbar.addClassName("toolbar");
            return toolbar;
        }

        private void configureGrid() {
            grid.addClassName("party-grid");
            grid.setSizeFull();
            grid.setColumns("id", "name", "startDate", "startTime", "endDate", "description");
            grid.getColumns().forEach(col -> col.setAutoWidth(true));

            grid.asSingleSelect().addValueChangeListener(event -> editEventDto(event.getValue()));
        }
}
