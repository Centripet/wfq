package org.wfq.wufangquan.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wfq.wufangquan.controller.requestFormation.addFriendRequest;
import org.wfq.wufangquan.controller.requestFormation.createGroupRequest;
import org.wfq.wufangquan.controller.requestFormation.friendRequestHandleRequest;
import org.wfq.wufangquan.entity.regen.WFriend;
import org.wfq.wufangquan.entity.regen.WFriendRequest;
import org.wfq.wufangquan.entity.regen.WGroup;
import org.wfq.wufangquan.entity.regen.WGroupMember;
import org.wfq.wufangquan.mapper.WFriendMapper;
import org.wfq.wufangquan.mapper.WFriendRequestMapper;
import org.wfq.wufangquan.mapper.WGroupMapper;
import org.wfq.wufangquan.mapper.WGroupMemberMapper;
import org.wfq.wufangquan.service.IWFriendRequestService;

import java.time.LocalDateTime;
import java.util.List;

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
public class WFriendRequestServiceImpl extends ServiceImpl<WFriendRequestMapper, WFriendRequest> implements IWFriendRequestService {
    private final WFriendRequestMapper wFriendRequestMapper;
    private final WFriendMapper wFriendMapper;
    private final WGroupMapper wGroupMapper;
    private final WGroupMemberMapper wGroupMemberMapper;

    @Override
    public boolean addFriend(String user_id, addFriendRequest request) {
        String UUID = generateUUID();
        WFriendRequest wFriendRequest = WFriendRequest.builder()
                .request_id(UUID)
                .sender_id(user_id)
                .receiver_id(request.user_id())
                .message(request.message())
                .status(0)
                .create_time(LocalDateTime.now())
                .build();

        return wFriendRequestMapper.insert(wFriendRequest) >= 1;
    }

    @Override
    public List<WFriendRequest> getFriendRequestList(String user_id) {
        QueryWrapper<WFriendRequest> wrapper = new QueryWrapper<>();
        wrapper.eq("receiver_id", user_id);
        return wFriendRequestMapper.selectList(wrapper);
    }

    @Override
    public List<WFriendRequest> getFriendSendList(String user_id) {
        QueryWrapper<WFriendRequest> wrapper = new QueryWrapper<>();
        wrapper.eq("sender_id", user_id);
        return wFriendRequestMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public boolean friendRequestHandle(String user_id, friendRequestHandleRequest request) {
        if (request.status() == 1 || request.status() == 2) {
            WFriendRequest fRequest = wFriendRequestMapper.selectOne(
                    new QueryWrapper<WFriendRequest>()
                            .eq("request_id", request.request_id())
                            .eq("receiver_id", user_id)
            );

            UpdateWrapper<WFriendRequest> wrapper = new UpdateWrapper<>();
            wrapper.eq("request_id", request.request_id())
                    .set("status", request.status());
            this.update(wrapper);

            if (request.status() == 1) {
                return
                        wFriendMapper.insert(WFriend.builder()
                                .user_id(user_id)
                                .friend_id(fRequest.getSender_id())
                                .create_time(LocalDateTime.now())
                                .build()) +
                        wFriendMapper.insert(WFriend.builder()
                                .user_id(fRequest.getSender_id())
                                .friend_id(user_id)
                                .create_time(LocalDateTime.now())
                                .build())
                        >= 1;

            }

            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public String createGroup(String userId, createGroupRequest request) {
        String UUID = generateUUID();
        wGroupMapper.insert(
                WGroup.builder()
                .group_id(UUID)
                .creator_id(userId)
                .group_name(request.group_name())
                .group_owner(userId)
                .create_time(LocalDateTime.now())
                .build()
        );
        wGroupMemberMapper.insert(
                WGroupMember.builder()
                        .group_id(UUID)
                        .user_id(userId)
                        .group_role(3)
                        .joined_at(LocalDateTime.now())
                        .build()
        );
        for (String id : request.user_ids()) {
            wGroupMemberMapper.insert(
                    WGroupMember.builder()
                            .group_id(UUID)
                            .user_id(id)
                            .group_role(1)
                            .joined_at(LocalDateTime.now())
                            .build()
            );
        }

        return UUID;
    }

}
