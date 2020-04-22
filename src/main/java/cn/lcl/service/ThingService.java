package cn.lcl.service;

import cn.lcl.dto.DataPageDTO;
import cn.lcl.dto.ThingAddDTO;
import cn.lcl.dto.ThingReplyDTO;
import cn.lcl.pojo.Thing;
import cn.lcl.pojo.ThingReceiver;
import cn.lcl.pojo.result.Result;

public interface ThingService {

    Result addThing(ThingAddDTO thing);

    Result getCreatedThing(Thing thing);

    Result getThing4Reply(Thing thing);

    Result getRepliedThing(ThingReceiver thingReceiver);

    Result readThing(Thing thing);

    Result getJoinedList(DataPageDTO<?> page);

    Result replyThing(ThingReplyDTO reply);


}
