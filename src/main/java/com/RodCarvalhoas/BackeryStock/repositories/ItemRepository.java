package com.RodCarvalhoas.BackeryStock.repositories;

import com.RodCarvalhoas.BackeryStock.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}
