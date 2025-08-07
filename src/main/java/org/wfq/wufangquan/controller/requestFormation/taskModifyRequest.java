package org.wfq.wufangquan.controller.requestFormation;

import java.time.LocalDateTime;
import java.util.List;

public record taskModifyRequest(
        String task_id,
        String title,
        String task_name,
        String description,
        Integer priority,
        LocalDateTime deadline,
        String assignee_id,
        List<uploadSubmitRequest> files
) {
}
