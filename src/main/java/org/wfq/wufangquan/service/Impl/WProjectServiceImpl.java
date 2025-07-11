package org.wfq.wufangquan.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wfq.wufangquan.entity.regen.WProject;
import org.wfq.wufangquan.mapper.WProjectMapper;
import org.wfq.wufangquan.service.IWProjectService;

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

}
