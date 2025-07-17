package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record installStatusRequest(
        @NotNull String app_id
) {
}
