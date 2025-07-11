package org.wfq.wufangquan.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.wfq.wufangquan.controller.requestFormation.messageHistoryRequest;
import org.wfq.wufangquan.entity.Messages;
import org.wfq.wufangquan.mapper.MessagesMapper;
import org.wfq.wufangquan.service.IMessagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.wfq.wufangquan.util.CaptchaGenerator.generateUUID;
import static org.wfq.wufangquan.util.ReflectUtils.getInitial;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Centripet
 * @since 2025-05-18
 */
@Service
@RequiredArgsConstructor
public class MessagesServiceImpl extends ServiceImpl<MessagesMapper, Messages> implements IMessagesService {

    private final MessagesMapper messagesMapper;

    @Override
    @Async
    public void newMessage(Messages messages) {

        String UUID = generateUUID();
        messages.setMessageID(UUID);
        messagesMapper.insert(messages);

    }

    @Override
    public IPage<Messages> getMessagesByPage(String userId, messageHistoryRequest request) {
//        Page<Messages> pageRequest = new Page<>(request.page(), request.size());
//        QueryWrapper<Messages> queryWrapper = new QueryWrapper<>();
//
//        queryWrapper.and(wrapper -> wrapper
//                .eq("SenderID", userId).eq("ReceiverID", request.receiverId())
//                .or()
//                .eq("SenderID", request.receiverId()).eq("ReceiverID", userId)
//        );
//
//        return messagesMapper.selectPage(pageRequest, queryWrapper);
        return null;
    }

    @Override
    @Async
    public void setReadStatus(String messageId, boolean status) {
        UpdateWrapper<Messages> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("MessageID", messageId)
                .set("ReadStatus", status ? 1 : 0);
        this.update(updateWrapper);
    }

    @Override
    public List<Map<String, Object>> getDepartmentsWithUsers() {
        List<Map<String, Object>> rows = messagesMapper.queryDepartmentWithUsers();

        Map<String, Map<String, Object>> deptMap = new LinkedHashMap<>();

        for (Map<String, Object> row : rows) {
            String deptId = String.valueOf(row.get("DepartmentID"));

            // 如果部门尚未添加，先构造部门 Map
            if (!deptMap.containsKey(deptId)) {
                Map<String, Object> dept = new LinkedHashMap<>();
                dept.put("DepartmentID", deptId);
                dept.put("Prefix", row.get("Prefix"));
                dept.put("DepartmentName", row.get("Name"));
                dept.put("Color", row.get("Color"));
                dept.put("Users", new ArrayList<Map<String, Object>>());
                deptMap.put(deptId, dept);
            }

            // 构造用户 Map
            Map<String, Object> user = new LinkedHashMap<>();
            user.put("userId", row.get("userid"));
            user.put("Username", row.get("Username"));
            user.put("NickName", row.get("NickName"));
            user.put("PhoneNumber", row.get("Phone"));
            user.put("ExtensionNumber", row.get("AxnExtensionNum"));
            user.put("Icon", row.get("Icon"));
            user.put("NameInitials", getInitial((String) row.get("Username")));
            user.put("DepartmentID", deptId);

            // 添加到对应部门下的 Users 列表
            List<Map<String, Object>> users = (List<Map<String, Object>>) deptMap.get(deptId).get("Users");
            users.add(user);

        }

        return new ArrayList<>(deptMap.values());
    }

}
