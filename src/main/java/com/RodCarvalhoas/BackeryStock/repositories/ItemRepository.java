package com.RodCarvalhoas.BackeryStock.repositories;

import com.RodCarvalhoas.BackeryStock.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT obj FROM Item obj WHERE obj.categoria.id = :id_cat ORDER BY obj.name")
    List<Item> findAllByCategoria(@Param(value = "id_cat") Integer id_cat);

}
