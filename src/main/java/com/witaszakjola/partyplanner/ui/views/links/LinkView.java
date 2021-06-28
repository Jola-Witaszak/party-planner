package com.witaszakjola.partyplanner.ui.views.links;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.witaszakjola.partyplanner.ui.MainLayout;

@Route(value = "home", layout = MainLayout.class)
@PageTitle("Home | Party Fun & Spontan Planner")
public class LinkView extends VerticalLayout {

    Anchor trivago = new Anchor("https://trivago.pl", "Go to trivago.pl");
    Anchor pyszne = new Anchor("https://pyszne.pl", "Go to pyszne.pl");
    Anchor windy = new Anchor("https://windy.com", "Go to windy.com");
    Anchor parking = new Anchor("https://park4night.com", "Parking");
    Anchor skyScanner = new Anchor("https://skyscanner.pl", "Skyscanner");

    public LinkView() {
        add(trivago, skyScanner, pyszne, windy, parking);
    }
}
