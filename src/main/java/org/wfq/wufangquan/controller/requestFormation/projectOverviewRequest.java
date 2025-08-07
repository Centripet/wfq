package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record projectOverviewRequest(
    @NotNull String project_id
) {
}
