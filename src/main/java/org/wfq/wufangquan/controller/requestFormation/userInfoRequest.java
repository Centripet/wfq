package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record userInfoRequest(
        @NotNull String user_id
) {
}
