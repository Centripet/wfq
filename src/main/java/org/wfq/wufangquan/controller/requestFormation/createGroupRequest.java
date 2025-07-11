package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record createGroupRequest(
        @NotNull String group_name,
        List<String> user_ids
) {
}
