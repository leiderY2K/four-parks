package com.project.layer.Services.IpRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface RequestService {

    String getClientIp(HttpServletRequest request);
}
