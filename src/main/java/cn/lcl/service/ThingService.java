package cn.lcl.service;

import cn.lcl.pojo.dto.SearchPageDTO;
import cn.lcl.pojo.dto.IdDTO;
import cn.lcl.pojo.dto.ThingAddDTO;
import cn.lcl.pojo.dto.ThingFinishDTO;
import cn.lcl.pojo.ThingReceiver;
import cn.lcl.pojo.result.Result;

public interface ThingService {

    Result saveThing(ThingAddDTO thing);

    Result listCreatedThings(SearchPageDTO<?> page);

    Result getCreatedThing(SearchPageDTO<ThingReceiver> page);

    Result getJoinedThing(IdDTO thingId);

    Result readThing(IdDTO thingId);

    Result listJoinedThings(SearchPageDTO<ThingReceiver> page);

    Result finishThing(ThingFinishDTO finishDTO);

    Result getFinishedThing(ThingReceiver thingReceiver);

    Result ifFinished(IdDTO idDTO);

}
