package com.example.springshop.frontend;

import com.example.springshop.config.security.CustomUserDetailsService;
import com.example.springshop.dto.ProductDto;
import com.example.springshop.model.Review;
import com.example.springshop.service.ReviewService;
import com.example.springshop.util.MappingUtil;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.stream.Collectors;

@Route("moderator")
public class ModeratorView extends VerticalLayout {
    private final ProductDto dto;
    private Grid<Review> grid = new Grid<>(Review.class);
    private ReviewService reviewService;

    public ModeratorView(ReviewService reviewService, CustomUserDetailsService userDetailsService) {
        this.reviewService = reviewService;
        this.dto = (ProductDto) ComponentUtil.getData(UI.getCurrent(), "dto");
        System.out.println(dto.getName());
        add(findAll());

    }

    public Grid findAll() {
        List<Review> reviews = reviewService.findAllByProduct(dto.getId());
        grid.setColumns("text", "isModerated");
        grid.setItems(reviews);
        grid.setSizeUndefined();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        grid.addColumn(new ComponentRenderer<>(item -> {
            var toModeratedButton = new Button("Moderated", rev -> {
                reviewService.setModerated(item.getId());
                Notification.show("Отзыв проверен");
                grid.getDataProvider().refreshAll();
            });
            return new HorizontalLayout(toModeratedButton);
        }));

        grid.addColumn(new ComponentRenderer<>(item -> {
            var toRemoveButton = new Button("Удалить", rev -> {
                reviewService.remove(item);
                grid.getDataProvider().refreshAll();
                Notification.show("Отзыв был удален");
            });
            return new HorizontalLayout(toRemoveButton);

        }));
        ListDataProvider<Review> dataProvider = DataProvider.ofCollection(reviews);
        grid.setDataProvider(dataProvider);
        return grid;
    }
}
