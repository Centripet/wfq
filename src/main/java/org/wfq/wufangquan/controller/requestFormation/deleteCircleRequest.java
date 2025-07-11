package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record deleteCircleRequest(
    @NotNull String circle_id
) {
}
