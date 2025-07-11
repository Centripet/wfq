package org.wfq.wufangquan.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wfq.wufangquan.configuration.pubPreviewRequest;
import org.wfq.wufangquan.controller.requestFormation.*;
import org.wfq.wufangquan.entity.JwtPayload;
import org.wfq.wufangquan.entity.regen.WFile;
import org.wfq.wufangquan.service.*;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponse;

import java.io.File;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Centripet
 * @since 2025-06-02
 */
@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class WNoticeController {
    private final IWNoticeService noticeService;
    private final JwtService jwtService;
    private final IWFileService fileService;
    private final AliOssService aliOssService;
    private final kkFileViewService fileViewService;

    @PostMapping("/noticeList")
    public ApiResponse<?> getNotices(
            @RequestBody getNoticesRequest req,
            HttpServletResponse response
            ) {
        getNoticesRequest request = new getNoticesRequest(
                (req.page() == null) ? 1 : req.page(),
                (req.size() == null) ? 10 : req.size()
        );
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        return ApiResponse.success(noticeService.getNotices(user_id, request));
    }

    @PostMapping("/noticeDetail")
    public ApiResponse<?> getNoticeDetail(
            @RequestBody getNoticeDetailRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

//        return ApiResponse.success(noticeService.getNoticeByNoticeId(request.notice_id()));
        return ApiResponse.success(noticeService.getNoticeDetail(user_id, request.notice_id()));
    }


    @PostMapping("/pubUpload")
    public ApiResponse<?> pubUpload(
            @RequestBody uploadSubmitRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        if(!aliOssService.fileExists(request.key())) {
            return ApiResponse.fail(500, "上传失败");
        }

        String file_id = fileService.uploadSubmit(user_id, request);
        if (!noticeService.addPublicity(file_id)) {
            return ApiResponse.fail(500, "提交失败");
        };

        return ApiResponse.success("success");
    }

    @PostMapping("/pubList")
    public ApiResponse<?> pubList(
            @RequestBody pubListRequest req,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        pubListRequest request = new pubListRequest(
                (req.page() == null) ? 1 : req.page(),
                (req.size() == null) ? 10 : req.size()
        );

        return ApiResponse.success(noticeService.pubList(user_id, request));
    }

    @PostMapping("/pubPreview")
    public ApiResponse<?> pubPreview(
            @RequestBody pubPreviewRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> pub = fileService.getFileById(request.pub_id());
        String key = (String) pub.get("file_key");
        try {

//            File file = aliOssService.downloadFromOss(key);
//            String previewUrl = fileViewService.uploadToKkFileView(file);

            String signedUrl = aliOssService.generateDownloadUrl(key, 300);
            String previewUrl = fileViewService.generateKkFilePreviewUrl(signedUrl);

            pub.put("preview", previewUrl);

            return ApiResponse.success(pub);
        } catch (Exception e) {

            return ApiResponse.fail(500, "预览生成失败: " + e.getMessage());
        }

    }

}
