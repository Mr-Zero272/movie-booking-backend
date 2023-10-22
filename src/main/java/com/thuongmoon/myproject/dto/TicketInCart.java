package com.thuongmoon.myproject.dto;

import com.thuongmoon.myproject.model.SeatStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketInCart {
	private SeatStatus seatStatus;
	private String movieName;
	private String director;
	private String imageName;
	private String imageHorizonName;
	private Long movieId;
}
