package cn.lcl.util;

import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthorUntil {
    private static ArrayList<Long> getManagedIdList() {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            throw new MyException(ResultEnum.NOT_AUTHENTICATION);
        }
        Object managedIdList = subject.getSession().getAttribute("managedIdList");

        // dangerous
        return (ArrayList<Long>) managedIdList;
    }

    public static void authorManageUser(Long id) {
        if (!getManagedIdList().contains(id)) {
            throw new MyException(ResultEnum.NO_AUTHOR_FOR_THIS_USER);
        }
    }

    public static void authorManageUser(Collection<Long> ids) {
        for (Long id : ids) {
            authorManageUser(id);
        }
    }
}
