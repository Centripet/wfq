package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record taskAxisRequest(
        @NotNull String project_id
) {
}
