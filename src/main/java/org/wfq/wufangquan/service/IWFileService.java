package org.wfq.wufangquan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wfq.wufangquan.controller.requestFormation.filePreviewRequest;
import org.wfq.wufangquan.controller.requestFormation.uploadSubmitRequest;
import org.wfq.wufangquan.entity.regen.WFile;
import org.wfq.wufangquan.entity.res.uploadSubmit;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Centripet
 * @since 2025-06-21
 */
public interface IWFileService extends IService<WFile> {

    String uploadSubmit(String userId, uploadSubmitRequest request, boolean is_public_read);

    WFile uploadSubmit(String userId, uploadSubmit upload, boolean is_public_read);

    List<WFile> uploadSubmitMult(String userId, Map<uploadSubmitRequest, Boolean> keyMap);

    Map<String, Object> getFileById(String file_id);


    String fileGetUrl(Map<String, Object> file);

    String filePreviewUrl(Map<String, Object> file);

    Map<String, Object> filePreview(String file_id);

    List<WFile> generateUrlForEntity(List<WFile> files);

}
