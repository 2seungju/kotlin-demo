package com.example.kotlindemo.service

import com.example.kotlindemo.model.OAuthToken
import javax.servlet.http.HttpServletRequest

interface NaverLoginService {
    fun createUri(request: HttpServletRequest): String;

    fun fetchToken(authCode: String): OAuthToken;
}