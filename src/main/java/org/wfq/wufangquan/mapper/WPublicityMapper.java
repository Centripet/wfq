package org.wfq.wufangquan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.wfq.wufangquan.entity.regen.WFile;
import org.wfq.wufangquan.entity.regen.WPublicity;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Centripet
 * @since 2025-06-21
 */
@Mapper
public interface WPublicityMapper extends BaseMapper<WPublicity> {

    @Select("""
        SELECT * FROM w_publicity AS a
        LEFT JOIN w_file AS b
        ON a.pub_id=b.file_id
        LIMIT #{limit} OFFSET #{offset};
""")
    List<WFile> pubList(
            @Param("limit") int limit,
            @Param("offset") int offset
    );
}
