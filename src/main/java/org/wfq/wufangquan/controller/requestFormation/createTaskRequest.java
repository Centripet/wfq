package org.wfq.wufangquan.controller.requestFormation;

import java.time.LocalDateTime;
import java.util.List;

public record createTaskRequest(
        String project_id,
        String stage_id,
        String title,
        String task_name,
        String description,
        int priority,
        LocalDateTime deadline,
        String assignee_id,
        List<uploadSubmitRequest> files
) {
}
