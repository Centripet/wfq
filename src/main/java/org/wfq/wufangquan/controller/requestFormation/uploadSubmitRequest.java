package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

public record uploadSubmitRequest(
        String key,
        String suffix,
        String origin_name,
        int type,
        String title,
        String info
        ) {
}
