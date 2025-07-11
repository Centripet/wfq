package org.wfq.wufangquan.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.wfq.wufangquan.entity.Messages;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Centripet
 * @since 2025-05-18
 */
@Mapper
public interface MessagesMapper extends BaseMapper<Messages> {


    @Select("""
            SELECT * FROM Users AS t1 LEFT JOIN TxlDepartments AS t2
            ON t1.Organization=t2.DepartmentID\s
            WHERE t1.Organization IS NOT NULL
            ORDER BY paixu
    """)
    List<Map<String, Object>> queryDepartmentWithUsers();


}
