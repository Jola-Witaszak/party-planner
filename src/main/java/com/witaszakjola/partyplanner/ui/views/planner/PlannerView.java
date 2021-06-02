package com.witaszakjola.partyplanner.ui.views.planner;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.witaszakjola.partyplanner.ui.MainLayout;
@PageTitle("Planner | Party Fun & Spontan Planner")
@Route(value = "planner", layout = MainLayout.class)
public class PlannerView extends VerticalLayout {

    Label temporary = new Label("in progress...");

    public PlannerView() {
        temporary.setSizeFull();
        add(temporary);
    }
}
