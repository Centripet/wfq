package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record taskAnnotationRequest(
        @NotNull String file_id,
        @NotNull int type,
        @NotNull String content,
        String coordinate
) {
}
