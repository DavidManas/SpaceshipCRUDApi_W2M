package com.technicaltest.spaceship_crud_api.exception;

/**
 * Exception thrown when a requested resource is not found.
 *
 * This exception is a runtime exception that extends {@link RuntimeException}.
 * It can be thrown by service methods or controllers when an attempt is made
 * to access a resource that does not exist in the system, such as a spaceship
 * that is not found in the database.
 */
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L; 

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message that explains the reason for the exception
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
