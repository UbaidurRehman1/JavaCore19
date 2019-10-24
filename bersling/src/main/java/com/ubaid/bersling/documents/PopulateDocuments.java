package com.ubaid.bersling.documents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import com.ubaid.bersling.database.DataSourceConfig;

public class PopulateDocuments {
	String q_document = "insert into documents(id, metadata) values(?, JSON_SET('{}', '$.difficulty', ?))";
//	String documents_drop_index = "ALTER TABLE `documents` drop primary key";
//	String documents_add_index = "ALTER TABLE `documents` add primary key(id)";
	
	public PopulateDocuments()
	{
		DataSourceConfig config = new DataSourceConfig();
		Connection con = config.getConnection();

		try
		{
			PreparedStatement st = con.prepareStatement(q_document);

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

