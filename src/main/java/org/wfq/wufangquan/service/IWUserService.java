package org.wfq.wufangquan.service;
import com.baomidou.mybatisplus.extension.service.IService;
import org.wfq.wufangquan.controller.requestFormation.loginRequest;
import org.wfq.wufangquan.controller.requestFormation.registerRequest;
import org.wfq.wufangquan.controller.requestFormation.resetPasswordRequest;
import org.wfq.wufangquan.entity.regen.SUser;
import org.wfq.wufangquan.entity.regen.WUser;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Centripet
 * @since 2025-05-11
 */
public interface IWUserService extends IService<WUser> {
    WUser loginVerification(loginRequest request);

    List<WUser> findUsersByUserPhone(String phone);
    List<WUser> findUsersByUserAccount(String account);
    List<WUser> findUsersByUserId(String userId);

    boolean userExists(registerRequest request);
    boolean registerService(registerRequest request);
    boolean resetPassword(resetPasswordRequest request, String phone);

    List<SUser> userSearch(String keyword);

    SUser userInfo(String user_id);

}