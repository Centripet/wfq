package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record taskStatusRequest(
        @NotNull String task_id,
        @NotNull String action,
        @NotNull String comment,
        String location
) {
}
