package generator;

public interface UserNote {
    int deleteByPrimaryKey(Long id);

    int insert(UserNote record);

    int insertSelective(UserNote record);

    UserNote selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserNote record);

    int updateByPrimaryKey(UserNote record);
}