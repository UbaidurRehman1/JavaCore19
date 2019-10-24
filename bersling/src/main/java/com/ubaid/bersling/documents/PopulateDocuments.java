package com.ubaid.bersling.documents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import com.ubaid.bersling.database.DataSourceConfig;

/**
 * this class populate the document with json table 
 * @author UbaidurRehman
 *
 */
public class PopulateDocuments {
	
	//Query which is used for prepared statement
	String q_document = "insert into documents(id, metadata) values(?, JSON_SET('{}', '$.difficulty', ?))";
	
	//Constructor
	public PopulateDocuments()
	{
		//getting data source
		DataSourceConfig config = new DataSourceConfig();
		
		//getting connection
		Connection con = config.getConnection();

		try
		{
			//getting prepared statement
			PreparedStatement st = con.prepareStatement(q_document);

			// loop which 10M in values into the prepared statement
			// and then executing the batch
			for(int i = 1; i <= 10000000; i++)
			{
				st.setString(1, Integer.toString(i));
				st.setString(2, Integer.toString(i));
				st.addBatch();
				if(i % 100000 == 0)
				{
					System.err.println("[INFO]: adding " + i + " entries in JSON document table");
				}

			}
			System.err.println("[INFO]: Adding Batch in JSON document table");
			int[] added = st.executeBatch();
			System.err.println("[INFO]: Added: " + Arrays.asList(added));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
}

