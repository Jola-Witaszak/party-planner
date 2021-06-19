package com.witaszakjola.partyplanner.ui.views.map;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.witaszakjola.partyplanner.backend.service.MapService;
import com.witaszakjola.partyplanner.ui.MainLayout;

@PageTitle("Map | Party Fun & Spontan Planner")
@Route(value = "map", layout = MainLayout.class)
public class MapView extends VerticalLayout {

    private final MapService mapService;

    Label inProgress = new Label("in progress...");


    public MapView(MapService mapService) {
        this.mapService = mapService;
        addClassName("map-view");
        setSizeFull();
        Div div = new Div();
        div.setSizeFull();
        div.addClassName("map");

        download();

        inProgress.setSizeFull();
        inProgress.setVisible(true);
        add(inProgress);
    }

    private void download() {
        mapService.downloadMap();
    }
}
