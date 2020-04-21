package cn.lcl.dto;

import cn.lcl.pojo.ThingReplyFile;
import lombok.Data;

import java.util.List;

@Data
public class ThingReplyDTO {

    private Integer thingId;

    private String content;

    // 用户上传的文件
    private List<ThingReplyFile> replyFiles;



}
