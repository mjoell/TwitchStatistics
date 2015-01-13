package com.twitchstatistics.twitchstatistics;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class MysqlDS {
    public static DataSource getDataSource() {
    	BasicDataSource ds = new BasicDataSource();
    	
    	ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl(String.format("jdbc:mysql://{0}:{1}/{2}", TwitchStatistics.mysqlhost, TwitchStatistics.mysqlport, TwitchStatistics.mysqldb));
		ds.setUsername(TwitchStatistics.mysqluser);
		ds.setPassword(TwitchStatistics.mysqlpassword);
    	
    	return ds;
    }
}
