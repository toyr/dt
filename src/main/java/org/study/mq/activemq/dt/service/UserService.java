package org.study.mq.activemq.dt.service;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.study.mq.activemq.dt.constant.EventProcess;
import org.study.mq.activemq.dt.constant.EventType;
import org.study.mq.activemq.dt.dao.UserDao;
import org.study.mq.activemq.dt.entity.Event;
import org.study.mq.activemq.dt.entity.Point;

import javax.annotation.Resource;

/**
 * @author unisk1123
 * @Description
 * @create 2019/5/19
 */
@Service
public class UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private UserEventService userEventService;

    @Transactional
    public void newUser(String userName, Integer pointAmount) {
        // 1.保存用户
        String userId = userDao.insert(userName);

        // 2.新增事件
        Event event = new Event();
        event.setType(EventType.NEW_USER.getValue());
        event.setProcess(EventProcess.NEW.getValue());
        Point point = new Point();
        point.setUserId(userId);
        point.setAmount(pointAmount);
        // 将对象转换成Json字符串保存到事件表的content字段中
        event.setContent(JSON.toJSONString(point));
        userEventService.newEvent(event);
    }
}
