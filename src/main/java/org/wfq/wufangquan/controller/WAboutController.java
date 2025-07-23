package org.wfq.wufangquan.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wfq.wufangquan.entity.JwtPayload;
import org.wfq.wufangquan.entity.regen.WAbout;
import org.wfq.wufangquan.mapper.WAboutMapper;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/about")
@RequiredArgsConstructor
public class WAboutController {

    private final WAboutMapper wAboutMapper;

    @PostMapping("/about")
    public ApiResponse<?> register(
            HttpServletResponse response
    ) {
        List<WAbout> configList = wAboutMapper.selectList(null);
        return ApiResponse.success(
                configList.stream()
                .collect(Collectors.toMap(WAbout::getAbout_name, WAbout::getAbout_value))
        );
    }

}
