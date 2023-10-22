package com.thuongmoon.myproject.service;

import com.thuongmoon.myproject.model.AuthenticationResponse;
import com.thuongmoon.myproject.model.AuthenticationResquest;
import com.thuongmoon.myproject.model.RegisterRequest;

public interface AuthenticationService {
	public AuthenticationResponse register(RegisterRequest request);
	public AuthenticationResponse authenticate(AuthenticationResquest request);
}
