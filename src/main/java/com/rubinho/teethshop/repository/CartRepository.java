package com.rubinho.teethshop.repository;

import com.rubinho.teethshop.model.Cart;
import com.rubinho.teethshop.model.CartId;
import com.rubinho.teethshop.model.Product;
import com.rubinho.teethshop.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart, CartId> {
    @Modifying
    @Transactional
    @Query("""
            DELETE FROM Cart c WHERE c.user= :user""")
    void deleteByUser(@Param("user") User user);


    @Modifying
    @Transactional
    @Query("""
            UPDATE Cart c SET c.count = :count WHERE c.user= :user AND c.product = :product""")
    void changeCartLine(@Param("count") Integer count, @Param("user") User user, @Param("product") Product product);


    List<Cart> findCartByUser(User user);
}
