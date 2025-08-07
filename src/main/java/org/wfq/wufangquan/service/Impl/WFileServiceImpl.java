package org.wfq.wufangquan.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wfq.wufangquan.controller.requestFormation.uploadSubmitRequest;
import org.wfq.wufangquan.entity.regen.WFile;
import org.wfq.wufangquan.entity.res.uploadSubmit;
import org.wfq.wufangquan.mapper.WFileMapper;
import org.wfq.wufangquan.service.AliOssService;
import org.wfq.wufangquan.service.IWFileService;
import org.wfq.wufangquan.service.kkFileViewService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private final AliOssService ossService;
    private final kkFileViewService fileViewService;
    private final AliOssService aliOssService;

    @Override
    public String uploadSubmit(String userId, uploadSubmitRequest request, boolean is_public_read) {
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
                        .is_public_read(is_public_read)
                        .origin_name(request.origin_name())
                        .build()
        );
        return UUID;
    }

    @Override
    public WFile uploadSubmit(String userId, uploadSubmit upload, boolean is_public_read) {
        String UUID = generateUUID();
        WFile file = WFile.builder()
                .file_id(UUID)
                .uploader(userId)
                .file_key(upload.getKey())
                .title(upload.getTitle())
                .info(upload.getInfo())
                .type(upload.getType())
                .suffix(upload.getSuffix())
                .create_time(LocalDateTime.now())
                .is_public_read(is_public_read)
                .origin_name(upload.getOrigin_name())
                .build();
        wFileMapper.insert(file);
        return file;
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

    @Override
    public String fileGetUrl(Map<String, Object> file) {
        String file_key = (String) file.get("file_key");
        if ((Boolean) file.get("is_public_read")) {
            return ossService.generatePublicUrl(file_key);
        } else {
            return ossService.generateDownloadUrl(file_key, AliOssService.EXPIRE_TIME_HALF_HOUR);
        }

    }

    @Override
    public String filePreviewUrl(Map<String, Object> file) {
        String file_key = (String) file.get("file_key");
        if ((Boolean) file.get("is_public_read")) {
            String url = ossService.generatePublicUrl(file_key);
            System.out.println(url);
            return fileViewService.generateKkFilePreviewUrl(url);
        } else {
            String url = ossService.generateDownloadUrl(file_key, AliOssService.EXPIRE_TIME_TEN_MIN);
            System.out.println(url);
            return fileViewService.generateKkFilePreviewUrl(url);
        }

    }

    @Override
    public Map<String, Object> filePreview(String file_id) {
        Map<String, Object> file = this.getFileById(file_id);
        String url = this.fileGetUrl(file);
        file.put("oss_url", url);
        file.put("preview_url", fileViewService.generateKkFilePreviewUrl(url));
        return file;
    }

    @Override
    public List<WFile> generateUrlForEntity(List<WFile> files) {

        for (WFile file : files) {
            String oss_url = null;
            if (file.getIs_public_read()) {
                oss_url = ossService.generatePublicUrl(file.getFile_key());
                System.out.println(oss_url);
            } else {
                oss_url = ossService.generateDownloadUrl(file.getFile_key(), AliOssService.EXPIRE_TIME_ONE_HOUR);
                System.out.println(oss_url);
            }

            file.setOss_url(oss_url);
            file.setPreview_url(fileViewService.generateKkFilePreviewUrl(oss_url));
        }

        return files;
    }


}
