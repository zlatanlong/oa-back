package generator;

public interface VoteInput {
    int deleteByPrimaryKey(Long id);

    int insert(VoteInput record);

    int insertSelective(VoteInput record);

    VoteInput selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoteInput record);

    int updateByPrimaryKey(VoteInput record);
}