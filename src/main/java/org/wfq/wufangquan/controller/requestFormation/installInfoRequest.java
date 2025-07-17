package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record installInfoRequest(
        @NotNull String app_id
) {
}
