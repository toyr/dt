package org.study.mq.activemq.dt.listener;

import org.springframework.util.StringUtils;
import org.study.mq.activemq.dt.constant.EventProcess;
import org.study.mq.activemq.dt.constant.EventType;
import org.study.mq.activemq.dt.entity.Event;
import org.study.mq.activemq.dt.exception.BusinessException;
import org.study.mq.activemq.dt.service.PointEventService;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author unisk1123
 * @Description
 * @create 2019/5/19
 */
public class PointMessageListener implements MessageListener {

    @Resource
    private PointEventService pointEventService;
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                TextMessage txtMsg = (TextMessage) message;
                String eventContent = txtMsg.getText();
                System.out.println("队列监听器收到文本消息：" + eventContent);

                if (!StringUtils.isEmpty(eventContent)) {
                    // 新增事件
                    Event event = new Event();
                    event.setType(EventType.NEW_POINT.getValue());
                    event.setProcess(EventProcess.PUBLISHED.getValue());
                    event.setContent(eventContent);
                    pointEventService.newEvent(event);
                }
            } catch (JMSException e) {
                throw new BusinessException("接收消息处理过程异常");
            }
        } else {
            throw new IllegalArgumentException("只支持TextMessage消息类型");
        }
    }
}
