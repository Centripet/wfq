package org.wfq.wufangquan.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wfq.wufangquan.entity.regen.WApplication;
import org.wfq.wufangquan.mapper.WApplicationMapper;
import org.wfq.wufangquan.service.IWApplicationService;

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

}
