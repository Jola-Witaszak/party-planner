package com.witaszakjola.partyplanner.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.witaszakjola.partyplanner.ui.views.events.PartyView;
import com.witaszakjola.partyplanner.ui.views.home.LinkView;
import com.witaszakjola.partyplanner.ui.views.map.MapView;
import com.witaszakjola.partyplanner.ui.views.planner.PlannerView;
import com.witaszakjola.partyplanner.ui.views.users.ListView;

@CssImport("./styles/shared-styles.css")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Party Fun & Spontan");
        logo.addClassName("logo");

        Avatar appAvatar = new Avatar("Party");
        StreamResource imageResource = new StreamResource(
                "very-happy",
                () -> getClass().getResourceAsStream("/static/very-happy.png"));
        appAvatar.setImageResource(imageResource);

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, appAvatar);
        header.addClassName("header");
        header.setWidth("100%");
        header.setSizeFull();
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.START);

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink usersLink = new RouterLink("Users", ListView.class);
        usersLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink eventsLink = new RouterLink("Party", PartyView.class);
        eventsLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink mapLink = new RouterLink("Map & Weather Forecast", MapView.class);
        mapLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink plannerLink = new RouterLink("Planner", PlannerView.class);
        mapLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink homeLink = new RouterLink("Link", LinkView.class);
        homeLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(homeLink, eventsLink, usersLink, mapLink, plannerLink));
    }
}
