package com.thuongmoon.myproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatDto {
	private Long seatId;
	private String status;
	private String numberSeat;
	private String rowSeat;
	private int price;
}
