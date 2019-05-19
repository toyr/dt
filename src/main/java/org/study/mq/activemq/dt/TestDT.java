package org.study.mq.activemq.dt;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.study.mq.activemq.dt.service.UserService;

/**
 * @author unisk1123
 * @Description
 * @create 2019/5/19
 */
public class TestDT {

    private ApplicationContext context;

    @Before
    public void setup() {
        context = new ClassPathXmlApplicationContext("application.xml");
    }

    @Test
    public void newUser() throws InterruptedException {
        UserService userService = (UserService) context.getBean("userService");
        userService.newUser("ceshi", 1500);
        Thread.sleep(100000);
    }
}
