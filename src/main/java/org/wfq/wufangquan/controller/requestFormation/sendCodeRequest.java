package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record sendCodeRequest(
        @NotNull String str,
        @NotNull String method
) {
}
