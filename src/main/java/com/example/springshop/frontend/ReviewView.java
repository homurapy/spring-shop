package com.example.springshop.frontend;

import com.example.springshop.config.security.CustomUserDetailsService;
import com.example.springshop.dto.ProductDto;
import com.example.springshop.model.Review;
import com.example.springshop.model.User;
import com.example.springshop.service.ReviewService;
import com.example.springshop.util.MappingUtil;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Route("review")
public class ReviewView extends VerticalLayout {
    private final ReviewService reviewService;
    private final CustomUserDetailsService userDetailsService;
    private final ProductDto dto;

    public ReviewView(ReviewService reviewService, CustomUserDetailsService userDetailsService) {
        this.reviewService = reviewService;
        this.userDetailsService = userDetailsService;
        this.dto = (ProductDto) ComponentUtil.getData(UI.getCurrent(), "dto");


        if (this.dto == null) {
            UI.getCurrent().navigate("");
        } else {
            var reviews = reviewService
                    .findAllByProduct(dto.getId()).stream()
                    .filter(review -> review.getIsModerated())
                    .collect(Collectors.toList());
            initView(reviews);
            if(userDetailsService.getUser().getRole().equals("ROLE_ADMIN"))
            {
                initAddminButton();
            }
        }
    }

    private void initView(List<Review> reviews) {
        var toBackButton = new Button("назад", item -> {
            UI.getCurrent().navigate("product");
        });
        add(toBackButton);
        for (Review review : reviews) {
            TextArea textArea = new TextArea(review.getUser().getName());
            textArea.setValue(review.getText() != null ? review.getText() : "");
            textArea.setReadOnly(true);
            textArea.setSizeFull();
            add(textArea);
        }

        TextArea editableTextArea = new TextArea();
        editableTextArea.setSizeFull();

        var saveReviewButton = new Button("Сохранить отзыв", event -> {
            var review = new Review();
            review.setProduct(MappingUtil.dtoToProduct(dto, null));
            review.setUser(userDetailsService.getUser());
            review.setText(editableTextArea.getValue());
            if (editableTextArea == null) {
                Notification.show("Поле с отзывом не заполнено");
            } else {
                reviewService.save(review);
                Notification.show("Ваш отзыв успешно сохранён");
            }
        });
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(editableTextArea, saveReviewButton);
    }
    private void initAddminButton(){
        var showAllButton = new Button("Модерация", item -> {
            ComponentUtil.setData(UI.getCurrent(), "dto", dto);
            UI.getCurrent().navigate("moderator");
            });
        add(showAllButton);
    }

}
