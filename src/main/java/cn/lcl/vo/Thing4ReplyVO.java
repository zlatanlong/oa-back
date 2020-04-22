package cn.lcl.vo;

import cn.lcl.pojo.Thing;
import cn.lcl.pojo.ThingSendFile;
import lombok.Data;

import java.util.List;

@Data
public class Thing4ReplyVO {

    private Thing thing;

    private String senderName;

    private List<ThingSendFile> files;

}
