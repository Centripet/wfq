package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record groupInfoRequest(
        @NotNull String group_id
) {
}
