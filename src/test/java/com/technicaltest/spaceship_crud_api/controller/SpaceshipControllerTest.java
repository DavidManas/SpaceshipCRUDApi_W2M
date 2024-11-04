package com.technicaltest.spaceship_crud_api.controller;

import com.technicaltest.spaceship_crud_api.exception.GlobalExceptionHandler;
import com.technicaltest.spaceship_crud_api.exception.ResourceNotFoundException;
import com.technicaltest.spaceship_crud_api.model.Spaceship;
import com.technicaltest.spaceship_crud_api.service.SpaceshipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SpaceshipControllerTest {

    @InjectMocks
    private SpaceshipController spaceshipController;

    @Mock
    private SpaceshipService spaceshipService;
    
    private MockMvc mockMvc;

    /**
     * Initializes the mocks before each test and sets up the MockMvc instance 
     * for testing the SpaceshipController with a global exception handler.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        mockMvc = MockMvcBuilders.standaloneSetup(spaceshipController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    /**
     * Tests the retrieval of all spaceships.
     * Verifies that the response status is OK and the body contains the expected list of spaceships.
     */
    @Test
    public void testGetAllSpaceships() {
        // Arrange
        Spaceship spaceship1 = new Spaceship("X-Wing", "Star Wars");
        Spaceship spaceship2 = new Spaceship("TIE Fighter", "Star Wars");

        List<Spaceship> spaceships = Arrays.asList(spaceship1, spaceship2);
        when(spaceshipService.getAllSpaceships(anyInt(), anyInt())).thenReturn(spaceships);

        // Act
        ResponseEntity<List<Spaceship>> response = spaceshipController.getAllSpaceships(0, 10);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(spaceships, response.getBody());
    }

    /**
     * Tests the retrieval of a spaceship by its ID when the spaceship exists.
     * Verifies that the response status is OK and the body contains the expected spaceship.
     */
    @Test
    public void testGetSpaceshipById_Found() {
        // Arrange
        Spaceship spaceship = new Spaceship("X-Wing", "Star Wars");
        when(spaceshipService.getSpaceshipById(1L)).thenReturn(Optional.of(spaceship));

        // Act
        ResponseEntity<Spaceship> response = spaceshipController.getSpaceshipById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(spaceship, response.getBody());
    }

    /**
     * Tests the retrieval of a spaceship by its ID when the spaceship does not exist.
     * Verifies that the response status is NOT FOUND and the body is null.
     */
    @Test
    public void testGetSpaceshipById_NotFound() {
        // Arrange
        when(spaceshipService.getSpaceshipById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Spaceship> response = spaceshipController.getSpaceshipById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    /**
     * Tests the retrieval of spaceships by name.
     * Verifies that the response status is OK and the body contains the expected list of spaceships.
     */
    @Test
    public void testGetSpaceshipsByName() {
        // Arrange
        Spaceship spaceship = new Spaceship("X-Wing", "Star Wars");
        List<Spaceship> spaceships = List.of(spaceship);
        when(spaceshipService.getSpaceshipsByName("X-Wing")).thenReturn(spaceships);

        // Act
        ResponseEntity<List<Spaceship>> response = spaceshipController.getSpaceshipsByName("X-Wing");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(spaceships, response.getBody());
    }

    /**
     * Tests the creation of a new spaceship.
     * Verifies that the response status is OK and the body contains the created spaceship.
     */
    @Test
    public void testCreateSpaceship() {
        // Arrange
        Spaceship spaceship = new Spaceship("X-Wing", "Star Wars");
        when(spaceshipService.saveSpaceship(any(Spaceship.class))).thenReturn(spaceship);

        // Act
        ResponseEntity<Spaceship> response = spaceshipController.createSpaceship(spaceship);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(spaceship, response.getBody());
    }

    /**
     * Tests the updating of an existing spaceship.
     * Verifies that the response status is OK and the body contains the updated spaceship.
     */
    @Test
    public void testUpdateSpaceship() {
        // Arrange
        Spaceship spaceship = new Spaceship("X-Wing", "Star Wars");
        when(spaceshipService.saveSpaceship(any(Spaceship.class))).thenReturn(spaceship);

        // Act
        ResponseEntity<Spaceship> response = spaceshipController.updateSpaceship(1L, spaceship);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(spaceship, response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    /**
     * Tests the deletion of a spaceship by its ID.
     * Verifies that the response status is NO CONTENT and the delete operation is called once.
     */
    @Test
    public void testDeleteSpaceship() {
        // Arrange
        doNothing().when(spaceshipService).deleteSpaceship(1L);

        // Act
        ResponseEntity<Void> response = spaceshipController.deleteSpaceship(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(spaceshipService, times(1)).deleteSpaceship(1L);
    }
    
    /**
     * Tests the exception handling when trying to retrieve a non-existent spaceship by ID.
     * Verifies that the response status is NOT FOUND and the error details are correctly returned.
     */
    @Test
    public void testGetSpaceshipById_NotFoundException() throws Exception {
        // Arrange
        long nonExistentId = 999L;
        when(spaceshipService.getSpaceshipById(nonExistentId))
            .thenThrow(new ResourceNotFoundException("Spaceship not found"));

        // Act & Assert
        mockMvc.perform(get("/api/spaceships/" + nonExistentId))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.error").value("Not Found"))
            .andExpect(jsonPath("$.message").value("Spaceship not found"))
            .andExpect(jsonPath("$.path").value("/api/spaceships/" + nonExistentId));
    }

    /**
     * Tests the global exception handling for unexpected runtime exceptions.
     * Verifies that the response status is INTERNAL SERVER ERROR and the error details are correctly returned.
     */
    @Test
    public void testGenericExceptionHandling() throws Exception {
        // Arrange
        when(spaceshipService.getAllSpaceships(anyInt(), anyInt()))
            .thenThrow(new RuntimeException("Unexpected error"));

        // Act & Assert
        mockMvc.perform(get("/api/spaceships"))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.status").value(500))
            .andExpect(jsonPath("$.error").value("Internal Server Error"))
            .andExpect(jsonPath("$.message").value("Unexpected error"))
            .andExpect(jsonPath("$.path").value("/api/spaceships"));
    }
}
