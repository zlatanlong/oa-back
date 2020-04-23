package cn.lcl.service;

import cn.lcl.dto.DataPageDTO;
import cn.lcl.dto.IdDTO;
import cn.lcl.dto.ThingAddDTO;
import cn.lcl.dto.ThingReplyDTO;
import cn.lcl.pojo.ThingReceiver;
import cn.lcl.pojo.result.Result;

public interface ThingService {

    Result addThing(ThingAddDTO thing);

    Result getCreatedThings(DataPageDTO<?> page);

    Result getCreatedThing(DataPageDTO<ThingReceiver> page);

    Result getJoinedThing(IdDTO thingId);

    Result getRepliedThing(ThingReceiver thingReceiver);

    Result readThing(IdDTO thingId);

    Result getJoinedThings(DataPageDTO<ThingReceiver> page);

    Result replyThing(ThingReplyDTO reply);

    Result ifReplied(IdDTO idDTO);

}
