package org.wfq.wufangquan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.wfq.wufangquan.controller.requestFormation.circleListRequest;
import org.wfq.wufangquan.entity.regen.WCircle;
import org.wfq.wufangquan.mapper.SqlProvider.circleListProvider;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Centripet
 * @since 2025-06-17
 */
@Mapper
public interface WCircleMapper extends BaseMapper<WCircle> {

    @SelectProvider(type = circleListProvider.class, method = "buildQuery")
    List<WCircle> circleList(
            @Param("req") circleListRequest req,
            @Param("offset") int offset,
            @Param("isLogin") boolean isLogin
    );

}
