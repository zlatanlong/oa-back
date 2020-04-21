package cn.lcl.service;

import cn.lcl.dto.ThingDTO;
import cn.lcl.pojo.Thing;
import cn.lcl.pojo.result.Result;

public interface ThingService {

    Result addThing(ThingDTO thing);

    Result getCreatedThing(Thing thing);

    Result getThing(Thing thing);

    Result readThing(Thing thing);

    Result replyThing(Thing thing);


}
