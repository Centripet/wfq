package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record taskDetailRequest(
        @NotNull String task_id
) {
}
