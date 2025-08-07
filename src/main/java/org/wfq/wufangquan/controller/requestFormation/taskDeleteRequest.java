package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record taskDeleteRequest(
        @NotNull String task_id
) {
}
