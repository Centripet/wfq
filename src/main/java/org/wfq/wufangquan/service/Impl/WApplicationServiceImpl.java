package org.wfq.wufangquan.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wfq.wufangquan.controller.requestFormation.installInfoRequest;
import org.wfq.wufangquan.controller.requestFormation.installStatusRequest;
import org.wfq.wufangquan.entity.regen.WApplication;
import org.wfq.wufangquan.entity.regen.WApplicationUser;
import org.wfq.wufangquan.mapper.WApplicationMapper;
import org.wfq.wufangquan.mapper.WApplicationUserMapper;
import org.wfq.wufangquan.service.IWApplicationService;

import java.util.HashMap;
import java.util.Map;

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
public class WApplicationServiceImpl extends ServiceImpl<WApplicationMapper, WApplication> implements IWApplicationService {

    private final WApplicationMapper wApplicationMapper;
    private final WApplicationUserMapper wApplicationUserMapper;

    @Override
    public Map<String, Object> installInfo(String userId, installInfoRequest request) {
        return wApplicationUserMapper.installInfo(userId, request);
    }

    @Override
    public Map<String, Object> installStatus(String userId, installStatusRequest request) {
        return wApplicationUserMapper.installStatus(userId, request);
    }
}
