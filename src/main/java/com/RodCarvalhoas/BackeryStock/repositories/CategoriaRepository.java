package com.RodCarvalhoas.BackeryStock.repositories;

import com.RodCarvalhoas.BackeryStock.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
