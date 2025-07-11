package org.wfq.wufangquan.mapper.SqlProvider;

import org.apache.ibatis.annotations.Param;
import org.wfq.wufangquan.controller.requestFormation.circleListRequest;

public class circleListProvider {

    public String buildQuery(
            @Param("req") circleListRequest req,
            @Param("offset") int offset,
            @Param("isLogin") boolean isLogin
    ) {
        StringBuilder sql = new StringBuilder(
            """
            SELECT * FROM w_circle a
            WHERE a.is_deleted = 0
            """
        );

        if (req.project_id() != null && !req.project_id().isEmpty()) {
            sql.append("""
                AND EXISTS (
                    SELECT 1 FROM w_circle_project b
                    WHERE b.circle_id = a.circle_id AND b.project_id = #{req.project_id}
                )
            """);
        }

        if (!isLogin) {
            sql.append(" AND a.is_public = 1");
        }

        if (req.type() != null && req.type() != 0) {
            sql.append(" AND a.type = #{req.type}");
        }

        if (req.publisher() != null && !req.publisher().isEmpty()) {
            sql.append(" AND a.publisher = #{req.publisher}");
        }

        if ("hot".equalsIgnoreCase(req.sort())) {
            sql.append(" ORDER BY a.view_count DESC");
        } else {
            sql.append(" ORDER BY a.create_time DESC");
        }

        sql.append(" LIMIT #{req.size} OFFSET #{offset}");
        return sql.toString();
    }
}
