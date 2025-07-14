package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record filePreviewRequest(
        @NotNull String file_id
) {
}
