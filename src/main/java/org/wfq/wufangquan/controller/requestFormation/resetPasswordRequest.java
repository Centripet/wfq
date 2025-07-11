package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record resetPasswordRequest(
        @NotNull String account,
        @NotNull String verificationCode,
        @NotNull String passwordHash,
        @NotNull String passwordHashRe
) {
}
