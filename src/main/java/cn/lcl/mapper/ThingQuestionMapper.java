package cn.lcl.mapper;

import cn.lcl.pojo.ThingQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ThingQuestionMapper extends BaseMapper<ThingQuestion> {

    List<ThingQuestion> selectQuestionListByThingId(Integer thingId);

    List<ThingQuestion> selectQuestionAnswers(Integer thingId, Integer userId);

    String selectQuestionType(Integer thingId);

}
