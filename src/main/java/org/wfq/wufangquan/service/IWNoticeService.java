package org.wfq.wufangquan.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.wfq.wufangquan.controller.requestFormation.getNoticesRequest;
import org.wfq.wufangquan.controller.requestFormation.pubListRequest;
import org.wfq.wufangquan.entity.regen.WFile;
import org.wfq.wufangquan.entity.regen.WNotice;
import org.wfq.wufangquan.entity.regen.WPublicity;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Centripet
 * @since 2025-06-02
 */
public interface IWNoticeService extends IService<WNotice> {

    List<WNotice> getNotices(String user_id, getNoticesRequest request);

    Optional<WNotice> getNoticeDetail(String user_id, String notice_id);

    Optional<WNotice> getNoticeByNoticeId(String notice_id);

    boolean addPublicity(String fileId);

    List<WFile> pubList(String userId, pubListRequest request);
}
