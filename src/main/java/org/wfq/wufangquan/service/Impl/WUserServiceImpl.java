package org.wfq.wufangquan.service.Impl;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wfq.wufangquan.controller.requestFormation.loginRequest;
import org.wfq.wufangquan.controller.requestFormation.registerRequest;
import org.wfq.wufangquan.controller.requestFormation.resetPasswordRequest;
import org.wfq.wufangquan.entity.regen.SUser;
import org.wfq.wufangquan.entity.regen.WUser;
import org.wfq.wufangquan.mapper.WUserMapper;
import org.wfq.wufangquan.service.IWUserService;

import java.time.LocalDateTime;
import java.util.List;
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

    private final WUserMapper WUserMapper;

    @Override
    public WUser loginVerification(loginRequest request) {
        Optional<WUser> WUser = WUserMapper.verifyUser(request.account(), request.passwordHash());
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
        long count = WUserMapper.selectCount(queryWrapper);
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

        if (WUserMapper.insert(user)<1) {
            return false;
        }

        if (WUserMapper.updateNewUser(request.account(), request.phone(), request.passwordHash())<1) {
            return false;
        }

        return true;
    }

    @Override
    public boolean resetPassword(resetPasswordRequest request, String phone) {
        return WUserMapper.updateNewUser(request.account(), phone, request.passwordHash()) >= 1;
    }

    @Override
    public List<SUser> userSearch(String keyword) {
        QueryWrapper<WUser> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .like(WUser::getAccount, keyword)
                .or()
                .like(WUser::getNick_name, keyword);
        List<WUser> users = WUserMapper.selectList(wrapper);

        return users.stream()
                .map(user -> BeanUtil.copyProperties(user, SUser.class))
                .collect(Collectors.toList());
    }

    @Override
    public SUser userInfo(String user_id) {
        WUser user = WUserMapper.selectOne(
                new QueryWrapper<WUser>().eq("user_id", user_id)
        );

        return BeanUtil.copyProperties(user, SUser.class);
    }

}