package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record friendRequestHandleRequest(
        @NotNull String request_id,
        @NotNull int status
) {
}
