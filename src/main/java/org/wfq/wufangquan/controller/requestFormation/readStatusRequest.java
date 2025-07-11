package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record readStatusRequest(
        @NotNull  String messageId
) {
}
