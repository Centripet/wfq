package org.wfq.wufangquan.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.wfq.wufangquan.controller.requestFormation.addMomentRequest;
import org.wfq.wufangquan.controller.requestFormation.getMomentsRequest;
import org.wfq.wufangquan.controller.requestFormation.idMomentRequest;
import org.wfq.wufangquan.controller.requestFormation.idUserRequest;
import org.wfq.wufangquan.entity.Moment;
import org.wfq.wufangquan.entity.regen.WUser;
import org.wfq.wufangquan.mapper.MomentMapper;
import org.wfq.wufangquan.mapper.WUserMapper;
import org.wfq.wufangquan.service.IMomentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static org.wfq.wufangquan.util.CaptchaGenerator.generateUUID;

/**
 * <p>
 * 动态 服务实现类
 * </p>
 *
 * @author Centripet
 * @since 2025-05-23
 */
@Service
@RequiredArgsConstructor
public class MomentServiceImpl extends ServiceImpl<MomentMapper, Moment> implements IMomentService {

    private final WUserMapper IUserMapper;
    private final MomentMapper momentMapper;

    @Override
    public boolean addMoment(String userId, addMomentRequest request) {
        WUser user = IUserMapper.selectOne(
                new QueryWrapper<WUser>().eq("userid", userId)
        );
        String UUID = generateUUID();
        Moment moment = Moment.builder()
                .momentId(UUID)
                .userId(userId)
                .organization(Integer.valueOf(user.getOrganization()))
                .content(request.Content())
                .resUrl(request.ResUrl())
                .filenames(request.filenames())
                .visibilityType(0)
                .delMoment(false)
                .renwu("0")
                .newStaus(0)
                .createTime(LocalDateTime.now())
                .build();

        return momentMapper.insert(moment) > 0;
    }

    @Override
    public boolean deleteMoment(String userId, idMomentRequest request) {
        if(!momentMapper.selectOne(
                new QueryWrapper<Moment>().eq("MomentId", request.MomentId())
        ).getUserId().equals(userId)){
            return false;
        }

        return momentMapper.update(
                null,
                new UpdateWrapper<Moment>()
                        .set("DelMoment", true)
                        .set("DelTime", LocalDateTime.now())
                        .eq("MomentId", request.MomentId())
        ) > 0;

    }

    @Override
    public boolean setReadMoment(String userId, idMomentRequest request) {
        String isUserIdSeeList = momentMapper.selectOne(
                new QueryWrapper<Moment>().eq("MomentId", request.MomentId())
        ).getIsUserIdSeeList();

        Set<String> idSet = new HashSet<>(Arrays.asList(isUserIdSeeList.split(",")));
        idSet.add(userId);
        String updatedSeeList = String.join(",", idSet);

        return momentMapper.update(
                null,
                new UpdateWrapper<Moment>()
                        .set("IsUserIdSeeList", updatedSeeList)
                        .eq("MomentId", request.MomentId())
        ) > 0;
    }

    @Override
    public boolean setFavoriteMoment(String userId, idMomentRequest request, boolean favorite) {

        String isFavoritesList = momentMapper.selectOne(
                new QueryWrapper<Moment>().eq("MomentId", request.MomentId())
        ).getIsUserIdSeeList();

        Set<String> idSet = new HashSet<>(Arrays.asList(isFavoritesList.split(",")));
        if (favorite) {
            idSet.add(userId);
        } else {
            idSet.remove(userId);
        }
        String updatedFavoritesList = String.join(",", idSet);

        return momentMapper.update(
                null,
                new UpdateWrapper<Moment>()
                        .set("IsFavoritesList", updatedFavoritesList)
                        .eq("MomentId", request.MomentId())
        ) <= 0;

    }

    @Override
    public IPage<Moment> getMomentsByUserId(getMomentsRequest request) {
        Page<Moment> pageRequest = new Page<>(request.page(), request.size());
        QueryWrapper<Moment> queryWrapper = new QueryWrapper<>();

        if(request.Organization()<0) {
            queryWrapper.and(wrapper -> wrapper
                    .eq("DelMoment", false)
            );
        } else {
            queryWrapper.and(wrapper -> wrapper
                    .eq("DelMoment", false)
                    .eq("Organization",request.Organization())
            );
        }

        return momentMapper.selectPage(pageRequest, queryWrapper);
    }

    @Override
    public Map<String,String> getUserInfoByUserId(idUserRequest request) {
        WUser user = IUserMapper.selectOne(
                new QueryWrapper<WUser>().eq("userid", request.userId())
        );
        Map<String,String> userMap = new HashMap<>();
        userMap.put("userid", user.getUser_id());
        userMap.put("Username", user.getAccount());
        userMap.put("NickName", user.getNick_name());
        userMap.put("Icon", user.getIcon());

        return userMap;
    }

}
