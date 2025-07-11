package org.wfq.wufangquan.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 动态
 * </p>
 *
 * @author Centripet
 * @since 2025-05-23
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("Moment")
public class Moment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId("MomentId")
    private String momentId;

    /**
     * *用户id
     */
    private String userId;

    /**
     * 哪一方发的朋友圈
     */
    private Integer organization;

    /**
     * 文本内容
     */
    private String content;

    /**
     * 图片或视频的存放地址
     */
    private String resUrl;

    /**
     * 文件名称
     */
    private String filenames;

    /**
     * 已阅的用户id列表
     */
    private String isUserIdSeeList;

    /**
     * 收藏的用户列表
     */
    private String isFavoritesList;

    /**
     * 图片列表
     */
    private String pictureList;

    /**
     * 0全部可看； 1谁可以看； 2谁不可以看；3仅自己可见；4勘察方特殊权限； 5外协方特殊权限
     */
    private Integer visibilityType;

    /**
     * 权限用户的id数组，触发器根据这个数组自动插入权限表
     */
    private String visibilityUserIds;

    /**
     * *0为可见；1为删除
     */
    private boolean delMoment;

    /**
     * 创建日期
     */
    private LocalDateTime createTime;

    /**
     * 删除时间
     */
    private LocalDateTime delTime;

    /**
     * 是否是任务，0默认不是任务，1是进行中，2是未完成，3是已完成
     */
    private String renwu;

    private Integer newStaus;

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getOrganization() {
        return organization;
    }

    public void setOrganization(Integer organization) {
        this.organization = organization;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public String getFilenames() {
        return filenames;
    }

    public void setFilenames(String filenames) {
        this.filenames = filenames;
    }

    public String getIsUserIdSeeList() {
        return isUserIdSeeList;
    }

    public void setIsUserIdSeeList(String isUserIdSeeList) {
        this.isUserIdSeeList = isUserIdSeeList;
    }

    public String getIsFavoritesList() {
        return isFavoritesList;
    }

    public void setIsFavoritesList(String isFavoritesList) {
        this.isFavoritesList = isFavoritesList;
    }

    public String getPictureList() {
        return pictureList;
    }

    public void setPictureList(String pictureList) {
        this.pictureList = pictureList;
    }

    public Integer getVisibilityType() {
        return visibilityType;
    }

    public void setVisibilityType(Integer visibilityType) {
        this.visibilityType = visibilityType;
    }

    public String getVisibilityUserIds() {
        return visibilityUserIds;
    }

    public void setVisibilityUserIds(String visibilityUserIds) {
        this.visibilityUserIds = visibilityUserIds;
    }

    public boolean getDelMoment() {
        return delMoment;
    }

    public void setDelMoment(boolean delMoment) {
        this.delMoment = delMoment;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getDelTime() {
        return delTime;
    }

    public void setDelTime(LocalDateTime delTime) {
        this.delTime = delTime;
    }

    public String getRenwu() {
        return renwu;
    }

    public void setRenwu(String renwu) {
        this.renwu = renwu;
    }

    public Integer getNewStaus() {
        return newStaus;
    }

    public void setNewStaus(Integer newStaus) {
        this.newStaus = newStaus;
    }

    @Override
    public String toString() {
        return "Moment{" +
        "momentId = " + momentId +
        ", user_id = " + userId +
        ", organization = " + organization +
        ", content = " + content +
        ", resUrl = " + resUrl +
        ", filenames = " + filenames +
        ", isUserIdSeeList = " + isUserIdSeeList +
        ", isFavoritesList = " + isFavoritesList +
        ", pictureList = " + pictureList +
        ", visibilityType = " + visibilityType +
        ", visibilityUserIds = " + visibilityUserIds +
        ", delMoment = " + delMoment +
        ", create_time = " + createTime +
        ", delTime = " + delTime +
        ", renwu = " + renwu +
        ", newStaus = " + newStaus +
        "}";
    }
}
