package generator;

public interface VoteOptionUser {
    int deleteByPrimaryKey(Long id);

    int insert(VoteOptionUser record);

    int insertSelective(VoteOptionUser record);

    VoteOptionUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoteOptionUser record);

    int updateByPrimaryKey(VoteOptionUser record);
}