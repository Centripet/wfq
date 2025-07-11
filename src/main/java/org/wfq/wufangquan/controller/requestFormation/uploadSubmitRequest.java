package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record uploadSubmitRequest(
        @NotNull String key,
        @NotNull String suffix,
        @NotNull String origin_name,
        int type,
        String title,
        String info
        ) {
}
