package org.wfq.wufangquan.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.wfq.wufangquan.controller.requestFormation.circlePublishRequest;
import org.wfq.wufangquan.controller.requestFormation.installInfoRequest;
import org.wfq.wufangquan.controller.requestFormation.installStatusRequest;
import org.wfq.wufangquan.controller.requestFormation.uploadSubmitRequest;
import org.wfq.wufangquan.entity.JwtPayload;
import org.wfq.wufangquan.entity.regen.WFile;
import org.wfq.wufangquan.service.IWApplicationService;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponse;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponseWrap;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Centripet
 * @since 2025-07-12
 */
@Slf4j
@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
public class WApplicationController {

    private IWApplicationService wApplicationService;

    @PostMapping("/installInfo")
    @ApiResponseWrap
    public ApiResponse<?> installInfo(
            @RequestBody installInfoRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        return ApiResponse.success(wApplicationService.installInfo(user_id, request));
    }

    @PostMapping("/installStatus")
    @ApiResponseWrap
    public ApiResponse<?> installStatus(
            @RequestBody installStatusRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        return ApiResponse.success(wApplicationService.installStatus(user_id, request));
    }


}
