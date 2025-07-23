package org.wfq.wufangquan.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.wfq.wufangquan.controller.requestFormation.messageHistoryRequest;
import org.wfq.wufangquan.entity.Messages;
import org.wfq.wufangquan.entity.regen.WFile;
import org.wfq.wufangquan.entity.regen.WMessage;
import org.wfq.wufangquan.entity.res.TMessage;
import org.wfq.wufangquan.mapper.WFileMapper;
import org.wfq.wufangquan.mapper.WGroupMessageMapper;
import org.wfq.wufangquan.mapper.WMessageMapper;
import org.wfq.wufangquan.service.IWMessageService;

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
public class WMessageServiceImpl extends ServiceImpl<WMessageMapper, WMessage> implements IWMessageService {

    private final WMessageMapper messageMapper;
    private final WFileMapper fileMapper;

    @Override
    @Async
    public void newMessage(WMessage message) {

        String UUID = generateUUID();

        message.setMessage_id(UUID);

        messageMapper.insert(message);

    }

    @Override
    public List<TMessage> sessionList(String userId) {
        return messageMapper.sessionList(userId);
    }

    @Override
    public List<Map<String, Object>> messageHistory(String userId, messageHistoryRequest request) {
        List<Map<String, Object>> mh = messageMapper.messageHistory(userId, request.uuid(), request.size(), (request.page() - 1) * request.size());

//        for (Map<String, Object> map : mh) {
//            if (map.get("file_id") != null) {
//                WFile file = fileMapper.selectOne(
//                        new QueryWrapper<WFile>()
//                                .eq("file_id", map.get("file_id"))
//                );
//                if (file != null) {
//                    map.put("file", file);
//                }
//            }
//        }
// 收集所有不为 null 的 file_id

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
