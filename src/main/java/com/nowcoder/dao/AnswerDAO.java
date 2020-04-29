package com.nowcoder.dao;

import com.nowcoder.model.Answer;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by nowcoder on 2016/7/2.
 */
@Mapper
public interface AnswerDAO {
    String TABLE_NAME = "answer";
    String INSERT_FIELDS = " user_id, news_id, entity, like_count, comment_count, create_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{newsId},#{entity},#{likeCount},#{commentCount},#{createDate})"})
    int addAnswer(Answer answer);

    @Select({"select ", SELECT_FIELDS , " from ", TABLE_NAME, " where id=#{id}"})
    Answer getById(int id);
    @Select({"SELECT count(*) FROM answer WHERE news_id=#{newsId}"})
    int countById(int news_id);

    @Update({"update ", TABLE_NAME, " set comment_count = #{commentCount} where id=#{id}"})
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);

    @Update({"update ", TABLE_NAME, " set like_count = #{likeCount} where id=#{id}"})
    int updateLikeCount(@Param("id") int id, @Param("likeCount") int likeCount);

    List<Answer> selectByNewsIdAndOffset(@Param("newsId") int newsId, @Param("offset") int offset,
                                       @Param("limit") int limit);
}