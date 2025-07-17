package org.wfq.wufangquan.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.wfq.wufangquan.controller.requestFormation.fileUploadRequest;
import org.wfq.wufangquan.controller.requestFormation.uploadSubmitRequest;
import org.wfq.wufangquan.entity.JwtPayload;
import org.wfq.wufangquan.controller.requestFormation.filePreviewRequest;
import org.wfq.wufangquan.service.AliOssService;
import org.wfq.wufangquan.service.IWFileService;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponse;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponseWrap;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Centripet
 * @since 2025-06-21
 */
@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class WFileController {

    private final AliOssService aliOssService;
    private final IWFileService fileService;
    private static final Set<String> ALLOWED_SUFFIXES = Set.of(
            ".jpg", ".jpeg", ".png", ".gif", ".bmp",
            ".pdf", ".doc", ".docx", ".xls", ".xlsx",
            ".txt", ".zip", ".rar", ".mp4"
    );

    @PostMapping("/uploadPolicy")
    @ApiResponseWrap
    public ApiResponse<?> uploadPolicy(
            @RequestBody fileUploadRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        if (request.suffix() == null ||
                request.suffix().isBlank() ||
                !ALLOWED_SUFFIXES.contains(request.suffix().toLowerCase())) {
            return ApiResponse.fail(400, "非法的文件后缀名");
        }

        String key = aliOssService.generateObjectKey("general", user_id, request.suffix());
        if (request.MAX_SIZE_FLAG() == 0) {
            return ApiResponse.success(aliOssService.generateUploadPolicy(key, AliOssService.MAX_FILE_SIZE_50MB));
        } else if (request.MAX_SIZE_FLAG() == 1) {
            return ApiResponse.success(aliOssService.generateUploadPolicy(key, AliOssService.MAX_FILE_SIZE_500MB));
        }

        return ApiResponse.fail(500, "生成凭证失败");
    }

    @PostMapping("/uploadSubmit")
    @ApiResponseWrap
    public ApiResponse<?> uploadSubmit(
            @RequestBody uploadSubmitRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        return ApiResponse.success(Map.of("file_id", fileService.uploadSubmit(user_id, request, false)));
    }

    @PostMapping("/filePreview")
    @ApiResponseWrap
    public ApiResponse<?>  filePreview(
            @RequestBody filePreviewRequest request,
            HttpServletResponse response
    ) {

        return ApiResponse.success(fileService.filePreview(request.file_id()));
    }


}
