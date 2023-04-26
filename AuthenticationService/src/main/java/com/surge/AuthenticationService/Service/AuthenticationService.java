package com.surge.AuthenticationService.Service;

import com.surge.AuthenticationService.dto.AuthenticationRequest;
import com.surge.AuthenticationService.dto.AuthenticationResponse;
import com.surge.AuthenticationService.dto.BaseResponse;
import com.surge.AuthenticationService.dto.RegisterRequest;

public interface AuthenticationService {
    BaseResponse<AuthenticationResponse> authenticate(AuthenticationRequest request);

    BaseResponse<AuthenticationResponse> register(RegisterRequest request);
}
