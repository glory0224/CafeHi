package com.cafeHi.www.common.security.common;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * 사용자가 전달하는 추가적인 파라미터를 전달하는 클래스
 */
public class FormWebAuthenticationDetails extends WebAuthenticationDetails {

    private String secretKey; // 암호화된 키
    private String customerIpAddress; // 사용자 IP 주소
    private String customerSessionId; // 사용자 세션 ID



    public FormWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        secretKey = request.getParameter("secret_key");
        customerIpAddress = request.getParameter("customer_ip_address");
        customerSessionId = request.getParameter("customer_sessionId");
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getCustomerIpAddress() {
        return customerIpAddress;
    }

    public String getCustomerSessionId() {
        return customerSessionId;
    }
}
