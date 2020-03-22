package generator;

public interface ExamRoom {
    int deleteByPrimaryKey(Long id);

    int insert(ExamRoom record);

    int insertSelective(ExamRoom record);

    ExamRoom selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExamRoom record);

    int updateByPrimaryKey(ExamRoom record);
}