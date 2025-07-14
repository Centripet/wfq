package org.wfq.wufangquan.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wfq.wufangquan.controller.requestFormation.taskAxisRequest;
import org.wfq.wufangquan.entity.JwtPayload;
import org.wfq.wufangquan.service.IWProjectService;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponse;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponseWrap;

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
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class WProjectController {

    private final IWProjectService projectService;

    @PostMapping("/taskAxis")
    @ApiResponseWrap
    public ApiResponse<?>  taskAxis(
            @RequestBody taskAxisRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        return ApiResponse.success(projectService.taskAxis(request));
    }

// 全文搜索*
//    @PostMapping("/taskSearch")
//    @ApiResponseWrap
//    public ApiResponse<?>  taskSearch(
//            @RequestBody taskSearchRequest request,
//            HttpServletResponse response
//    ) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
//        String user_id = payload.getUser_id();
//
//
//        return ApiResponse.success("");
//    }





}
