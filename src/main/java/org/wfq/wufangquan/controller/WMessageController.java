package org.wfq.wufangquan.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wfq.wufangquan.controller.requestFormation.*;
import org.wfq.wufangquan.entity.JwtPayload;
import org.wfq.wufangquan.entity.regen.WFriendRequest;
import org.wfq.wufangquan.service.IWFriendRequestService;
import org.wfq.wufangquan.service.IWGroupMessageService;
import org.wfq.wufangquan.service.IWGroupService;
import org.wfq.wufangquan.service.IWMessageService;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponse;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponseWrap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class WMessageController {

    private final IWMessageService messageService;
    private final IWGroupMessageService groupMessageService;
    private final IWFriendRequestService friendRequestService;
    private final IWGroupService groupService;

    @PostMapping("/addFriend")
    @ApiResponseWrap
    public ApiResponse<?> addFriend(
            @RequestBody addFriendRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        if (!friendRequestService.addFriend(user_id, request)) {
            return ApiResponse.fail(500, "发送失败");
        };

        return ApiResponse.success("发送好友请求成功");
    }


    @PostMapping("/getFriendRequestList")
    @ApiResponseWrap
    public List<WFriendRequest> getFriendRequestList(
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        return friendRequestService.getFriendRequestList(user_id);
    }

    @PostMapping("/getFriendSendList")
    @ApiResponseWrap
    public List<WFriendRequest> getFriendSendList(
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        return friendRequestService.getFriendSendList(user_id);
    }

    @PostMapping("/friendRequestHandle")
    @ApiResponseWrap
    public ApiResponse<?> friendRequestHandle(
            @RequestBody friendRequestHandleRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        if (!friendRequestService.friendRequestHandle(user_id, request)) {
            return ApiResponse.fail(500, "处理失败");
        }
        return ApiResponse.success("处理成功");
    }

    @PostMapping("/createGroup")
    @ApiResponseWrap
    public ApiResponse<?> createGroup(
            @RequestBody createGroupRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        return ApiResponse.success(Map.of("group_id", friendRequestService.createGroup(user_id, request)));
    }

    @PostMapping("/sessionList")
    @ApiResponseWrap
    public ApiResponse<?> sessionList(
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        return ApiResponse.success(Map.of(
                "message", messageService.sessionList(user_id),
                "group", groupMessageService.sessionList(user_id)
        ));
    }

    @PostMapping("/messageHistory")
    @ApiResponseWrap
    public ApiResponse<?> messageHistory(
            @RequestBody messageHistoryRequest req,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtPayload payload = (JwtPayload) authentication.getPrincipal();
        String user_id = payload.getUser_id();

        messageHistoryRequest request = new messageHistoryRequest(
                req.method(),
                req.uuid(),
                (req.page() == null) ? 1 : req.page(),
                (req.size() == null) ? 10 : req.size()
        );
        System.out.println(request);
        return switch (request.method()) {
            case "receiver" -> ApiResponse.success(messageService.messageHistory(user_id, request));
            case "group" -> ApiResponse.success(groupMessageService.messageHistory(user_id, request));
            default -> ApiResponse.fail(400, "未知操作");
        };

    }

    @PostMapping("/groupInfo")
    @ApiResponseWrap
    public ApiResponse<?> groupInfo(
            @RequestBody groupInfoRequest request,
            HttpServletResponse response
    ) {
        return ApiResponse.success(groupService.groupWithMemberInfo(request.group_id()));
    }

}
