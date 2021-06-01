package com.witaszakjola.partyplanner.ui.views.map;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.witaszakjola.partyplanner.ui.MainLayout;

@PageTitle("Map | Party Fun & Spontan Planner")
@Route(value = "map", layout = MainLayout.class)
public class MapView extends VerticalLayout {
    public MapView() {
    }
}
