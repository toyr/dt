package org.study.mq.activemq.dt.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.UUID;

/**
 * @author unisk1123
 * @Description
 * @create 2019/5/19
 */
@Repository
public class UserDao extends JdbcDaoSupport {

    public String insert(String userName) {
        String id = UUID.randomUUID().toString().replace("-", "");
        getJdbcTemplate().update("insert into t_user (id, user_name) values (?,?)",
                (PreparedStatement  ps) ->{
                    ps.setString(1, id);
                    ps.setString(2, userName);
                 }
        );
        return id;
    }

}
