package cn.newness.imip.framework.common.util.spring;

import cn.hutool.extra.spring.SpringUtil;

import java.util.Objects;

/**
 * Spring 工具类
 *
 * @author 新奇源码
 */
public class SpringUtils extends SpringUtil {

    /**
     * 是否为生产环境
     *
     * @return 是否生产环境
     */
    public static boolean isProd() {
        String activeProfile = getActiveProfile();
        return Objects.equals("prod", activeProfile);
    }

}