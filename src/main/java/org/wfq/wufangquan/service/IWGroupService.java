package org.wfq.wufangquan.service;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Centripet
 * @since 2025-06-09
 */
public interface IWGroupService {
    Map<String, Object> groupWithMemberInfo(String group_id);
}
