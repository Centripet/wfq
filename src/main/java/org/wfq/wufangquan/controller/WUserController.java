package org.wfq.wufangquan.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wfq.wufangquan.controller.requestFormation.collectionListRequest;
import org.wfq.wufangquan.controller.requestFormation.userInfoModifyRequest;
import org.wfq.wufangquan.controller.requestFormation.userInfoRequest;
import org.wfq.wufangquan.controller.requestFormation.userSearchRequest;
import org.wfq.wufangquan.entity.JwtPayload;
import org.wfq.wufangquan.entity.regen.SUser;
import org.wfq.wufangquan.service.IWUserService;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponse;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponseWrap;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class WUserController {
    private final IWUserService userService;

    @PostMapping("/userSearch")
    @ApiResponseWrap
    public List<SUser> userSearch(
            @RequestBody userSearchRequest request,
            HttpServletResponse response
    ) {
        return userService.userSearch(request.keyword());
    }

    @PostMapping("/userInfo")
    @ApiResponseWrap
    public ApiResponse<?> userInfo(
            @RequestBody userInfoRequest request,
            HttpServletResponse response
    ) {
        return ApiResponse.success(userService.userInfo(request.user_id()));
    }

    @PostMapping("/userInfoModify")
    @ApiResponseWrap
    public ApiResponse<?> userInfoModify(
            @RequestBody userInfoModifyRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        return userService.userInfoModify(user_id, request);
    }

    @PostMapping("/collectionList")
    @ApiResponseWrap
    public ApiResponse<?> collectionList(
            @RequestBody collectionListRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        return ApiResponse.success(userService.collectionList(user_id, request));
    }


}
