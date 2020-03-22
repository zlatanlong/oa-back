package generator;

public interface VoteOptionsGroup {
    int deleteByPrimaryKey(Long id);

    int insert(VoteOptionsGroup record);

    int insertSelective(VoteOptionsGroup record);

    VoteOptionsGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoteOptionsGroup record);

    int updateByPrimaryKey(VoteOptionsGroup record);
}