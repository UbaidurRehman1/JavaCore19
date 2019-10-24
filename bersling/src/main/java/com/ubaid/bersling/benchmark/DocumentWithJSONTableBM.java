package com.ubaid.bersling.benchmark;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.ubaid.bersling.database.DataSourceConfig;

/**
 * this class show the benchmarks of both tables (with JSON and without JSON)
 * @author UbaidurRehman
 *
 */
public class DocumentWithJSONTableBM
{
	private DataSourceConfig config;
	public DocumentWithJSONTableBM() {
		config = new DataSourceConfig();

		System.err.println("\n\nBenchmark for Document Table having JSON Field");
		for (int i = 1; i <= 128; i = i * 2)
		{
			simulate(i, true);
		}

		System.err.println("\n\nBenchmark for Document Table which do not having JSON Field");
		for (int i = 1; i <= 128; i = i * 2)
		{
			simulate(i, false);
		}

	}
	
	/**
	 * this method create the concurrent instances and run them in the Executors service (a service which give us threads pool)
	 * when executor service done its work then we calculate the time 
	 * and print it
	 * @param totalThreads
	 * @param isJSONTable
	 */
	private void simulate(int totalThreads, boolean isJSONTable) {
		//creating fixed thread pool of given thread
		ExecutorService service = Executors.newFixedThreadPool(totalThreads);
		System.err.println("\n\nSimulating for " + totalThreads +  " concurrent query (retrieve all the entries with difficulty: '3')");

		//starting time
		long start = System.currentTimeMillis();
		long end = 0;
		
		//adding the queryThread (a class which is responsible to execute query)
		//getQueryThread method return on isJSONTable (a boolean which return true if we want for json field table)
		for (int i = 0; i < totalThreads; i++)
		{
			service.execute(getQueryThread(isJSONTable));
		}
		service.shutdown();
		try
		{
			//waiting for all threads to finish their work
			if(service.awaitTermination(1, TimeUnit.HOURS))
				end = System.currentTimeMillis();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		long milliseconds = end - start;
		double seconds = (double) milliseconds / (double) 1000.0;
		String info = String.format("The total time for %d concurrent queries "
				+ " is %.6f seconds\n\n", totalThreads, seconds);
		System.err.println(info);
		
	}
	
	/**
	 * 
	 * @param json a boolean variable
	 * @return if json is true then returning class which execute on json field table 
	 *         if json is false then returing class which execute table having no json field
	 */
	private QueryThread getQueryThread(boolean json)
	{
		return json ? new JSONTableBM() : new WithoutJSONTableBM();
	}
	
	/**
	 * abstract class which implements Runnable interface for multithreading
	 * @author UbaidurRehman
	 *
	 */
	private abstract class QueryThread implements Runnable
	{

		@Override
		public void run()
		{
			Connection con = config.getConnection();
			try
			{
				Statement st = con.createStatement();
				st.executeQuery(getQuery());
				con.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			
		}
		
		abstract String getQuery();		
	}
	
	private class JSONTableBM extends QueryThread
	{
		private String q = "select id, metadata ->> '$.difficulty' difficulty from documents where metadata ->> '$.difficulty' = 3";

		@Override
		String getQuery()
		{
			return q;
		}		
	}
	
	private class WithoutJSONTableBM extends QueryThread
	{

		private String q = "select * from documentsNoJSON inner join metaData mD on documentsNoJSON.id = mD.id where difficulty = 3";

		@Override
		String getQuery()
		{
			return q;
		}		
	}

}



