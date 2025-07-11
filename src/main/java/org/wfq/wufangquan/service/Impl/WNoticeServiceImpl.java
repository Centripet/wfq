package org.wfq.wufangquan.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wfq.wufangquan.controller.requestFormation.getNoticesRequest;
import org.wfq.wufangquan.controller.requestFormation.pubListRequest;
import org.wfq.wufangquan.entity.regen.WFile;
import org.wfq.wufangquan.entity.regen.WNotice;
import org.wfq.wufangquan.entity.regen.WPublicity;
import org.wfq.wufangquan.mapper.WNoticeMapper;
import org.wfq.wufangquan.mapper.WPublicityMapper;
import org.wfq.wufangquan.service.IWNoticeService;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Centripet
 * @since 2025-06-02
 */
@Service
@RequiredArgsConstructor
public class WNoticeServiceImpl extends ServiceImpl<WNoticeMapper, WNotice> implements IWNoticeService {

    private final WNoticeMapper wNoticeMapper;
    private final WPublicityMapper wPublicityMapper;

    @Override
    public List<WNotice> getNotices(String user_id, getNoticesRequest request) {

        return wNoticeMapper.getNoticesWithReadStatus(user_id, request.size(), (request.page() - 1) * request.size());
    }

    @Override
    public Optional<WNotice> getNoticeDetail(String user_id, String notice_id) {

        return wNoticeMapper.getNoticeByNoticeIdWithReadStatus(user_id, notice_id);
    }

    @Override
    public Optional<WNotice> getNoticeByNoticeId(String notice_id) {
        return this.lambdaQuery()
                .eq(WNotice::getNotice_id, notice_id)
                .oneOpt();
    }

    @Override
    public boolean addPublicity(String fileId) {
        return wPublicityMapper.insert(
                WPublicity.builder()
                        .pub_id(fileId)
                        .build()
        ) > 0;
    }

    @Override
    public List<WFile> pubList(String userId, pubListRequest request) {
        return wPublicityMapper.pubList(request.size(), (request.page() - 1) * request.size());
    }


}
