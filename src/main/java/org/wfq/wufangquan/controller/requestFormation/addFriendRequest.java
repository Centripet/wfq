package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record addFriendRequest(
        @NotNull String user_id,
        String message
) {
}
