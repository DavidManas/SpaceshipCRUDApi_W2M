package com.technicaltest.spaceship_crud_api.repository;

import com.technicaltest.spaceship_crud_api.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> {
    List<Spaceship> findByNameContaining(String name);
}
