package com.witaszakjola.partyplanner.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.witaszakjola.partyplanner.ui.views.events.EventsView;
import com.witaszakjola.partyplanner.ui.views.map.MapView;
import com.witaszakjola.partyplanner.ui.views.users.ListView;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {
    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Party Fun & Spontan Planner");
        logo.addClassName("logo");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.addClassName("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("Users", ListView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink eventsLink = new RouterLink("Party", EventsView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink mapLink = new RouterLink("Map & Weather Forecast", MapView.class);
        mapLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(listLink, eventsLink, mapLink));
    }
}
