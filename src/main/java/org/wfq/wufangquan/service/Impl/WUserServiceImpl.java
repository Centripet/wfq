package org.wfq.wufangquan.service.Impl;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wfq.wufangquan.controller.requestFormation.*;
import org.wfq.wufangquan.entity.regen.SUser;
import org.wfq.wufangquan.entity.regen.WFile;
import org.wfq.wufangquan.entity.regen.WUser;
import org.wfq.wufangquan.mapper.WCircleCollectionMapper;
import org.wfq.wufangquan.mapper.WUserMapper;
import org.wfq.wufangquan.service.AliOssService;
import org.wfq.wufangquan.service.IWFileService;
import org.wfq.wufangquan.service.IWUserService;
import org.wfq.wufangquan.wrapper.responseHandle.ApiResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.wfq.wufangquan.util.CaptchaGenerator.generateHexSalt;
import static org.wfq.wufangquan.util.CaptchaGenerator.generateUUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Centripet
 * @since 2025-05-11
 */
@Service
@RequiredArgsConstructor
public class WUserServiceImpl extends ServiceImpl<WUserMapper, WUser> implements IWUserService {

    private final WUserMapper wUserMapper;
    private final WCircleCollectionMapper wCircleCollectionMapper;
    private final IWFileService wFileService;
    private final AliOssService ossService;

    @Override
    public WUser loginVerification(loginRequest request) {
        Optional<WUser> WUser = wUserMapper.verifyUser(request.account(), request.passwordHash());
        return WUser.orElse(null);
    }

    @Override
    public List<WUser> findUsersByUserPhone(String phone) {
        return this.lambdaQuery()
                .eq(WUser::getPhone, phone)
                .list();
    }

    @Override
    public List<WUser> findUsersByUserAccount(String account) {
        return this.lambdaQuery()
                .eq(WUser::getAccount, account)
                .list();
    }

    @Override
    public List<WUser> findUsersByUserId(String userId) {
        return this.lambdaQuery()
                .eq(WUser::getUser_id, userId)
                .list();
    }

    public boolean userExists(registerRequest request) {
        String account = request.account();
        String phone = request.phone();
        // 构建查询条件
        LambdaQueryWrapper<WUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(account != null, WUser::getAccount, account)
                .or()
                .eq(phone != null, WUser::getPhone, phone);
        // 查询是否存在记录
        long count = wUserMapper.selectCount(queryWrapper);
        return count > 0;
    }

    @Override
    @Transactional
    public boolean registerService(registerRequest request) {
        String UUID = generateUUID();
        String salt = generateHexSalt(16);
        WUser user = WUser.builder()
                .user_id(UUID)
                .account(request.account())
                .phone(request.phone())
                .account_status(true)
                .salt(salt)
                .create_time(LocalDateTime.now())
                .build();

        if (wUserMapper.insert(user)<1) {
            return false;
        }

        if (wUserMapper.updateNewUser(request.account(), request.phone(), request.passwordHash())<1) {
            return false;
        }

        return true;
    }

    @Override
    public boolean resetPassword(resetPasswordRequest request, String phone) {
        return wUserMapper.updateNewUser(request.account(), phone, request.passwordHash()) >= 1;
    }

    @Override
    public List<SUser> userSearch(String keyword) {
        QueryWrapper<WUser> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .like(WUser::getAccount, keyword)
                .or()
                .like(WUser::getNick_name, keyword);
        List<WUser> users = wUserMapper.selectList(wrapper);

        return users.stream()
                .map(user -> BeanUtil.copyProperties(user, SUser.class))
                .collect(Collectors.toList());
    }

    @Override
    public SUser userInfo(String user_id) {
        WUser user = wUserMapper.selectOne(
                new QueryWrapper<WUser>().eq("user_id", user_id)
        );
        Map<String, Object> icon = wFileService.getFileById(user.getIcon());
        String fileKey = Optional.ofNullable(icon)
                .map(i -> i.get("file_key"))
                .map(Object::toString)
                .orElse(null);

        user.setIcon_url(fileKey != null ? ossService.generatePublicUrl(fileKey) : null);

        return BeanUtil.copyProperties(user, SUser.class);
    }

    @Override
    public ApiResponse<?> userInfoModify(String userId, userInfoModifyRequest request) {
        if (request.account() != null && !request.account().isBlank()) {
            if (!wUserMapper.exists(new QueryWrapper<WUser>().eq("account", request.account()))) {
                UpdateWrapper<WUser> wrapper = new UpdateWrapper<>();
                wrapper.eq("user_id", userId)
                                .set("account", request.account());
                wUserMapper.update(null, wrapper);
            } else {
                return ApiResponse.fail(400, "此用户名已存在");
            }
        }
        if (request.newPasswordHash() != null && !request.newPasswordHash().isBlank()) {
            String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$";
            if (!request.passwordHash().matches(regex)) {
                if (userId.equals(wUserMapper.verifyUserByUserId(userId, request.passwordHash()).map(WUser::getUser_id).orElse(null))) {
                    wUserMapper.updatePasswordByUserId(userId, request.newPasswordHash());
                } else {
                    return ApiResponse.fail(400, "原密码不正确");
                }
            } else {
                return ApiResponse.fail(400, "密码必须包含大小写字母且长度必须为6-20");
            }
        }
        if (request.icon().key() != null && !request.icon().key().isBlank()) {
            if (ossService.fileExists(request.icon().key())) {
                ossService.setPublicRead(request.icon().key());
                String UUID = wFileService.uploadSubmit(userId, request.icon(), true);

                UpdateWrapper<WUser> wrapper = new UpdateWrapper<>();
                wrapper.eq("user_id", userId)
                        .set("icon", UUID);
                wUserMapper.update(null, wrapper);
            }

        }
        return ApiResponse.success("success");
    }

    @Override
    public List<Map<String, Object>> collectionList(String userId, collectionListRequest request) {
        return wCircleCollectionMapper.collectionList(userId, request.size(), (request.page() - 1) * request.size());
    }


}