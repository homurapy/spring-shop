package com.example.springshop.frontend;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("main")
public class MainView extends VerticalLayout {
    public MainView() {
HelloWorldView helloWorldView = new HelloWorldView();
    }
}
