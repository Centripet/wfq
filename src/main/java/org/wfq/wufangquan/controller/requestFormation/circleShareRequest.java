package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record circleShareRequest(
        @NotNull String circle_id,
        List<String> user_ids,
        List<String> group_ids
) {
}
