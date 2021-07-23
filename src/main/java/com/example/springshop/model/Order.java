package com.example.springshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "\"order\"")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Order {

    @Id
    @Column(name = "id")
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total_price")
    private Integer totalPrice;

    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    List<Item> items;

    public Order(UUID randomUUID, User user, Integer totalPrice, Date date) {
    }
}
