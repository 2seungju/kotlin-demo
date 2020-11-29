package com.example.kotlindemo.service

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.security.SecureRandom
import javax.servlet.http.HttpServletRequest

@Service
@ConfigurationProperties(prefix = "naver.login")
class NaverLoginServiceImpl: NaverLoginService {
    lateinit var clientId: String;
    lateinit var secretKey: String;
    lateinit var callback: String;

    override fun createUri(request: HttpServletRequest): String {
        val state = generateState();

        request.session.setAttribute("state", state);

        val encodedCallback: String = java.net.URLEncoder.encode(callback, "utf-8");

        return "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=$clientId&redirect_uri=$encodedCallback&state=$state";
    }

    private fun generateState(): String {
        val secureRandom = SecureRandom();

        return BigInteger(130, secureRandom).toString(32);
    }
}