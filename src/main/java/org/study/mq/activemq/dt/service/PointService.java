package org.study.mq.activemq.dt.service;

import org.springframework.stereotype.Service;
import org.study.mq.activemq.dt.dao.PointDao;
import org.study.mq.activemq.dt.entity.Point;

import javax.annotation.Resource;

/**
 * @author unisk1123
 * @Description
 * @create 2019/5/19
 */
@Service
public class PointService {

    @Resource
    private PointDao pointDao;

    public String newPoint(Point point) {
        return pointDao.insert(point);
    }
}
