package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record fileUploadRequest(
        @NotNull String suffix,
        @NotNull int MAX_SIZE_FLAG
//        ,int type,
//        String title,
//        String info
) {
}
