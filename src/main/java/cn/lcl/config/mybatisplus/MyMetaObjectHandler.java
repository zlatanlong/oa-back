package cn.lcl.config.mybatisplus;

import cn.lcl.pojo.User;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * mybatus-plus 自动填充配置
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        // create_time
        boolean hasCreateTime = metaObject.hasSetter("createTime");
        Object createTime = this.getFieldValByName("createTime", metaObject);

        if (hasCreateTime && createTime == null) {
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        }

        // delete_flg
        boolean hasDeleteFlg = metaObject.hasSetter("deleteFlg");
        if (hasDeleteFlg) {
            this.strictInsertFill(metaObject, "deleteFlg", Integer.class, 0);
        }

        // creator_id
        boolean hasCreatorId = metaObject.hasSetter("creatorId");
        if (hasCreatorId) {
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            this.strictInsertFill(metaObject, "creatorId", Integer.class, user.getId());
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {

        // update_time
        boolean hasUpdateTime = metaObject.hasSetter("updateTime");
        if (hasUpdateTime) {
            this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        }

        // updator_id
        boolean hasUpdatorId = metaObject.hasSetter("updatorId");
        if (hasUpdatorId) {
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            this.strictUpdateFill(metaObject, "updatorId", Integer.class, user.getId());
        }

    }
}
