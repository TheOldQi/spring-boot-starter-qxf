package com.xiafei.springboot.starter.autoconfig.nosql.redis;

/**
 * <P>Description: Jedis枚举 . </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE DATE: 2017/8/31</P>
 * <P>UPDATE DATE: 2017/8/31</P>
 *
 * @author qixiafei
 * @version 0.0.3-SNAPSHOT
 * @since java 1.7.0
 */
public class JedisEnums {

    /**
     * 操作类型枚举.
     */
    public enum NxxxEnum {
        NX("NX", "不存在则设置"),
        // 如果存在则设置
        XX("XX", "存在则设置");

        private String code;
        private String desc;

        NxxxEnum(final String code, final String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

    /**
     * 超时时间单位枚举.
     */
    public enum EXPX {
        // 秒
        SECOND("EX", "秒"),
        // 毫秒
        MILLIS("PX", "毫秒");

        private String code;
        private String desc;

        EXPX(final String code, final String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }


}
