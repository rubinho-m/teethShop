package com.rubinho.teethshop.repository;

import com.rubinho.teethshop.model.Order;
import com.rubinho.teethshop.model.OrderState;
import com.rubinho.teethshop.model.Product;
import com.rubinho.teethshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Modifying
    @Transactional
    @Query("""
            UPDATE Order o SET o.description = :description, o.address = :address, o.state = :state WHERE o.id = :id""")
    void changeOrder(@Param("id") Long id,
                     @Param("description") String description,
                     @Param("address") String address,
                     @Param("state") OrderState state);


    List<Order> findAllByUserAndState(User user, OrderState state);
}
