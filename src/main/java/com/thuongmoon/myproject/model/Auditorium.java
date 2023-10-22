package com.thuongmoon.myproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Auditorium implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL,
			mappedBy = "auditorium", orphanRemoval = true)
	@JsonIgnore
	private List<Screening> screenings = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL,
			mappedBy = "auditorium", orphanRemoval = true)
	@JsonIgnore
	private List<Seat> seats = new ArrayList<>();

	public Auditorium() {}

	public Auditorium(Long id, String name, List<Screening> screenings, List<Seat> seats) {
		super();
		this.id = id;
		this.name = name;
		this.screenings = screenings;
		this.seats = seats;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Screening> getScreenings() {
		return screenings;
	}

	public void setScreenings(List<Screening> screenings) {
		this.screenings = screenings;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	@Override
	public String toString() {
		return "Auditorium [id=" + id + ", name=" + name + ", screenings=" + screenings + ", seats=" + seats + "]";
	}
}
