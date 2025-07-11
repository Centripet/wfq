package org.wfq.wufangquan.configuration;

import jakarta.validation.constraints.NotNull;

public record pubPreviewRequest(
        @NotNull String pub_id
) {
}
