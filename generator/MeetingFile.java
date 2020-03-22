package generator;

public interface MeetingFile {
    int deleteByPrimaryKey(Long id);

    int insert(MeetingFile record);

    int insertSelective(MeetingFile record);

    MeetingFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MeetingFile record);

    int updateByPrimaryKey(MeetingFile record);
}