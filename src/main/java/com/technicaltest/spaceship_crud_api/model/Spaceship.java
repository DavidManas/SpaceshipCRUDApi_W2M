package com.technicaltest.spaceship_crud_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a spaceship entity with an ID, name, and the series it belongs to.
 */
@Entity
public class Spaceship {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String series;
	
	/**
	 * Default constructor required by JPA.
	 * 
	 * This constructor is used by the JPA provider to create instances of the
	 * Spaceship entity without initializing any fields.
	 */
	public Spaceship() {
	}
	
	/**
	 * Constructs a new Spaceship instance with the specified name and series.
	 * The spaceship ID will be automatically generated.
	 *
	 * @param name   the name of the spaceship
	 * @param series the series to which the spaceship belongs
	 */
	public Spaceship(String name, String series) {
        this.name = name;
        this.series = series;
    }

	/**
	 * Retrieves the unique identifier of the spaceship.
	 *
	 * @return the ID of the spaceship
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the unique identifier of the spaceship.
	 *
	 * @param id the new ID of the spaceship
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retrieves the name of the spaceship.
	 *
	 * @return the name of the spaceship
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the spaceship.
	 *
	 * @param name the new name of the spaceship
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retrieves the series to which the spaceship belongs.
	 *
	 * @return the series of the spaceship
	 */
	public String getSeries() {
		return series;
	}

	/**
	 * Sets the series to which the spaceship belongs.
	 *
	 * @param series the new series of the spaceship
	 */
	public void setSeries(String series) {
		this.series = series;
	}
}
