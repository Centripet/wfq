package org.wfq.wufangquan.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wfq.wufangquan.controller.requestFormation.*;
import org.wfq.wufangquan.entity.JwtPayload;
import org.wfq.wufangquan.entity.regen.WFile;
import org.wfq.wufangquan.entity.regen.WMessage;
import org.wfq.wufangquan.nettyService.NettyPushService;
import org.wfq.wufangquan.service.AliOssService;
import org.wfq.wufangquan.service.IWCircleService;
import org.wfq.wufangquan.service.IWFileService;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponse;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponseWrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Centripet
 * @since 2025-06-17
 */
@RestController
@RequestMapping("/api/circle")
@RequiredArgsConstructor
public class WCircleController {

    private final IWCircleService wCircleService;
    private final AliOssService aliOssService;
    private final IWFileService fileService;
    private final NettyPushService nettyPushService;

    @PostMapping("/circlePublish")
    @ApiResponseWrap
    public ApiResponse<?> circlePublish(
            @RequestBody circlePublishRequest request,
            HttpServletResponse response
            ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        List<WFile> wFiles;
//      OSS fileUploadService *
        if (!request.files().isEmpty()) {
            Map<uploadSubmitRequest, Boolean> keyMap = aliOssService.fileExistsList(request.files());

            if (aliOssService.fileExists(keyMap)) {
                return ApiResponse.fail(400, "文件上传未全部成功",keyMap);
            }

            wFiles = fileService.uploadSubmitMult(user_id, keyMap);
        } else {
            wFiles = null;
        }

        return ApiResponse.success(wCircleService.circlePublish(user_id, request, wFiles));
    }


    @PostMapping("/circleList")
    @ApiResponseWrap
    public ApiResponse<?> circleList(
            @RequestBody circleListRequest req,
            HttpServletResponse response
    ) {
        int type;
        if (req.type()>0 && req.type()<5) {
            type=req.type();
        } else {
            type=0;
        }

        String sort;
        if (req.sort().equals("time") || req.sort().equals("hot")) {
            sort = req.sort();
        } else {
            sort = "time";
        }

        circleListRequest request = new circleListRequest(
                (req.page() == null) ? 1 : req.page(),
                (req.size() == null) ? 10 : req.size(),
                type,
                req.publisher(),
                req.project_id(),
                sort
        );

        return ApiResponse.success(wCircleService.circleList(request, true));
    }

    @PostMapping("/circleDetail")
    @ApiResponseWrap
    public ApiResponse<?> circleDetail(
            @RequestBody circleDetailRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        Map<String, Object> circleTotal = wCircleService.circleDetail(user_id, request.circle_id());

        return ApiResponse.success(circleTotal);
    }

    @PostMapping("/circleShare")
    @ApiResponseWrap
    public ApiResponse<?> circleShare(
            @RequestBody circleShareRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        nettyPushService.circlePush(user_id, request);

        return ApiResponse.success("success");
    }

    @PostMapping("/modifyCircle")
    @ApiResponseWrap
    public ApiResponse<?> modifyCircle(
            @RequestBody modifyCircleRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        List<WFile> wFiles;
//      OSS fileUploadService *
        if (!request.add_files().isEmpty()) {
            Map<uploadSubmitRequest, Boolean> keyMap = aliOssService.fileExistsList(request.add_files());

            if (aliOssService.fileExists(keyMap)) {
                return ApiResponse.fail(400, "文件上传未全部成功",keyMap);
            }

            wFiles = fileService.uploadSubmitMult(user_id, keyMap);
        } else {
            wFiles = null;
        }

        boolean flag = wCircleService.modifyCircle(user_id, request, wFiles);

        return ApiResponse.success("修改成功");
    }

    @PostMapping("/deleteCircle")
    @ApiResponseWrap
    public ApiResponse<?> deleteCircle(
            @RequestBody deleteCircleRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        if (!wCircleService.deleteCircle(user_id, request.circle_id())) {
            return ApiResponse.fail(500,"删除失败");
        }

        return ApiResponse.success("删除成功");
    }
}



