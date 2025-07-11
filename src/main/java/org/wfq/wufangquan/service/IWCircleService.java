package org.wfq.wufangquan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wfq.wufangquan.controller.requestFormation.circleListRequest;
import org.wfq.wufangquan.controller.requestFormation.circlePublishRequest;
import org.wfq.wufangquan.controller.requestFormation.modifyCircleRequest;
import org.wfq.wufangquan.entity.regen.WCircle;
import org.wfq.wufangquan.entity.regen.WFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Centripet
 * @since 2025-06-17
 */
public interface IWCircleService extends IService<WCircle> {

    Map<String, String> circlePublish(String userId, circlePublishRequest request, List<WFile> wFils);

    List<WCircle> circleList(circleListRequest request, boolean isLogin);

    Map<String, Object> circleDetail(String user_id, String circle_id);

    boolean deleteCircle(String userId, String circle_id);

    boolean modifyCircle(String userId, modifyCircleRequest request, List<WFile> files);

    WCircle circleInfo(String user_id, String circle_id);


}
