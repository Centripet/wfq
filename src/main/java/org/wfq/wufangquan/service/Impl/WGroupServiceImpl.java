package org.wfq.wufangquan.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wfq.wufangquan.entity.regen.WGroup;
import org.wfq.wufangquan.entity.regen.WGroupMember;
import org.wfq.wufangquan.mapper.WGroupMapper;
import org.wfq.wufangquan.mapper.WGroupMemberMapper;
import org.wfq.wufangquan.service.IWGroupService;

import java.util.List;
import java.util.Map;

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
public class WGroupServiceImpl extends ServiceImpl<WGroupMapper, WGroup> implements IWGroupService {

    private final WGroupMapper wGroupMapper;
    private final WGroupMemberMapper wGroupMemberMapper;

    @Override
    public Map<String, Object> groupWithMemberInfo(String group_id) {

        WGroup group = wGroupMapper.selectOne(
                new QueryWrapper<WGroup>().eq("group_id", group_id)
        );

        QueryWrapper<WGroupMember> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(WGroupMember::getGroup_id, group_id);
        List<WGroupMember> groupMembers = wGroupMemberMapper.selectList(wrapper);

        Map<String, Object> groupMap = BeanUtil.beanToMap(group);
        groupMap.put("members", groupMembers);

        return groupMap;
    }


}
