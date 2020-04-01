package cn.lcl.config.shrio;

import cn.lcl.exception.enums.ResultEnum;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyRolesAuthorizationFilter extends RolesAuthorizationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        return FilterUntil.onAccessDenied((HttpServletResponse) response, ResultEnum.NOT_AUTHORIZATION);
    }


}
