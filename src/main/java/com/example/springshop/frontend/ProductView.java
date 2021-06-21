package com.example.springshop.frontend;

import com.example.springshop.dto.ProductDto;
import com.example.springshop.service.CartService;
import com.example.springshop.service.ProductService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Set;

@Route("product")

public class ProductView extends VerticalLayout {
    private ProductService productService;
    private final CartService cartService;

    private Grid<ProductDto> grid = new Grid<>(ProductDto.class);

    public ProductView(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService =cartService;

        findAll();
        add(initCartButton());
    }

    public void findAll() {
        List<ProductDto> productList = productService.findAll();
        grid.setColumns("name", "price");
        grid.setItems(productList);
        grid.setSizeUndefined();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        ListDataProvider<ProductDto> dataProvider = DataProvider.ofCollection(productList);
        grid.setDataProvider(dataProvider);
        add(grid);
    }

    private HorizontalLayout initCartButton() {
        var addToCartButton = new Button("Добавить в корзину", items -> {
            cartService.addProduct(grid.getSelectedItems());
            Notification.show("Товары успешно добавлены в корзину");
        });
        var toCartButton = new Button("Корзина", item -> {
            UI.getCurrent().navigate("cart");
        });
        return new HorizontalLayout(toCartButton, addToCartButton);
    }
}
