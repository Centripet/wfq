package org.wfq.wufangquan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wfq.wufangquan.controller.requestFormation.*;
import org.wfq.wufangquan.entity.JwtPayload;
import org.wfq.wufangquan.entity.regen.WAnnotation;
import org.wfq.wufangquan.entity.regen.WFile;
import org.wfq.wufangquan.entity.regen.WProjectTask;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponse;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Centripet
 * @since 2025-08-03
 */
public interface IWProjectTaskService extends IService<WProjectTask> {

    WProjectTask createTask(String user_id, createTaskRequest request, List<WFile> wFiles);

    WAnnotation taskAnnotation(String userId, taskAnnotationRequest request);

    ApiResponse<?> task_status(JwtPayload payload, taskStatusRequest request);

    List<Map<String, Object>> task_history(taskHistoryRequest request);

    boolean taskDelete(taskDeleteRequest request);

    boolean taskModify(String userId, taskModifyRequest request, List<WFile> wFiles);

    Map<String, Object> taskDetail(taskDetailRequest request);

    ApiResponse<?> taskList(taskListRequest request);


}
