package com.musala.examnoel.views.index;

import com.musala.examnoel.views.main.MainView;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import lombok.extern.slf4j.Slf4j;

@Route(value = "index", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Musala Exam")
@Slf4j
public class IndexView extends Div {

    private VerticalLayout layout;

    public IndexView() {
        addClassName("musala-exam-view");

        Anchor swagger = new Anchor("/swagger-ui.html","API-DOC");

        layout = new VerticalLayout();

        layout.setPadding(true);

        layout.setSpacing(true);
        setWidth("95%");
        layout.setAlignItems(FlexComponent.Alignment.AUTO);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        layout.add(swagger);


        add(layout);
    }

}
