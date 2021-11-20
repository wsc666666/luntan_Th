package com.nowcoder.dao;

import com.nowcoder.model.News;
import com.nowcoder.model.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasse on 2020/4/1
 */
@Mapper
public interface UserDAO {
    String TABLE_NAME = "user";
    String INSET_FIELDS = " name, password, salt, head_url, zh_name, jc, star, star2, star3, lv ";
    String SELECT_FIELDS = " id, name, password, salt, head_url, zh_name, jc, star ,star2, star3, lv ";

    @Insert({"insert into ", TABLE_NAME, "(", INSET_FIELDS,
            ") values (#{name},#{password},#{salt},#{headUrl},#{zhName},#{jc},#{star},#{star2},#{star3},#{lv})"})
    int addUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(int id);
    @Select({"SELECT count(*) FROM answer WHERE user_id=#{userId}"})
    int countById(int user_id);
    @Select({"SELECT count(*) FROM news WHERE user_id=#{userId}"})
    int countNewsById(int user_id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{name}"})
    User selectByName(String name);

    @Update({"update ", TABLE_NAME, " set password=#{password} where id=#{id}"})
    void updatePassword(User user);
    @Update({"update ", TABLE_NAME, " set star=#{star} where id=#{id}"})
    void updateStar(User user);
    @Update({"update ", TABLE_NAME, " set star2=#{star2} where id=#{id}"})
    void updateStar2(User user);
    @Update({"update ", TABLE_NAME, " set star3=#{star3} where id=#{id}"})
    void updateStar3(User user);
    @Update({"update ", TABLE_NAME, " set lv=#{lv} where id=#{id}"})
    void updateLv(User user);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);
    @Select({"SELECT ",SELECT_FIELDS," FROM ",TABLE_NAME," WHERE star3 LIKE '%${star3}%'"})
    ArrayList<User> selectByStar3(@Param("star3") String star3);
}
