package org.wfq.wufangquan.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;
import org.wfq.wufangquan.controller.requestFormation.*;
import org.wfq.wufangquan.entity.JwtPayload;
import org.wfq.wufangquan.entity.regen.WUser;
import org.wfq.wufangquan.service.IWUserService;
import org.wfq.wufangquan.service.JwtService;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponse;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.wfq.wufangquan.service.AliSmsService.sendCodeForAliYun;
import static org.wfq.wufangquan.util.CaptchaGenerator.generateNumericCaptcha;

/**
 * <p>
 * 授权
 * </p>
 *
 * @author Centripet
 */
@Slf4j
@Tag(name = "登录验证", description = "登录注册鉴权等相关接口")
@RestController
@RequestMapping("/api/oath")
@RequiredArgsConstructor
public class authController {
    private final IWUserService usersService;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtService jwtService;
    private final List<String> deviceList = List.of("pc", "mobile");

    @PostMapping("/login")
    @Operation(summary = "登录", description = "账号密码登录")
    public ApiResponse<?> login(
            @RequestBody loginRequest request,
            HttpServletResponse response
    ) {
        WUser user = usersService.loginVerification(request);
        if (user != null) {
            JwtPayload payload = JwtPayload.builder()
                    .user_id(user.getUser_id())
                    .role(user.getRole())
                    .department(user.getDepartment())
                    .build();

            String accessToken = jwtService.generateAccessToken(payload);
            String refreshToken = jwtService.generateRefreshToken(payload);

            jwtService.setTokenForUserId(redisTemplate, user.getUser_id(), refreshToken, accessToken, response);

            return ApiResponse.success(Collections.singletonMap("user_id", user.getUser_id()));
        } else {
            return ApiResponse.fail(401, "登录失败:用户名或密码错误");
        }
    }

    @PostMapping("/login-Captcha")
    public ApiResponse<?> loginCaptcha(
            @RequestBody loginCaptchaRequest request,
            HttpServletResponse response
    ) {
        List<WUser> users = usersService.findUsersByUserAccount(request.account());
        String phone;
        if (!users.isEmpty()) {
            WUser user = users.get(0);
            phone = user.getPhone();
            String code = redisTemplate.opsForValue().get("phoneCode:" + phone);

            if (code == null) {
                return ApiResponse.fail(400, "验证码已失效");
            }

            if (!code.equals(request.verificationCode())) {
                return ApiResponse.fail(400, "验证码错误");
            }

            JwtPayload payload = JwtPayload.builder()
                    .user_id(user.getUser_id())
                    .role(user.getRole())
                    .department(user.getDepartment())
                    .build();

            System.out.println(payload.toString());

            String accessToken = jwtService.generateAccessToken(payload);
            String refreshToken = jwtService.generateRefreshToken(payload);

            jwtService.setTokenForUserId(redisTemplate, user.getUser_id(), refreshToken, accessToken, response);

            redisTemplate.delete("phoneCode:" + phone);

            return ApiResponse.success(Collections.singletonMap("user_id", user.getUser_id()));
        } else {
            return ApiResponse.fail(401, "登录失败:用户不存在");
        }

    }

    @PostMapping("/resetPassword")
    public ApiResponse<?> resetPassword(
            @RequestBody resetPasswordRequest request,
            HttpServletResponse response
    ) {

        List<WUser> users = usersService.findUsersByUserAccount(request.account());
        String phone;
        if (!users.isEmpty()) {
            WUser user = users.get(0);
            phone = user.getPhone();
            String code = redisTemplate.opsForValue().get("phoneCode:" + phone);

            if (code == null) {
                return ApiResponse.fail(400,"验证码已失效");
            }

            if (!code.equals(request.verificationCode())) {
                return ApiResponse.fail(400,"验证码错误");
            }

            if (!Objects.equals(request.passwordHash(), request.passwordHashRe())) {
                return ApiResponse.fail(400,"密码不一致");
            }

            String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$";
            if (!request.passwordHash().matches(regex)) {
                return ApiResponse.fail(400,"密码必须包含大小写字母");
            }
            if (!usersService.resetPassword(request, phone)) {
                return ApiResponse.fail(500, "重置失败");
            }

            redisTemplate.delete("phoneCode:" + phone);

            return ApiResponse.success("重置密码成功");
        } else {
            return ApiResponse.fail(401, "重置失败:用户不存在");
        }
    }

    @PostMapping("/sendCaptcha")
    public ApiResponse<?> sendCode(@RequestBody sendCodeRequest request) {
        String phone = "";

        if (request.method().equals("account")) {
            List<WUser> users = usersService.findUsersByUserAccount(request.str());
            if (!users.isEmpty()) {
                phone = users.get(0).getPhone();
            } else {
                return ApiResponse.fail(401, "发送失败:用户不存在");
            }
        }

        if (request.method().equals("phone")) {
            phone = request.str();
        }

        if (phone.isEmpty()) {
            return ApiResponse.fail(401, "未知发送方式");
        }

        String regex = "^1[3-9]\\d{9}$";

        if (!phone.matches(regex)) {
            return ApiResponse.fail(400,"手机号码格式不正确");
        }

//        String code = redisTemplate.opsForValue().get("phoneCode:" + phone);
//        if (code != null) {
//            return ApiResponse.fail(400,"5分钟内只能发送一次验证码");
//        }

        Long ttl = redisTemplate.getExpire("phoneCode:" + phone, TimeUnit.SECONDS);
        if (ttl != null && ttl > 0) {
            long secondsPassed = 300 - ttl; // 已过去的秒数（300 是 5 分钟）
            if (secondsPassed < 60) {
                // 不允许再次发送
                return ApiResponse.fail(400,"验证码发送过于频繁，请稍后再试");
            }
        }

        String code = generateNumericCaptcha(6);
        redisTemplate.opsForValue().set("phoneCode:" + phone, code, 5, TimeUnit.MINUTES);

        // 验证码发送服务 * sendCodeService
        sendCodeForAliYun(phone,code);

        return ApiResponse.success("验证码已发送,5分钟有效");
    }

    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody registerRequest request) {

        if (!request.agree_policy()) {
            return ApiResponse.fail(400,"未勾选用户协议");
        }

        String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$";
        if (!request.passwordHash().matches(regex)) {
            return ApiResponse.fail(400,"密码必须包含大小写字母");
        }

        String code = redisTemplate.opsForValue().get("phoneCode:" + request.phone());

        if (code == null) {
            return ApiResponse.fail(400,"验证码已失效");
        }

        if (!code.equals(request.verificationCode())) {
            return ApiResponse.fail(400,"验证码错误");
        }

        if (usersService.userExists(request)) {
            return ApiResponse.fail(400,"注册失败:用户名或手机号码已存在");
        }

        try {
            if (!usersService.registerService(request)) {
                return ApiResponse.fail(500,"注册失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ApiResponse.fail(500,"注册失败");
        }

        redisTemplate.delete("phoneCode:" + request.phone());

        return ApiResponse.success("注册成功");
    }

    @PostMapping("/refresh")
    public ApiResponse<?> refreshToken(
            @CookieValue(name = "refresh_token", required = false) String refreshToken,
            HttpServletResponse response
    ) {
        if (refreshToken == null) {
            return ApiResponse.fail(401,"未提供 refresh_token");
        }

        JwtPayload payload;
        try {
            // 从 refresh_token 中解析
            payload = jwtService.extractPayload(refreshToken);
        } catch (Exception e) {
            return ApiResponse.fail(401,"无效的 refresh_token");
        }

        // 从 Redis 中获取用户对应的 refresh_token
        String redisToken = redisTemplate.opsForValue().get("refresh:" + payload.getUser_id());
        if (redisToken == null) {
            return ApiResponse.fail(401,"未找到该用户的 refresh_token");
        }

        // 比对 Redis 中存储的 refresh_token 和前端传来的 refresh_token
        if (!redisToken.equals(refreshToken)) {
            return ApiResponse.fail(401,"refresh_token 不匹配");
        }

        // 验证 refresh_token 是否有效
        if (!jwtService.isTokenValid(refreshToken)) {
            return ApiResponse.fail(401,"refresh_token 已过期");
        }

        // 生成新的 access_token 和 refresh_token
        String newAccessToken = jwtService.generateAccessTokenByUserId(payload);
        String newRefreshToken = jwtService.generateRefreshTokenByUserId(payload);

        jwtService.setTokenForUserId(redisTemplate, payload.getUser_id(), newAccessToken, newRefreshToken, response);

        return ApiResponse.success("Token 已刷新");
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(
            @CookieValue(name = "refresh_token", required = false) String refreshToken,
            HttpServletResponse response
    ) {
        if (refreshToken == null) {
            return ApiResponse.fail(401,"未提供 refresh_token");
        }

        String userId;
        try {
            userId = jwtService.extractUserId(refreshToken);
        } catch (Exception e) {
            return ApiResponse.fail(401,"refresh_token 无效");
        }

        // 从 Redis 中删除该 refresh_token
        redisTemplate.delete("refresh:" + userId);

        // 清除 Cookie
        ResponseCookie clearCookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)  // 立即失效
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, clearCookie.toString());

        return ApiResponse.success("已成功登出");
    }
}
