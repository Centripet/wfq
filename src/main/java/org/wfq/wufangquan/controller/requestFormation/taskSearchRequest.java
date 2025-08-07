package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record taskSearchRequest(
        @NotNull String keyword,
        @NotNull String project_id
        ) {
}
