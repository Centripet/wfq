package org.wfq.wufangquan.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.wfq.wufangquan.entity.regen.WUser;
import org.apache.ibatis.annotations.Mapper;
import java.util.Optional;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Centripet
 * @since 2025-05-11
 */

@Mapper
public interface WUserMapper extends BaseMapper<WUser> {

    @Select("SELECT * FROM w_user WHERE account = #{account} AND account_status = 1 " +
            "AND password_hash = SHA2(CONCAT(salt, #{password}), 256) LIMIT 1")
    Optional<WUser> verifyUser(@Param("account") String account, @Param("password") String password);

    @Select("SELECT * FROM w_user WHERE user_id = #{user_id} AND account_status = 1 " +
            "AND password_hash = SHA2(CONCAT(salt, #{password}), 256) LIMIT 1")
    Optional<WUser> verifyUserByUserId(@Param("user_id") String user_id, @Param("password") String password);

    @Update("UPDATE w_user " +
            "SET password_hash = SHA2(CONCAT(salt, #{password}), 256)" +
            "WHERE account = #{account} AND account_status = 1 AND phone = #{phone}")
    Integer updateNewUser(@Param("account") String account, @Param("phone") String phone, @Param("password") String password);

    @Update("UPDATE w_user " +
            "SET password_hash = SHA2(CONCAT(salt, #{password}), 256)" +
            "WHERE user_id = #{user_id} AND account_status = 1")
    Integer updatePasswordByUserId(@Param("user_id") String user_id, @Param("password") String password);

}