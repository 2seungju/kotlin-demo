package com.example.kotlindemo.utils

import com.example.kotlindemo.model.OAuthToken
import com.example.kotlindemo.common.NaverOAuthDomain
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

// 현재는 네이버 로그인만 구현
class OAuthUtil private constructor(
        private val clientId: String?,
        private val secretKey: String?,
        private val callback: String?,
        private val state: String?,
        private val code: String?
) {
    private val restTemplate = RestTemplate();
    private val objectMapper = ObjectMapper().registerKotlinModule();

    fun createAuthorizationUri(): String {
        val encodedCallback: String = java.net.URLEncoder.encode(callback, "utf-8");

        return "${NaverOAuthDomain.AUTH.domain}&client_id=$clientId&redirect_uri=$encodedCallback&state=$state";
    }

    fun fetchOAuthToken(): OAuthToken {
        val apiUri = "${NaverOAuthDomain.ACCESS_TOKEN.domain}&client_id=$clientId&client_secret=$secretKey&code=$code"

        val responseEntity: ResponseEntity<String> = requestGetApi(apiUri);

        return objectMapper.readValue(responseEntity.body?: "fail");
    }

    fun refreshOAuthToken(refreshToken: String): OAuthToken {
        val apiUri = "${NaverOAuthDomain.REFRESH_TOKEN.domain}&client_id=$clientId&client_secret=$secretKey&refresh_token=$refreshToken"

        val responseEntity: ResponseEntity<String> = requestGetApi(apiUri);

        return objectMapper.readValue(responseEntity.body?: "fail");
    }

    fun deleteOAuthToken(accessToken: String): OAuthToken {
        val apiUri = "${NaverOAuthDomain.DELETE_TOKEN.domain}&client_id=$clientId&client_secret=$secretKey&access_token=$accessToken"

        val responseEntity: ResponseEntity<String> = requestGetApi(apiUri);

        return objectMapper.readValue(responseEntity.body?: "fail");
    }

    private fun requestGetApi(apiUri: String): ResponseEntity<String> {
        return restTemplate.exchange(apiUri, HttpMethod.GET, null, String::class.java);
    }

    class Builder {
        private var clientId: String? = null;
        private var secretKey: String? = null
        private var callback: String? = null
        private var state: String? = null
        private var code: String? = null

        fun clientId(clientId: String?) = apply { this.clientId = clientId };
        fun secretKey(secretKey: String?) = apply { this.secretKey = secretKey };
        fun callback(callback: String?) = apply { this.callback = callback };
        fun state(state: String?) = apply { this.state = state };
        fun code(code: String?) = apply { this.code = code };

        fun build() = OAuthUtil(clientId, secretKey, callback, state, code);
    }

    override fun toString(): String {
        return "OAuthUtil{clientId=$clientId, secretKey=$secretKey, callback=$callback, state=$state, code=$code}";
    }
}