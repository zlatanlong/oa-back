package cn.lcl.vo;

import cn.lcl.pojo.ThingReceiver;
import cn.lcl.pojo.ThingReplyFile;
import lombok.Data;

import java.util.List;

@Data
public class ThingRepliedVO {
    // 1.content
    private ThingReceiver thingReceiver;
    // 2.reply files
    private List<ThingReplyFile> files;
    // 3.question answers
}
