package com.example.kotlindemo.controller

import com.example.kotlindemo.service.NaverLoginService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("users")
class MainController(private val naverLoginService: NaverLoginService) {

    @GetMapping("login")
    fun login(request: HttpServletRequest): String {
        val apiUri = naverLoginService.createUri(request);

        return "redirect:$apiUri";
    }

    @GetMapping("callback")
    @ResponseBody
    fun callback(request: HttpServletRequest, @RequestParam state: String, @RequestParam code: String): ResponseEntity<Any> {
        val storedState: String = request.session.getAttribute("state") as String;

        if (storedState != state) {
            return ResponseEntity("Token is invalid." , HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity("Token is valid." , HttpStatus.OK)
    }
}
