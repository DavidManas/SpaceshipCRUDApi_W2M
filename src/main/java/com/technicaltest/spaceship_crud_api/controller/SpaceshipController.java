package com.technicaltest.spaceship_crud_api.controller;

import com.technicaltest.spaceship_crud_api.model.Spaceship;
import com.technicaltest.spaceship_crud_api.service.SpaceshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/spaceships")
public class SpaceshipController {

    @Autowired
    private SpaceshipService service;

    @Operation(summary = "Get all spaceships", description = "Retrieve a paginated list of all spaceships.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spaceships retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<Spaceship>> getAllSpaceships(
            @Parameter(description = "Page number for pagination") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size for pagination") @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAllSpaceships(page, size));
    }

    @Operation(summary = "Get spaceship by ID", description = "Retrieve a spaceship by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spaceship retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Spaceship not found with given ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Spaceship> getSpaceshipById(
            @Parameter(description = "ID of the spaceship to retrieve") @PathVariable Long id) {
        return service.getSpaceshipById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Search spaceships by name", description = "Retrieve all spaceships that contain the given name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spaceships found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid name parameter"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search")
    public ResponseEntity<List<Spaceship>> getSpaceshipsByName(
            @Parameter(description = "Name to search in spaceship names") @RequestParam String name) {
        return ResponseEntity.ok(service.getSpaceshipsByName(name));
    }

    @Operation(summary = "Create a new spaceship", description = "Add a new spaceship to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spaceship created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid spaceship data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Spaceship> createSpaceship(
            @Parameter(description = "Spaceship object to create") @RequestBody Spaceship spaceship) {
        return ResponseEntity.ok(service.saveSpaceship(spaceship));
    }

    @Operation(summary = "Update a spaceship", description = "Update an existing spaceship with a given ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spaceship updated successfully"),
            @ApiResponse(responseCode = "404", description = "Spaceship not found with given ID"),
            @ApiResponse(responseCode = "400", description = "Invalid spaceship data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Spaceship> updateSpaceship(
            @Parameter(description = "ID of the spaceship to update") @PathVariable Long id,
            @Parameter(description = "Updated spaceship object") @RequestBody Spaceship spaceship) {
        spaceship.setId(id);
        return ResponseEntity.ok(service.saveSpaceship(spaceship));
    }

    @Operation(summary = "Delete a spaceship", description = "Delete a spaceship by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Spaceship deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Spaceship not found with given ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpaceship(
            @Parameter(description = "ID of the spaceship to delete") @PathVariable Long id) {
        service.deleteSpaceship(id);
        return ResponseEntity.noContent().build();
    }
}
