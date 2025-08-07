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
import org.wfq.wufangquan.controller.requestFormation.*;
import org.wfq.wufangquan.entity.JwtPayload;
import org.wfq.wufangquan.entity.regen.WAnnotation;
import org.wfq.wufangquan.entity.regen.WFile;
import org.wfq.wufangquan.entity.regen.WProjectTask;
import org.wfq.wufangquan.entity.regen.WTaskStatus;
import org.wfq.wufangquan.service.AliOssService;
import org.wfq.wufangquan.service.IWFileService;
import org.wfq.wufangquan.service.IWProjectTaskService;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponse;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponseWrap;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Centripet
 * @since 2025-08-02
 */
@Slf4j
@RestController
@RequestMapping("/api/task-assistant")
@RequiredArgsConstructor
public class WTaskController {

    private final IWProjectTaskService projectTaskService;
    private final AliOssService ossService;
    private final IWFileService fileService;

    @PostMapping("/create_task")
    @ApiResponseWrap
    public ApiResponse<?> create_task(
            @RequestBody createTaskRequest request,
            HttpServletResponse response
    ) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        List<WFile> wFiles;
        if (!request.files().isEmpty()) {
            Map<uploadSubmitRequest, Boolean> keyMap = ossService.fileExistsList(request.files());

            if (!ossService.fileExists(keyMap)) {
                return ApiResponse.fail(400, "文件上传未全部成功",keyMap);
            }

            wFiles = fileService.uploadSubmitMult(user_id, keyMap);
        } else {
            wFiles = null;
        }

        WProjectTask task = projectTaskService.createTask(user_id, request, wFiles);

        return ApiResponse.success(task);
    }


    @PostMapping("/task_list")
    @ApiResponseWrap
    public ApiResponse<?> task_list(
            @RequestBody taskListRequest req,
            HttpServletResponse response
    ) throws IOException {
        taskListRequest request = new taskListRequest(
                req.str(),
                req.method(),
                (req.page() == null) ? 1 : req.page(),
                (req.size() == null) ? 10 : req.size()
        );

        return ApiResponse.success(projectTaskService.taskList(request));
    }


    @PostMapping("/task_detail")
    @ApiResponseWrap
    public ApiResponse<?> task_detail(
            @RequestBody taskDetailRequest request,
            HttpServletResponse response
    ) throws IOException {

        Map<String, Object> res = projectTaskService.taskDetail(request);

        return ApiResponse.success(res);
    }


    @PostMapping("/task_modify")
    @ApiResponseWrap
    public ApiResponse<?> task_modify(
            @RequestBody taskModifyRequest request,
            HttpServletResponse response
    ) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        if (payload.getRole().equals("ADMIN") || payload.getRole().equals("SUPERVISOR")) {

            List<WFile> wFiles;
            if (!request.files().isEmpty()) {
                Map<uploadSubmitRequest, Boolean> keyMap = ossService.fileExistsList(request.files());

                if (!ossService.fileExists(keyMap)) {
                    return ApiResponse.fail(400, "文件上传未全部成功",keyMap);
                }

                wFiles = fileService.uploadSubmitMult(user_id, keyMap);
            } else {
                wFiles = null;
            }


            if (projectTaskService.taskModify(user_id, request, wFiles)) {
                return ApiResponse.success("success");
            } else {
                return ApiResponse.fail(400, "任务已经处于进行中或已完成或不存在");
            }

        } else {
            return ApiResponse.fail(403, "权限不足");
        }

    }


    @PostMapping("/task_delete")
    @ApiResponseWrap
    public ApiResponse<?> task_delete(
            @RequestBody taskDeleteRequest request,
            HttpServletResponse response
    ) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();

        if (payload.getRole().equals("ADMIN") || payload.getRole().equals("SUPERVISOR")) {
            if (projectTaskService.taskDelete(request)) {
                return ApiResponse.success("success");
            } else {
                return ApiResponse.fail(400, "任务已经处于进行中或已完成或不存在");
            }

        } else {
            return ApiResponse.fail(403, "权限不足");
        }

    }


    @PostMapping("/task_status")
    @ApiResponseWrap
    public ApiResponse<?> task_status(
            @RequestBody taskStatusRequest request,
            HttpServletResponse response
    ) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();

        return projectTaskService.task_status(payload, request);
    }



    @PostMapping("/task_annotation")
    @ApiResponseWrap
    public ApiResponse<?> task_annotation(
            @RequestBody taskAnnotationRequest request,
            HttpServletResponse response
    ) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        return ApiResponse.success(projectTaskService.taskAnnotation(user_id, request));
    }


    @PostMapping("/task_history")
    @ApiResponseWrap
    public ApiResponse<?> task_history(
            @RequestBody taskHistoryRequest request,
            HttpServletResponse response
    ) throws IOException {

        List<Map<String, Object>> res = projectTaskService.task_history(request);

        return ApiResponse.success(res);
    }




}
