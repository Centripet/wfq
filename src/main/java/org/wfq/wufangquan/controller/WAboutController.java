package org.wfq.wufangquan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wfq.wufangquan.entity.JwtPayload;


@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class testController {

    @PostMapping("/test")
    public String register(@RequestBody String str) {
        System.out.println(str);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String userId = payload.getUser_id();
        System.out.println(userId);
        return payload.toString();
    }

}
