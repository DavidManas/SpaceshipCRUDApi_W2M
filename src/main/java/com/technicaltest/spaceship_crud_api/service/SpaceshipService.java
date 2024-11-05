package com.technicaltest.spaceship_crud_api.service;

import com.technicaltest.spaceship_crud_api.model.Spaceship;
import com.technicaltest.spaceship_crud_api.repository.SpaceshipRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpaceshipService {

    @Autowired
    private SpaceshipRepository repository;
    
    public SpaceshipService(SpaceshipRepository repository) {
        this.repository = repository;
    }

	/**
	* Initializes the spaceship data in the repository after the bean creation.
	*
	* This method is annotated with {@code @PostConstruct} to populate the repository
	* with a predefined list of spaceships from the "Star Wars" series when the application
	* context is loaded.
	*/
    @PostConstruct
    public void initData() {
        repository.save(new Spaceship("X-Wing", "Star Wars"));
        repository.save(new Spaceship("TIE Fighter", "Star Wars"));
        repository.save(new Spaceship("Millennium Falcon", "Star Wars"));
        repository.save(new Spaceship("Slave I", "Star Wars"));
        repository.save(new Spaceship("Star Destroyer", "Star Wars"));
        repository.save(new Spaceship("Executor", "Star Wars"));
        repository.save(new Spaceship("Nebulon-B Frigate", "Star Wars"));
        repository.save(new Spaceship("Jedi Starfighter", "Star Wars"));
    }

    public List<Spaceship> getAllSpaceships(int page, int size) {
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    public Optional<Spaceship> getSpaceshipById(Long id) {
        return repository.findById(id);
    }

    @Cacheable("spaceshipsByName")
    public List<Spaceship> getSpaceshipsByName(String name) {
        return repository.findByNameContaining(name);
    }

    public Spaceship saveSpaceship(Spaceship spaceship) {
        return repository.save(spaceship);
    }

    public void deleteSpaceship(Long id) {
        repository.deleteById(id);
    }
}
