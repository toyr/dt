package org.study.mq.activemq.dt.service;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import org.study.mq.activemq.dt.constant.EventProcess;
import org.study.mq.activemq.dt.constant.EventType;
import org.study.mq.activemq.dt.dao.PointEventDao;
import org.study.mq.activemq.dt.entity.Event;
import org.study.mq.activemq.dt.entity.Point;
import org.study.mq.activemq.dt.exception.BusinessException;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author unisk1123
 * @Description
 * @create 2019/5/19
 */
@Service
public class PointEventService {

    @Resource
    private PointEventDao pointEventDao;

    @Resource
    private PointService pointService;

    public int newEvent(Event event) {
        if (event != null) {
            return pointEventDao.insert(event);
        } else {
            throw new BusinessException("入参不能为空");
        }
    }

    public List<Event> getPublishedEventList() {
        return pointEventDao.getByProcess(EventProcess.PUBLISHED.getValue());
    }

    public void executeEvent(Event event) {
        if (event != null) {
            String process = event.getProcess();
            if ((EventProcess.PUBLISHED.getValue().equals(process)) && (EventType.NEW_POINT.getValue().equals(event.getType()))) {
                Point point = JSON.parseObject(event.getContent(), Point.class);
                pointService.newPoint(point);
                event.setProcess(EventProcess.PROCESSED.getValue());
                pointEventDao.updateProcess(event);
            }
        }
    }
}
