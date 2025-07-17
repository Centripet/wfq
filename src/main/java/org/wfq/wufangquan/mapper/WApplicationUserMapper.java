package org.wfq.wufangquan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.wfq.wufangquan.controller.requestFormation.installInfoRequest;
import org.wfq.wufangquan.controller.requestFormation.installStatusRequest;
import org.wfq.wufangquan.entity.regen.WApplicationUser;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Centripet
 * @since 2025-07-12
 */
@Mapper
public interface WApplicationUserMapper extends BaseMapper<WApplicationUser> {

    @Select("""
SELECT
user_id,
app_id,
download_status,
download_progress
FROM w_application_user
WHERE user_id=#{userId} AND app_id=#{request.app_id()}
LIMIT 1
""")
    Map<String, Object> installInfo(String userId, installInfoRequest request);
    @Select("""
SELECT
user_id,
app_id,
is_installed
last_open_time
FROM w_application_user
WHERE user_id=#{userId} AND app_id=#{request.app_id()}
LIMIT 1
""")
    Map<String, Object> installStatus(String userId, installStatusRequest request);
}
