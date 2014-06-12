package com.mjoell.twitchchatcounter;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class MysqlDS {
    public static DataSource getDataSource() {
    	BasicDataSource ds = new BasicDataSource();
    	
    	ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/twitch");
		ds.setUsername(TwitchChatCounter.mysqluser);
		ds.setPassword(TwitchChatCounter.mysqlpassword);
    	
    	return ds;
    }
}
