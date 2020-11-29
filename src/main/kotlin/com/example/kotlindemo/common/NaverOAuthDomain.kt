package com.example.kotlindemo.common

enum class NaverOAuthDomain(val domain: String) {
    AUTH("https://nid.naver.com/oauth2.0/authorize?response_type=code"),
    ACCESS_TOKEN("https://nid.naver.com/oauth2.0/token?grant_type=authorization_code"),
    REFRESH_TOKEN("https://nid.naver.com/oauth2.0/token?grant_type=refresh_token"),
    DELETE_TOKEN("https://nid.naver.com/oauth2.0/token?grant_type=delete&service_provider=NAVER"),
}