package cn.lcl.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("插入fill");

        /*
         * 如果有的实体有createTime字段需要fill，
         * 有的实体没有，并且添加createTime字段
         * 还需要额外开销，
         * 可以先进行判断
         */
        boolean hasSetter = metaObject.hasSetter("createdTime");

        /*
         * 先看是否主动设了值
         */
        Object createTime = getFieldValByName("createdTime", metaObject);

        System.out.println("hasSetter" + hasSetter);
        if (hasSetter && createTime == null) {
            this.strictInsertFill(metaObject, "createdTime", LocalDateTime.class, LocalDateTime.now());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("更新fill");
        this.strictUpdateFill(metaObject, "updatedTime", LocalDateTime.class, LocalDateTime.now());
    }
}
