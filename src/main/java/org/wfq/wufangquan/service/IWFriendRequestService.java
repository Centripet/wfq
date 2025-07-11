package org.wfq.wufangquan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wfq.wufangquan.controller.requestFormation.addFriendRequest;
import org.wfq.wufangquan.controller.requestFormation.createGroupRequest;
import org.wfq.wufangquan.controller.requestFormation.friendRequestHandleRequest;
import org.wfq.wufangquan.entity.regen.WFriendRequest;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponse;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Centripet
 * @since 2025-06-09
 */
public interface IWFriendRequestService extends IService<WFriendRequest> {

    boolean addFriend(String user_id, addFriendRequest request);

    List<WFriendRequest> getFriendRequestList(String user_id);

    List<WFriendRequest> getFriendSendList(String user_id);

    boolean friendRequestHandle(String user_id, friendRequestHandleRequest request);

    String createGroup(String userId, createGroupRequest request);

}
