package cn.lcl.service;

import cn.lcl.pojo.Tag;
import cn.lcl.pojo.result.Result;

public interface TagService {
    Result addTag(Tag tag);

    Result getTag(Tag tag);

    /**
     * 个人私有和共有的标签
     */
    Result getAvailableTags();

    Result getCreatedTags();

    Result updateTag(Tag tag);

}
