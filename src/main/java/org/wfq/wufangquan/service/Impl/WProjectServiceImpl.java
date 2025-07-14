package org.wfq.wufangquan.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wfq.wufangquan.controller.requestFormation.taskAxisRequest;
import org.wfq.wufangquan.entity.regen.*;
import org.wfq.wufangquan.mapper.*;
import org.wfq.wufangquan.service.IWProjectService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Centripet
 * @since 2025-07-12
 */
@Service
@RequiredArgsConstructor
public class WProjectServiceImpl extends ServiceImpl<WProjectMapper, WProject> implements IWProjectService {

    private final WProjectMapper wProjectMapper;
    private final WProjectStageMapper wProjectStageMapper;
    private final WProjectTaskMapper wProjectTaskMapper;
    private final WProjectFileMapper wProjectFileMapper;
    private final WFileMapper wFileMapper;

    @Override
    public Map<String, Object> taskAxis(taskAxisRequest request) {

        WProject project = wProjectMapper.selectOne(
                new QueryWrapper<WProject>()
                        .eq("project_id", request.project_id())
        );
        Map<String, Object> mapProject = BeanUtil.beanToMap(project);

        List<WProjectStage> projectStages = wProjectStageMapper.selectList(
                new QueryWrapper<WProjectStage>()
                        .eq("project_id", request.project_id())
        );

        if (!projectStages.isEmpty()) {
            List<Map<String, Object>> mapStages =
                    projectStages.stream()
                            .map(BeanUtil::beanToMap)
                            .toList();

            for (Map<String, Object> stage : mapStages) {
                List<WProjectTask> projectTasks = wProjectTaskMapper.selectList(
                        new QueryWrapper<WProjectTask>()
                                .eq("stage_id", stage.get("stage_id"))
                );
                if (!projectTasks.isEmpty()) {
                    List<Map<String, Object>> mapTasks =
                            projectTasks.stream()
                                    .map(BeanUtil::beanToMap)
                                    .toList();
                    Map<String, Object> mapTask = BeanUtil.beanToMap(projectTasks);

                    for (Map<String, Object> task : mapTasks) {
                        List<WProjectFile> taskFiles = wProjectFileMapper
                                .getProjectFiles((String)task.get("task_id"));

                        if (!taskFiles.isEmpty()) {
                            task.put("file", taskFiles);
                        }
                    }

                    stage.put("task", mapTask);
                }
            }

            mapProject.put("stages", mapStages);
        }

        return mapProject;
    }


}
