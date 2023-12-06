package com.thuongmoon.myproject.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequest {
	private List<Long> ids;
	private String invoiceId;
	private String nameInTicket;
	private String emailInTicket;
}
