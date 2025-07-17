package org.wfq.wufangquan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.wfq.wufangquan.entity.regen.WCircleCollection;

import java.util.List;
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
public interface WCircleCollectionMapper extends BaseMapper<WCircleCollection> {

    @Select("""
SELECT
t2.circle_id,
t2.title,
t2.create_time,
t2.publisher
FROM w_circle_collection AS t1
LEFT JOIN w_circle AS t2
ON t1.circle_id=t2.circle_id
WHERE t1.user_id=#{userId}
LIMIT #{limit} OFFSET #{offset}
""")
    List<Map<String, Object>> collectionList(String userId, Integer size, int offset);

}
