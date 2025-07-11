package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record loginRequest(
        @NotNull String account,
        @NotNull String passwordHash,
        @NotNull String device
) {
}
