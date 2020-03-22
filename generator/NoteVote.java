package generator;

public interface NoteVote {
    int deleteByPrimaryKey(Long id);

    int insert(NoteVote record);

    int insertSelective(NoteVote record);

    NoteVote selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NoteVote record);

    int updateByPrimaryKey(NoteVote record);
}