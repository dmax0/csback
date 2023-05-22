package cn.withwang.cs.mapper;

import cn.withwang.cs.entity.Team;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;


@Mapper
public interface TeamMapper {

    /**
     * @description 查询所有Team的信息
     * @return List<Team>
     */
    @Select("SELECT * FROM tb_team")
    @Results({
            @Result(property = "createdAt", column = "created_at", javaType = Timestamp.class),
            @Result(property = "updatedAt", column = "updated_at", javaType = Timestamp.class)
    })
    List<Team> selectAll();

    /**
     * @description 根据id查询Team的信息
     * @param id Team的id
     * @return Team
     */
    @Select("SELECT * FROM tb_team WHERE id = #{id}")
    @Results({
            @Result(property = "createdAt", column = "created_at", javaType = Timestamp.class),
            @Result(property = "updatedAt", column = "updated_at", javaType = Timestamp.class)
    })
    Team selectById(int id);

    /**
     * @description 创建Team
     * @param team Team的信息
     * @return int
     */
    @Insert("INSERT INTO tb_team (name, industry, province, city, created_at, updated_at) VALUES (#{name}, #{industry}, #{province}, #{city}, #{createdAt}, #{updatedAt})")
    int insert(Team team);

    /**
     * @description 更新Team
     * @param team Team的信息
     * @return int
     */
    @Update("UPDATE tb_team SET name = #{name}, industry = #{industry}, province = #{province}, city = #{city}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(Team team);
}
