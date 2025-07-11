package org.wfq.wufangquan.controller.requestFormation;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record modifyCircleRequest(
        @NotNull String circle_id,
        String title,
        String content,
        Boolean is_public,
        List<uploadSubmitRequest> add_files
) {
}
