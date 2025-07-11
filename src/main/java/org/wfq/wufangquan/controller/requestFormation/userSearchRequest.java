package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record userSearchRequest(
        @NotNull String keyword
) {
}
