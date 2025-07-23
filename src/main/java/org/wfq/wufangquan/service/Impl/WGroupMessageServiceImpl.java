package org.wfq.wufangquan.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.wfq.wufangquan.controller.requestFormation.messageHistoryRequest;
import org.wfq.wufangquan.entity.regen.*;
import org.wfq.wufangquan.entity.res.TGroupMessage;
import org.wfq.wufangquan.mapper.WFileMapper;
import org.wfq.wufangquan.mapper.WGroupMessageMapper;
import org.wfq.wufangquan.mapper.WGroupMessageReadMapper;
import org.wfq.wufangquan.service.IWGroupMessageService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.wfq.wufangquan.util.CaptchaGenerator.generateUUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Centripet
 * @since 2025-06-09
 */
@Service
@RequiredArgsConstructor
public class WGroupMessageServiceImpl extends ServiceImpl<WGroupMessageMapper, WGroupMessage> implements IWGroupMessageService {

    private final WGroupMessageMapper groupMessageMapper;
    private final WGroupMessageReadMapper groupMessageReadMapper;
    private final WFileMapper fileMapper;

    @Override
    @Async
    public void newGroupMessage(WGroupMessage groupMessage) {

        String UUID = generateUUID();

        groupMessage.setMessage_id(UUID);

        groupMessageMapper.insert(groupMessage);

    }

    @Override
    public List<SUser> getGroupMembers(String groupId) {
        return groupMessageMapper.getGroupMembers(groupId);
    }

    @Override
    public List<TGroupMessage> sessionList(String userId) {
        return groupMessageMapper.sessionList(userId);
    }

    @Override
    @Async
    public void addNewReadMessage(WGroupMessageRead gmr) {
        String UUID = generateUUID();

        gmr.setMessage_id(UUID);
        groupMessageReadMapper.insert(gmr);
    }

    @Override
    public List<Map<String, Object>> messageHistory(String userId, messageHistoryRequest request) {
        List<Map<String, Object>> mh = groupMessageMapper.messageHistory(userId, request.uuid(), request.size(), (request.page() - 1) * request.size());
        Set<Object> fileIds = mh.stream()
                .map(m -> m.get("file_id"))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (!fileIds.isEmpty()) {
            // 一次性查询所有 WFile
            List<WFile> files = fileMapper.selectList(
                    new QueryWrapper<WFile>().in("file_id", fileIds)
            );

            // 将查询结果转为 Map<file_id, WFile>
            Map<String, WFile> fileMap = files.stream()
                    .collect(Collectors.toMap(WFile::getFile_id, Function.identity()));

            // 将 WFile 放入原始 map 中
            for (Map<String, Object> map : mh) {
                Object fileId = map.get("file_id");
                if (fileId != null) {
                    WFile file = fileMap.get(fileId.toString());
                    if (file != null) {
                        map.put("file", file);
                    }
                }
            }
        }
        return mh;
    }


}
