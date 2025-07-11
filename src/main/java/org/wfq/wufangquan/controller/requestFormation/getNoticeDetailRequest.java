package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record getNoticeDetailRequest(
        @NotNull String notice_id
) {
}
