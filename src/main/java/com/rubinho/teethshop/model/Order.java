package com.rubinho.teethshop.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BucketItems> items;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Date date;

    @Column()
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderState state;
}
