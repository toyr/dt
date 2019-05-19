package org.study.mq.activemq.dt.service;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.study.mq.activemq.dt.constant.EventProcess;
import org.study.mq.activemq.dt.constant.EventType;
import org.study.mq.activemq.dt.dao.UserEventDao;
import org.study.mq.activemq.dt.entity.Event;
import org.study.mq.activemq.dt.exception.BusinessException;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.List;

/**
 * @author unisk1123
 * @Description
 * @create 2019/5/19
 */
@Service
public class UserEventService {

    @Resource
    private UserEventDao userEventDao;

    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;

    @Resource(name = "topicDistributedTransaction")
    private Destination topic;

    public int newEvent(Event event) {
        if (event != null) {
            return userEventDao.insert(event);
        } else {
            throw new BusinessException("入参不能为空");
        }
    }

    public List<Event> getNewEventList() {
        return userEventDao.getByProcess(EventProcess.NEW.getValue());
    }

    public void executeEvent(Event event) {
        if (event != null) {
            String process = event.getProcess();
            if (EventProcess.NEW.getValue().equals(process) && (EventType.NEW_USER.getValue().equals(event.getType()))) {
                String content = event.getContent();
                jmsTemplate.send(topic, (Session session) -> {
                    TextMessage msg = session.createTextMessage();
                    msg.setText(content);
                    return msg;
                });

                event.setProcess(EventProcess.PUBLISHED.getValue());
                userEventDao.updateProcess(event);
            }
        }
    }
}
