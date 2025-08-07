package org.wfq.wufangquan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wfq.wufangquan.controller.requestFormation.projectOverviewRequest;
import org.wfq.wufangquan.controller.requestFormation.taskAxisRequest;
import org.wfq.wufangquan.entity.regen.WProject;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Centripet
 * @since 2025-07-12
 */
public interface IWProjectService extends IService<WProject> {

    Map<String, Object> taskAxis(taskAxisRequest request);

    Map<String, Object> projectOverview(projectOverviewRequest request);

}
