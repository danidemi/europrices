package com.danidemi.europrice;

import java.io.File;

import org.hsqldb.server.Server;

public class Hsql {
	public static void main(String[] args) {
		
		File dbFolder = new File( new File("."), "db");
		if(!dbFolder.exists()){
			dbFolder.mkdir();
		}
		
		System.out.println(dbFolder.getAbsolutePath());
		
		
		String[] theargs = new String[]{ "-database.0", "file:" + new File( dbFolder, "europrices"),  "-dbname.0",  "europrices" };
		Server.main(theargs);
	}
}
