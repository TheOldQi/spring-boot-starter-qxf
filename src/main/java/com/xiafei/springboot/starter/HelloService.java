package com.xiafei.springboot.starter;

/**
 * <P>Description: . </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE DATE: 2018/5/8</P>
 * <P>UPDATE DATE: 2018/5/8</P>
 *
 * @author qixiafei
 * @version 1.0
 * @since java 1.8.0
 */
public class HelloService {

    private String msg;

    public String sayHello() {
        return "Hello " + msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }
}
