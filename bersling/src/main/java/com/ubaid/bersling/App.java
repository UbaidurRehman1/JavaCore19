package com.ubaid.bersling;

import com.ubaid.bersling.benchmark.DocumentWithJSONTableBM;
import com.ubaid.bersling.database.DataSourceConfig;
import com.ubaid.bersling.documents.PopulateDocuments;
import com.ubaid.bersling.documents.PopulateDocumentsWithOutJSON;

public class App {
	public static void main(String [] args)
	{
		System.err.println("[Info]: Running Script");
		DataSourceConfig config = new DataSourceConfig();
		config.runScript();
		System.err.println("[Info]: Adding data in Document with JSON column table");
		new PopulateDocuments();
		System.err.println("[Info]: Running Script");
		new PopulateDocumentsWithOutJSON();
		System.err.println("[Info]: Benchmark start");
		new DocumentWithJSONTableBM();
	}
}
