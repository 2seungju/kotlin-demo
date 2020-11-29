package com.example.kotlindemo.service

import com.example.kotlindemo.model.OAuthToken
import com.example.kotlindemo.utils.OAuthUtil
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

        val oAuthUtil: OAuthUtil = OAuthUtil.Builder()
                .clientId(clientId)
                .callback(callback)
                .state(state)
                .build();

        return oAuthUtil.createAuthorizationUri();
    }

    private fun generateState(): String {
        val secureRandom = SecureRandom();

        return BigInteger(130, secureRandom).toString(32);
    }

    override fun fetchToken(authCode: String): OAuthToken {
        val oAuthUtil: OAuthUtil = OAuthUtil.Builder()
                .clientId(clientId)
                .secretKey(secretKey)
                .callback(callback)
                .code(authCode)
                .build();

        return oAuthUtil.fetchOAuthToken();
    }
}