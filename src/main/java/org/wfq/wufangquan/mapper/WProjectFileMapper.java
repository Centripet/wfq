package org.wfq.wufangquan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.wfq.wufangquan.entity.regen.WProjectFile;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Centripet
 * @since 2025-07-12
 */
@Mapper
public interface WProjectFileMapper extends BaseMapper<WProjectFile> {

    @Select("""
SELECT
t2.file_id,
t2.create_time,
t2.title,
t2.info,
t2.origin_name,
t2.uploader,
t2.is_public_read
FROM w_project_file AS t1
LEFT JOIN w_file AS t2
ON t1.file_id=t2.file_id
WHERE t1.task_id=#{task_id}
""")
    List<WProjectFile> getProjectFiles(String task_id);

}
