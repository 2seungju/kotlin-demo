package com.example.kotlindemo.service

import javax.servlet.http.HttpServletRequest

interface NaverLoginService {
    fun createUri(request: HttpServletRequest): String;
}