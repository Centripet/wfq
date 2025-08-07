package org.wfq.wufangquan.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wfq.wufangquan.controller.requestFormation.*;
import org.wfq.wufangquan.entity.JwtPayload;
import org.wfq.wufangquan.entity.regen.*;
import org.wfq.wufangquan.mapper.*;
import org.wfq.wufangquan.service.AliOssService;
import org.wfq.wufangquan.service.IWFileService;
import org.wfq.wufangquan.service.IWProjectTaskService;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponse;

import java.time.LocalDateTime;
import java.util.*;

import static org.wfq.wufangquan.util.CaptchaGenerator.generateUUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Centripet
 * @since 2025-08-03
 */
@Service
@RequiredArgsConstructor
public class WProjectTaskServiceImpl extends ServiceImpl<WProjectTaskMapper, WProjectTask> implements IWProjectTaskService {

    private final WProjectTaskMapper wProjectTaskMapper;
    private final WFileMapper wFileMapper;
    private final WProjectFileMapper wProjectFileMapper;
    private final WAnnotationMapper wAnnotationMapper;
    private final WTaskStatusMapper wTaskStatusMapper;
    private final WUserMapper wUserMapper;
    private final AliOssService ossService;
    private final IWFileService fileService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WProjectTask createTask(String user_id, createTaskRequest request, List<WFile> wFiles) {
        String UUID = generateUUID();
        LocalDateTime nowTime = LocalDateTime.now();

        WProjectTask task = WProjectTask.builder()
                .task_id(UUID)
                .project_id(request.project_id())
                .stage_id(request.stage_id())
                .task_name(request.task_name())
                .deadline(request.deadline())
                .description(request.description())
                .title(request.title())
                .assignee_id(request.assignee_id())
                .priority(request.priority())
                .create_time(nowTime)
                .task_status("PENDING_ACCEPTANCE")
                .status(0)
                .creator(user_id)
                .build();

        wProjectTaskMapper.insert(task);

        if (wFiles != null) {
            for (WFile wFile: wFiles) {
                wProjectFileMapper.insert(
                        WProjectFile.builder()
                                .task_id(UUID)
                                .file_id(wFile.getFile_id())
                                .create_time(nowTime)
                                .build()
                );
            }
        }

        return task;
    }

    @Override
    public WAnnotation taskAnnotation(String userId, taskAnnotationRequest request) {
        String UUID = generateUUID();
        WAnnotation annotation = WAnnotation.builder()
                .annotation_id(UUID)
                .type(request.type())
                .file_id(request.file_id())
                .content(request.content())
                .create_time(LocalDateTime.now())
                .user_id(userId)
                .coordinate(request.coordinate())
                .build();

        wAnnotationMapper.insert(annotation);

        return annotation;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<?> task_status(JwtPayload payload, taskStatusRequest request) {
        WProjectTask task = wProjectTaskMapper.selectOne(
                new QueryWrapper<WProjectTask>()
                        .eq("task_id", request.task_id())
        );
        if (task != null) {
            String UUID = generateUUID();
            WTaskStatus status = WTaskStatus.builder()
                    .status_id(UUID)
                    .task_id(request.task_id())
                    .action(request.action())
                    .department(payload.getDepartment())
                    .comment(request.comment())
                    .location(request.location())
                    .user_id(payload.getUser_id())
                    .create_time(LocalDateTime.now())
                    .build();
            String task_status = null;
            int base_status=0;

            switch (task.getTask_status()) {
                case "PENDING_ACCEPTANCE":
                case "PENDING_ACCEPTANCE_REJECT":
                    if (payload.getDepartment().equals("施工方")) {
                        if (request.location().isBlank()) {
                            return ApiResponse.fail(400,"施工方需要提供定位");
                        }

                        if (request.action().equals("accept")) {
                            task_status = "IN_PROGRESS";
                            status.setStatus(task_status);
                            base_status=1;
                        } else if (request.action().equals("reject")) {
                            task_status = "PENDING_ACCEPTANCE_REJECT";
                            status.setStatus(task_status);
                            base_status=3;
                        }
                    }
                    break;
                case "IN_PROGRESS":
                    if (payload.getDepartment().equals("施工方")) {
                        if (request.location().isBlank()) {
                            return ApiResponse.fail(400,"施工方需要提供定位");
                        }
                        if (request.action().equals("submit")) {
                            task_status = "PENDING_REVIEW";
                            status.setStatus(task_status);
                            base_status=1;
                        } else if (request.action().equals("extension")) {
                            task_status = "PENDING_EXTENSION";
                            status.setStatus(task_status);
                            base_status=1;
                        }
                    }
                    break;
                case "PENDING_EXTENSION":
                    if (payload.getDepartment().equals("监理方")) {
                        if (request.action().equals("accept")) {
                            task_status = "EXTENDED";
                            status.setStatus(task_status);
                            base_status=1;
                        } else if (request.action().equals("reject")) {
                            task_status = "IN_PROGRESS";
                            status.setStatus(task_status);
                            base_status=1;
                        }
                    }

                    break;
                case "PENDING_REVIEW":
                    if (payload.getDepartment().equals("监理方")) {
                        if (request.action().equals("accept")) {
                            task_status = "COMPLETED";
                            status.setStatus(task_status);
                            base_status=2;
                        } else if (request.action().equals("reject")) {
                            task_status = "IN_PROGRESS";
                            status.setStatus(task_status);
                            base_status=1;
                        }
                    }

                    break;
            }


            if (task_status!=null) {
                wTaskStatusMapper.insert(status);
                wProjectTaskMapper.update(
                        null,
                        new UpdateWrapper<WProjectTask>()
                                .set("task_status", task_status)
                                .set("status", base_status)
                                .eq("task_id", request.task_id())
                );
                return ApiResponse.success(status);
            }

            return ApiResponse.fail(400,"非对应操作部门或未知操作");
        }

        return ApiResponse.fail(400,"任务不存在");
    }

    @Override
    public List<Map<String, Object>> task_history(taskHistoryRequest request) {

        WProjectTask task = wProjectTaskMapper.selectOne(
                new QueryWrapper<WProjectTask>()
                        .eq("task_id", request.task_id())
        );
        if (task != null) {
            List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();

            Map<String, Object> creator = new HashMap<>();
            creator.put("action", "create");
            creator.put("create_time", task.getCreate_time());
            WUser user = wUserMapper.selectOne(
                    new QueryWrapper<WUser>()
                            .eq("user_id", task.getCreator())
            );
            creator.put("create_time", task.getCreate_time());
            creator.put("user_id", user.getUser_id());
            creator.put("account", user.getAccount());
            creator.put("name", user.getNick_name());

            res.add(creator);

            List<WTaskStatus> taskStatuses = wTaskStatusMapper.selectList(
                    new QueryWrapper<WTaskStatus>()
                            .eq("task_id", request.task_id())
            );

            user = null;

            if (!taskStatuses.isEmpty()) {
                List<Map<String, Object>> taskStatusesMap = taskStatuses.stream().map(BeanUtil::beanToMap).toList();
                for (Map<String, Object> taskStatusMap : taskStatusesMap) {
                    user = wUserMapper.selectOne(
                            new QueryWrapper<WUser>()
                                    .eq("user_id", taskStatusMap.get("user_id"))
                    );
                    System.out.println(user);
                    if (user != null) {
                        taskStatusMap.put("operator",
                                Map.of(
                                        "user_id", user.getUser_id(),
                                        "account", user.getAccount(),
                                        "name", Optional.ofNullable(user.getNick_name()).orElse("")
                                )
                        );
                    }

                    res.add(taskStatusMap);
                }
            }

            return res;
        }

        return List.of();
    }

    @Override
    public boolean taskDelete(taskDeleteRequest request) {

        WProjectTask task = wProjectTaskMapper.selectOne(
                new QueryWrapper<WProjectTask>()
                        .eq("task_id", request.task_id())
        );
        if (task != null) {
            if (task.getTask_status().equals("PENDING_ACCEPTANCE")) {
                return wProjectTaskMapper.delete(
                        new QueryWrapper<WProjectTask>()
                                .eq("task_id", request.task_id())
                ) > 0;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean taskModify(String userId, taskModifyRequest request, List<WFile> wFiles) {
        WProjectTask task = wProjectTaskMapper.selectOne(
                new QueryWrapper<WProjectTask>()
                        .eq("task_id", request.task_id())
        );
        if (task != null) {
            if (task.getTask_status().equals("PENDING_ACCEPTANCE")) {
                LocalDateTime nowTime = LocalDateTime.now();

                UpdateWrapper<WProjectTask> updateWrapper = new UpdateWrapper<WProjectTask>()
                        .eq("task_id", request.task_id());

                // 动态构建更新字段
                if (request.title() != null) {
                    updateWrapper.set("title", request.title());
                }
                if (request.task_name() != null) {
                    updateWrapper.set("task_name", request.task_name());
                }
                if (request.description() != null) {
                    updateWrapper.set("description", request.description());
                }
                if (request.priority() != null) {
                    updateWrapper.set("priority", request.priority());
                }
                if (request.deadline() != null) {
                    updateWrapper.set("deadline", request.deadline());
                }
                if (request.assignee_id() != null) {
                    updateWrapper.set("assignee_id", request.assignee_id());
                }
                updateWrapper.set("update_time", nowTime);

                if (!(updateWrapper.getSqlSet() == null || updateWrapper.getSqlSet().isEmpty())) {
                    wProjectTaskMapper.update(null, updateWrapper);
                }


                if (wFiles != null) {
                    for (WFile wFile: wFiles) {
                        wProjectFileMapper.insert(
                                WProjectFile.builder()
                                        .task_id(request.task_id())
                                        .file_id(wFile.getFile_id())
                                        .create_time(nowTime)
                                        .build()
                        );
                    }
                }

                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> taskDetail(taskDetailRequest request) {
        WProjectTask task = wProjectTaskMapper.selectOne(
                new QueryWrapper<WProjectTask>()
                        .eq("task_id", request.task_id())
        );
        if (task != null) {
            Map<String, Object> taskMap = BeanUtil.beanToMap(task);

            List<String> fileIds = wProjectFileMapper.selectList(
                    new QueryWrapper<WProjectFile>().eq("task_id", request.task_id())
            ).stream().map(WProjectFile::getFile_id).toList();

            List<WFile> files = wFileMapper.selectList(
                    new QueryWrapper<WFile>()
                            .in("file_id", fileIds)
            );

            System.out.println(files);

            files = fileService.generateUrlForEntity(files);

            taskMap.put("files", files);

            return taskMap;
        }

        return Map.of();
    }

    @Override
    public ApiResponse<?> taskList(taskListRequest request) {
        Integer offSet = (request.page() - 1) * request.size();
//        List<Map<String, Object>>
        return switch (request.method()) {
            case "project" -> ApiResponse.success(
                    wProjectTaskMapper.taskListForProject(request.str(), request.size(), offSet)
                    );
            case "assignee" -> ApiResponse.success(
                    wProjectTaskMapper.taskListForAssignee(request.str(), request.size(), offSet)
            );
            case "status" -> ApiResponse.success(
                    wProjectTaskMapper.taskListForStatus(Integer.parseInt(request.str()), request.size(), offSet)
            );
            default -> ApiResponse.fail(400, "未知操作");
        };

    }


}
