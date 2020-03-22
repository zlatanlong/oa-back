package generator;

public interface UserMeeting {
    int deleteByPrimaryKey(Long id);

    int insert(UserMeeting record);

    int insertSelective(UserMeeting record);

    UserMeeting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserMeeting record);

    int updateByPrimaryKey(UserMeeting record);
}