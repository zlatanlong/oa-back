package generator;

public interface NoteFile {
    int deleteByPrimaryKey(Long id);

    int insert(NoteFile record);

    int insertSelective(NoteFile record);

    NoteFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NoteFile record);

    int updateByPrimaryKey(NoteFile record);
}