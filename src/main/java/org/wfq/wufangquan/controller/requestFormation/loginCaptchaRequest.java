package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record loginCaptchaRequest(
        @NotNull String account,
        @NotNull String verificationCode,
        @NotNull String device
) {
}
