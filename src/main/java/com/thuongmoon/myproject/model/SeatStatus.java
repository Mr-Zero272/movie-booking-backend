package com.thuongmoon.myproject.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatStatus implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private SeatStatusE status;
	private int price;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "seat_id")
	private Seat seat;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "screening_id")
	private Screening screening;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SeatStatus other = (SeatStatus) obj;
		return Objects.equals(screening, other.screening) && Objects.equals(seat, other.seat) && status == other.status;
	}

	@Override
	public int hashCode() {
		return 2023;
	}
}
