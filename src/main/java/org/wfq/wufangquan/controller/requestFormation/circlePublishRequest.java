package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record circlePublishRequest(
        String title,
        String content,
        @NotNull Boolean is_public,
        @NotNull Integer type,
        List<uploadSubmitRequest> files,
        List<String> group_ids,
        List<String> project_ids
        ) {
//    public record fileInfo(
//            @NotNull String fileName,
//            @NotNull String fileType,
//            @NotNull Long fileSize,      // 用 Long 存字节数，方便处理大小
//            @NotNull String fileUrl,
//            String fileInfo              // 如果是必填也加@NotNull，否则可选
//    ) {}
}
