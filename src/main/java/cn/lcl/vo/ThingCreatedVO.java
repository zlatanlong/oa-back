package cn.lcl.vo;

import cn.lcl.pojo.Thing;
import cn.lcl.pojo.ThingReceiver;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class ThingCreatedVO {

    private Thing thing;

    private Integer receiversCount;

    private Integer readCount;

    private Integer finishedCount;

    private Page<ThingReceiver> thingReceiversPage;
}
