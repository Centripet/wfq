package org.wfq.wufangquan.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class kkFileViewService {

    @Value("${kkFile.address}")
    private String address;

    public String generateKkFilePreviewUrl(String fileUrl) {

        byte[] urlBytes = fileUrl.getBytes(StandardCharsets.UTF_8);
        String base64Encoded = Base64.getEncoder().encodeToString(urlBytes);

        return address + "/onlinePreview?url=" + base64Encoded;
//        return "http://localhost:48012/onlinePreview?url=" + base64Encoded;
    }

    public String uploadToKkFileView(File file) throws IOException {
        String kkFileViewUrl = "http://localhost:48012/fileUpload"; // 替换为你的服务器地址

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(file));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.postForEntity(kkFileViewUrl, requestEntity, String.class);
        return response.getBody(); // KKFileView 返回的 JSON 中通常包含 previewUrl
    }
}
