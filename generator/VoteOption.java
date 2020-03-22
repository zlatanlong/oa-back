package generator;

public interface VoteOption {
    int deleteByPrimaryKey(Long id);

    int insert(VoteOption record);

    int insertSelective(VoteOption record);

    VoteOption selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoteOption record);

    int updateByPrimaryKey(VoteOption record);
}