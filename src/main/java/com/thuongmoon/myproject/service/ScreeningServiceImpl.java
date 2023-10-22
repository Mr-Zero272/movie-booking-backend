package com.thuongmoon.myproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.thuongmoon.myproject.dao.ScreeningDao;
import com.thuongmoon.myproject.dto.ScreeningTypeDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {
	private final ScreeningDao screeningDao;
	@Override
	public List<ScreeningTypeDto> getAllScreeningTypes() {
		List<String> allScreeningTypes = screeningDao.findAllScreeningTypes();
		List<ScreeningTypeDto> types = new ArrayList<>();
		for (int i = 0; i < allScreeningTypes.size(); i++) {
			types.add(new ScreeningTypeDto(allScreeningTypes.get(i)));
		}
		return types;
	}

}
