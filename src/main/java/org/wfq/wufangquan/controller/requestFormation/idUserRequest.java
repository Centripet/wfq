package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record idUserRequest(
        @NotNull String userId
) {
}
