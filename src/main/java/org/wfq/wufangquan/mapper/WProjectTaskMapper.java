package org.wfq.wufangquan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.wfq.wufangquan.entity.regen.WProjectTask;

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
public interface WProjectTaskMapper extends BaseMapper<WProjectTask> {


    @Select("""
        SELECT * FROM
        w_project_task
        WHERE project_id=#{str}
        LIMIT #{limit} OFFSET #{offset};
""")
    List<Map<String, Object>> taskListForProject(String str, Integer limit, Integer offset);

    @Select("""
        SELECT * FROM
        w_project_task
        WHERE assignee_id=#{str}
        LIMIT #{limit} OFFSET #{offset};
""")
    List<Map<String, Object>> taskListForAssignee(String str, Integer limit, Integer offset);

    @Select("""
        SELECT * FROM
        w_project_task
        WHERE status=#{str}
        LIMIT #{limit} OFFSET #{offset};
""")
    List<Map<String, Object>> taskListForStatus(int str, Integer limit, Integer offset);



}
