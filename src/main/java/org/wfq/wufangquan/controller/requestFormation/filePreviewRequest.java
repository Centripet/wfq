package org.wfq.wufangquan.entity.regen;

import jakarta.validation.constraints.NotNull;

public record filePreviewRequest(
        @NotNull String file_id
) {
}
