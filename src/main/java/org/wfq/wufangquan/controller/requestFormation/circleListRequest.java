package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record circleListRequest(
        @NotNull @Min(1) @Max(100) Integer page,
        @NotNull @Min(1) @Max(100) Integer size,
//        0所有1文本2文件3图片4视频
        Integer type,
        String publisher,
        String project_id,
        String sort
) {
}
