package generator;

public interface ExamRoomTeacher {
    int deleteByPrimaryKey(Long id);

    int insert(ExamRoomTeacher record);

    int insertSelective(ExamRoomTeacher record);

    ExamRoomTeacher selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExamRoomTeacher record);

    int updateByPrimaryKey(ExamRoomTeacher record);
}