package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record idMomentRequest(
        @NotNull String MomentId
) {
}
