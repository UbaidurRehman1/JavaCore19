package com.ubaid.bersling.database;

import java.sql.SQLException;
import java.sql.Connection;

import com.mysql.cj.jdbc.MysqlDataSource;


public class DataSourceConfig
{
	
	private Connection connection;
		
	public DataSourceConfig()
	{
		connection = getConnection();
		
		try
		{
			connection.setAutoCommit(false);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	
	//the following method is static method for all 
	//which return connection 
	public Connection getConnection()
	{
				
		MysqlDataSource dataSource = new MysqlDataSource();

		try
		{
			dataSource.setAllowPublicKeyRetrieval(true);
			dataSource.setUseSSL( false );
		    dataSource.setServerTimezone("GMT");
		    dataSource.setServerName("localhost");
		    dataSource.setDatabaseName("bersling");
		    dataSource.setPortNumber(3306);
		    dataSource.setUser("root");
		    dataSource.setPassword("password");
		    dataSource.setRewriteBatchedStatements(true);
		    Connection connection = dataSource.getConnection();
		    return connection;			
		}
		catch(SQLException exp)
		{
			exp.printStackTrace();
		}
		
		return null;
	}
		
}