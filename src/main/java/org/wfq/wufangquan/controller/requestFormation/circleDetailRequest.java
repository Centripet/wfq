package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record circleDetailRequest(
    @NotNull String circle_id
) {
}
