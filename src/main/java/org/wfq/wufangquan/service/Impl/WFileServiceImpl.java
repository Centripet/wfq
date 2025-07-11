package org.wfq.wufangquan.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wfq.wufangquan.controller.requestFormation.uploadSubmitRequest;
import org.wfq.wufangquan.entity.regen.WFile;
import org.wfq.wufangquan.mapper.WFileMapper;
import org.wfq.wufangquan.service.IWFileService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.wfq.wufangquan.util.CaptchaGenerator.generateUUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Centripet
 * @since 2025-06-21
 */
@Service
@RequiredArgsConstructor
public class WFileServiceImpl extends ServiceImpl<WFileMapper, WFile> implements IWFileService {

    private final WFileMapper wFileMapper;

    @Override
    public String uploadSubmit(String userId, uploadSubmitRequest request) {
        String UUID = generateUUID();
        wFileMapper.insert(
                WFile.builder()
                        .file_id(UUID)
                        .uploader(userId)
                        .file_key(request.key())
                        .title(request.title())
                        .info(request.info())
                        .type(request.type())
                        .suffix(request.suffix())
                        .create_time(LocalDateTime.now())
                        .is_public_read(false)
                        .origin_name(request.origin_name())
                        .build()
        );
        return UUID;
    }

    @Override
    public List<WFile> uploadSubmitMult(String userId, Map<uploadSubmitRequest, Boolean> keyMap) {
        List<WFile> wFiles = new ArrayList<>();
        for (uploadSubmitRequest key : keyMap.keySet()) {
            if (keyMap.get(key)) {
                WFile wFile = WFile.builder()
                        .file_id(generateUUID())
                        .uploader(userId)
                        .file_key(key.key())
                        .title(key.title())
                        .info(key.info())
                        .type(key.type())
                        .suffix(key.suffix())
                        .create_time(LocalDateTime.now())
                        .is_public_read(false)
                        .origin_name(key.origin_name())
                        .build();
                wFileMapper.insert(wFile);
                wFiles.add(wFile);
            }
        }
        return wFiles;
    }

    @Override
    public Map<String, Object> getFileById(String file_id) {
        WFile file = wFileMapper.selectOne(
                new QueryWrapper<WFile>()
                        .eq("file_id", file_id)
        );

        return BeanUtil.beanToMap(file);
    }
}
