package com.example.springshop.frontend;

import com.example.springshop.service.ProductService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Route("main")
public class MainView extends VerticalLayout {
    private ProductService productService;
}
