package cn.lcl.mapper;

import cn.lcl.pojo.ThingReceiver;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface ThingReceiverMapper extends BaseMapper<ThingReceiver> {
    Page<ThingReceiver> selectThingReceiversByReceiverId(Page<?> page, Integer userId, ThingReceiver thingReceiver);

    Page<ThingReceiver> selectThingReceiversAndUserRealNamePageByThingId(Page<?> page, ThingReceiver thingReceiver);

    ThingReceiver selectThingReceiverAndUserRealNamePage(Integer thingId,Integer userId);



}
