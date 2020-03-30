package cn.lcl.config.shrio;

import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.pojo.User;
import cn.lcl.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        System.out.println("进行了==>认证doGetAuthenticationInfo");

        // 获取当前用户userToken
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        // user : 根据输入的用户名去查到的用户对象
        User user = userService.queryUserByPhone(userToken.getUsername());

        if (user == null) { // 没有这个人，防止下面空指针异常
            return null;
        }

        // 封装用户登录数据
        // 密码认证由shiro做
        return
                /*
                 * 第一个参数是数据库中查到的用户
                 * 如果认证成功
                 * 可以传递到授权，授权时候就可以看到用户的身份了。
                 */
                new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }
}
