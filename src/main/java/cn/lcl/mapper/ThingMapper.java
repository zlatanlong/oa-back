package cn.lcl.mapper;

import cn.lcl.pojo.Thing;
import cn.lcl.vo.ThingCreatedListOneVO;
import cn.lcl.vo.ThingCreatedVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ThingMapper extends BaseMapper<Thing> {

    Page<ThingCreatedListOneVO> getCreatedThingsByUserId(Page<?> page, Integer userId);

    ThingCreatedVO getCreatedThingAboutReceiverNum(Integer thingId);

    Thing getThingById(Integer thingId);
}
