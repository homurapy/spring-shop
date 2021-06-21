package com.example.springshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @Column(name = "ID")
    private UUID uuid;

    @OneToOne(optional=false, cascade= CascadeType.ALL)
    @JoinColumn (name="cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    private String status;

}
