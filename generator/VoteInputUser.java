package generator;

public interface VoteInputUser {
    int deleteByPrimaryKey(Long id);

    int insert(VoteInputUser record);

    int insertSelective(VoteInputUser record);

    VoteInputUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoteInputUser record);

    int updateByPrimaryKey(VoteInputUser record);
}