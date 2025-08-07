package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record taskHistoryRequest(
        @NotNull String task_id
) {
}
