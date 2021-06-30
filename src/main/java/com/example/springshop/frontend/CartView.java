package com.example.springshop.frontend;

import com.example.springshop.dto.ProductDto;
import com.example.springshop.service.CartService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

@Route("cart")
public class CartView extends VerticalLayout {
    private final Grid<ProductDto> grid = new Grid<>(ProductDto.class);

    private final CartService cartService;

    public CartView(CartService cartService) {
        this.cartService = cartService;
        add(returnButton());
        initCartGrid();
        add(grid);
    }

    private void initCartGrid() {
        var products = cartService.getProducts();
        grid.setItems(products);
        grid.setColumns("name", "price");
        grid.setSizeUndefined();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        ListDataProvider<ProductDto> dataProvider = DataProvider.ofCollection(products);
        grid.setDataProvider(dataProvider);
        grid.addColumn(new ComponentRenderer<>(item -> {
            NumberField numberField = new NumberField();
            numberField.setHasControls(true);
            numberField.setMin(0);
            numberField.setValue(item.getCount());
            numberField.addValueChangeListener(event -> cartService.updateQuantity(item, numberField.getValue()));
            add(numberField);
            return new HorizontalLayout(numberField);
        }));
    }
    private HorizontalLayout returnButton() {
        var toCartButton = new Button("назад", item -> {
            UI.getCurrent().navigate("product");
//            cartService.saveCart(cartService.getProducts());
        });
        var clearCart = new Button("Очистить корзину", items -> {
            cartService.clearCart();
            grid.getDataProvider().refreshAll();
        });
        return new HorizontalLayout(toCartButton, clearCart);
    }
}
