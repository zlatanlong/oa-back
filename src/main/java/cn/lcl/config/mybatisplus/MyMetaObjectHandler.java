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
        /*
         * 如果有的实体有createTime字段需要fill，
         * 有的实体没有，并且添加createTime字段
         * 还需要额外开销，
         * 可以先进行判断
         */
        boolean hasSetter = metaObject.hasSetter("createTime");

        /*
         * 先看是否主动设了值
         */
        Object createTime = this.getFieldValByName("createTime", metaObject);

        if (hasSetter && createTime == null) {
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("==============execute update fill============");

        boolean hasSetter = metaObject.hasSetter("updateTime");

        if (hasSetter) {
            this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        }

//
//        boolean hasSetter = metaObject.hasSetter("operateUserId");
//        if (hasSetter) {
//            Subject subject = SecurityUtils.getSubject();
//            if (subject.isAuthenticated()) {
//                User user = (User) subject.getPrincipal();
//                this.strictUpdateFill(metaObject, "operateUserId", Long.class, user.getId());
//            }
//        }

//        this.strictUpdateFill(metaObject, "updatedTime", LocalDateTime.class, LocalDateTime.now());
    }
}
