package org.wfq.wufangquan.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wfq.wufangquan.controller.requestFormation.circleListRequest;
import org.wfq.wufangquan.controller.requestFormation.circlePublishRequest;
import org.wfq.wufangquan.controller.requestFormation.modifyCircleRequest;
import org.wfq.wufangquan.entity.regen.*;
import org.wfq.wufangquan.mapper.*;
import org.wfq.wufangquan.service.AliOssService;
import org.wfq.wufangquan.service.IWCircleService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.wfq.wufangquan.util.CaptchaGenerator.generateUUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Centripet
 * @since 2025-06-17
 */
@Service
@RequiredArgsConstructor
public class WCircleServiceImpl extends ServiceImpl<WCircleMapper, WCircle> implements IWCircleService {

    private final WCircleMapper wCircleMapper;
    private final WCircleFileMapper wCircleFileMapper;
    private final WCircleViewMapper wCircleViewMapper;
    private final WCircleGroupMapper wCircleGroupMapper;
    private final WCircleProjectMapper wCircleProjectMapper;
    private final WFileMapper wFileMapper;
    private final WUserMapper wUserMapper;
    private final AliOssService aliOssService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> circlePublish(String userId, circlePublishRequest request, List<WFile> wFiles) {
        String UUID = generateUUID();
        LocalDateTime nowTime = LocalDateTime.now();
        WCircle wCircle = WCircle.builder()
                .circle_id(UUID)
                .title(request.title())
                .content(request.content())
                .is_public(request.is_public())
                .is_deleted(false)
                .view_count(0)
                .create_time(nowTime)
                .type(request.type())
                .publisher(userId)
                .build();
        wCircleMapper.insert(wCircle);

        for (String group_id:request.group_ids()) {
            wCircleGroupMapper.insert(
                    WCircleGroup.builder()
                            .circle_id(UUID)
                            .group_id(group_id)
                            .create_time(nowTime)
                            .build()
            );
        }

        for (String project_id:request.project_ids()) {
            wCircleProjectMapper.insert(
                    WCircleProject.builder()
                            .circle_id(UUID)
                            .project_id(project_id)
                            .create_time(nowTime)
                            .build()
            );
        }

        if (wFiles != null) {
            for (WFile wFile: wFiles) {
                wCircleFileMapper.insert(
                        WCircleFile.builder()
                                .circle_id(UUID)
                                .file_id(wFile.getFile_id())
                                .create_time(nowTime)
                                .build()
                );
            }
        }

        return Map.of(
                "circle_id", UUID,
                "create_time", nowTime.toString()
        );
    }

    @Override
    public List<WCircle> circleList(circleListRequest request, boolean isLogin) {

        int offset = (request.page() - 1) * request.size();

        return wCircleMapper.circleList(request, offset, isLogin);
    }

    @Override
    public Map<String, Object> circleDetail(String user_id, String circle_id) {
//        QueryWrapper<> = new QueryWrapper<>();
        WCircle circle = wCircleMapper.selectOne(
                new QueryWrapper<WCircle>()
                        .eq("circle_id", circle_id)
                        .eq("is_deleted", false)
        );
        if (circle == null) {
            return null;
        }
        String publisher_id = circle.getPublisher();
        Map<String, Object> circleMap = BeanUtil.beanToMap(circle);

        List<WFile>  files = wFileMapper.selectList(
                            new QueryWrapper<WFile>()
                                    .eq("circle_id", circle_id)
                    );

        files = aliOssService.generateUrlForEntity(files);

        SUser publisher = BeanUtil.copyProperties(
                wUserMapper.selectOne(
                        new QueryWrapper<WUser>()
                                .eq("user_id", publisher_id)
                ),
                SUser.class
        );

        circleMap.put("files", files);
        circleMap.put("publisher", publisher);

        return circleMap;
    }

    @Override
    public boolean modifyCircle(String userId, modifyCircleRequest request, List<WFile> wFiles) {
        boolean exists = wCircleMapper.exists(
                new QueryWrapper<WCircle>()
                        .eq("circle_id", request.circle_id())
                        .eq("publisher", userId)
                        .eq("is_deleted", false)
        );

        if (!exists) {
            return false;
        }

        LocalDateTime nowTime = LocalDateTime.now();

        UpdateWrapper<WCircle> updateWrapper = new UpdateWrapper<WCircle>()
                .eq("circle_id", request.circle_id());

        // 动态构建更新字段
        if (request.title() != null) {
            updateWrapper.set("title", request.title());
        }
        if (request.content() != null) {
            updateWrapper.set("content", request.content());
        }
        if (request.is_public() != null) {
            updateWrapper.set("is_public", request.is_public());
        }
        updateWrapper.set("update_time", nowTime);
        if (!(updateWrapper.getSqlSet() == null || updateWrapper.getSqlSet().isEmpty())) {
            wCircleMapper.update(null, updateWrapper);
        }

        if (wFiles != null) {
            for (WFile wFile: wFiles) {
                wCircleFileMapper.insert(
                        WCircleFile.builder()
                                .circle_id(request.circle_id())
                                .file_id(wFile.getFile_id())
                                .create_time(nowTime)
                                .build()
                );
            }
        }

        return true;
    }

    @Override
    public WCircle circleInfo(String user_id, String circle_id) {
        WCircle circle = wCircleMapper.selectOne(
                new QueryWrapper<WCircle>()
                        .eq("circle_id", circle_id)
                        .eq("is_deleted", false)
        );
        if (circle == null) {
            return null;
        }
//        Map<String, Object> circleMap = BeanUtil.beanToMap(circle);

        return circle;
    }

    @Override
    public boolean deleteCircle(String userId, String circle_id) {
        if (
                wCircleMapper.exists(
                        new QueryWrapper<WCircle>()
                                .eq("circle_id", circle_id)
                                .eq("publisher", userId)
                                .eq("is_deleted", false)
                )
        ) {
            wCircleMapper.update(
                    new UpdateWrapper<WCircle>()
                            .eq("circle_id", circle_id)
                            .set("is_deleted", true)
            );
        } else {
            return false;
        }

        return true;
    }


}
