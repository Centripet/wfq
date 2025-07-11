package org.wfq.wufangquan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.wfq.wufangquan.controller.requestFormation.addMomentRequest;
import org.wfq.wufangquan.controller.requestFormation.getMomentsRequest;
import org.wfq.wufangquan.controller.requestFormation.idMomentRequest;
import org.wfq.wufangquan.controller.requestFormation.idUserRequest;
import org.wfq.wufangquan.entity.Moment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 动态 服务类
 * </p>
 *
 * @author Centripet
 * @since 2025-05-23
 */
public interface IMomentService extends IService<Moment> {

    boolean addMoment(String userId, addMomentRequest request);

    boolean deleteMoment(String userId, idMomentRequest request);

    boolean setReadMoment(String userId, idMomentRequest request);

    boolean setFavoriteMoment(String userId, idMomentRequest request, boolean favorite);

    IPage<Moment> getMomentsByUserId(getMomentsRequest request);

    Map<String,String> getUserInfoByUserId(idUserRequest request);


}
