package org.wfq.wufangquan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wfq.wufangquan.controller.requestFormation.installInfoRequest;
import org.wfq.wufangquan.controller.requestFormation.installStatusRequest;
import org.wfq.wufangquan.entity.regen.WApplication;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Centripet
 * @since 2025-07-12
 */
public interface IWApplicationService extends IService<WApplication> {

    Map<String, Object> installInfo(String userId, installInfoRequest request);

    Map<String, Object> installStatus(String userId, installStatusRequest request);

}
