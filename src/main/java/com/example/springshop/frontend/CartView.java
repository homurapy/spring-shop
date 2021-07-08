package com.example.springshop.frontend;

import com.example.springshop.dto.ProductDto;
import com.example.springshop.model.Order;
import com.example.springshop.repository.OrderRepository;
import com.example.springshop.service.CartService;
import com.example.springshop.service.MailService;
import com.example.springshop.service.OrderService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Route("cart")
public class CartView extends VerticalLayout {
    private final Grid<ProductDto> grid = new Grid<>(ProductDto.class);
    private final CartService cartService;
    private final MailService mailService;
    private final OrderService orderService;

    public CartView(CartService cartService, MailService mailService, OrderService orderService) {
        this.cartService = cartService;
        this.mailService = mailService;
        this.orderService = orderService;
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
            IntegerField numberField = new IntegerField();
            numberField.setHasControls(true);
            numberField.setMin(0);
            numberField.setValue(item.getCount());
            numberField.addValueChangeListener(event -> cartService.updateQuantity(item, numberField.getValue()));
            add(numberField);
            return new HorizontalLayout(numberField);
        }));
    }

    private HorizontalLayout returnButton() {
        var button = new Button("Создать заказ", buttonClickEvent -> {
            Order order = orderService.createOrder(cartService.getProducts());
            String text = "Ваш заказ №" + order.getUuid() + " успешно создан, к оплате " + order.getTotalPrice() + " рублей";
            System.out.println(text);
            mailService.sendMessage("xzi123@mail.ru", text);
        });
        var toCartButton = new Button("назад", item -> {
            UI.getCurrent().navigate("product");
        });
        var clearCart = new Button("Очистить корзину", items -> {
            cartService.clearCart();
            grid.getDataProvider().refreshAll();
        });
        return new HorizontalLayout(toCartButton, clearCart, button);
    }
}
