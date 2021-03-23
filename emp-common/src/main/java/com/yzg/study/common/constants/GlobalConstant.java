package com.yzg.study.common.constants;

/**
 * 全局常量
 */
public interface GlobalConstant {
    String OAUTH_SIGNING_KEY = "gaoxinqimeng_oauth_key";
    long NOT_EXPIRE = -1;
    String KEY_SET_PREFIX = "_set:";
    String KEY_LIST_PREFIX = "_list:";

    /**
     * ip
     */
    String UNKNOWN = "unknown";
    String DB_PREFIX = "spring.datasource";

    /**
     * security  过滤url 配置
     */
    String FILTER_IGNORE = "ignore";


    /**
     * 标识，便于接口处理
     */
    String PROJECT_PREFIX = "gaoxinqimeng_";

    /**
     * oauth 相关前缀
     */
    String OAUTH_PREFIX = "oauth:";

}
