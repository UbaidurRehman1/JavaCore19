package com.ubaid.bersling.database;

import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;

import com.mysql.cj.jdbc.MysqlDataSource;


/**
 * This class give the connection
 * @author UbaidurRehman
 *
 */
public class DataSourceConfig
{
	private String databaseName;
	private String password;

	/**
	 * getting database name and password from the properties file and set the database name and password
	 */
	public DataSourceConfig()
	{
		File file = new File(getClass().getClassLoader().getResource("app.properties").getFile());
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while(line != null)
			{
				String[] temp = line.split("=");
				if (temp[0].contentEquals("database.name"))
					databaseName = temp[1];
				else if (temp[0].contentEquals("database.password"))
					password = temp[1];
				line = reader.readLine();
			}
			
			reader.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * this method run the sql.data script
	 */
	public void runScript()
	{
		ScriptRunner sr = new ScriptRunner(getConnection());
		File file = new File(getClass().getClassLoader().getResource("data.sql").getFile());
		try
		{
			Reader reader = new BufferedReader(new FileReader(file));
			sr.runScript(reader);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	
	/**
	 * 
	 * @return an instance of connection
	 */
	public Connection getConnection()
	{
				
		MysqlDataSource dataSource = new MysqlDataSource();

		try
		{
			dataSource.setAllowPublicKeyRetrieval(true);
			dataSource.setUseSSL( false );
		    dataSource.setServerTimezone("GMT");
		    dataSource.setServerName("localhost");
		    dataSource.setDatabaseName(databaseName);
		    dataSource.setPortNumber(3306);
		    dataSource.setUser("root");
		    dataSource.setPassword(password);
		    dataSource.setRewriteBatchedStatements(true);
		    Connection connection = dataSource.getConnection();
		    return connection;			
		}
		catch(SQLException exp)
		{
			System.err.println("[Error]: Please create bersling database");
			exp.printStackTrace();
		}
		
		return null;
	}
	
	public String getDatabaseName() {
		return databaseName;
	}

	public String getPassword() {
		return password;
	}


}