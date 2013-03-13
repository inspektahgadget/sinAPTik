package com.Zola.sinAPTik;
import it.sauronsoftware.ftp4j.FTPClient;
import java.util.HashMap;
import java.io.File;
import java.util.Scanner;
class driver{
	static HashMap<String,String> map;
	public static void main(String[] args){
		String init = args[0];
		/*
		Reading the list of packages from list.xml
		Putting list in hashMap
		*/
		try{
                        //Creating and connecting to the ftp server
                        FTPClient client = new FTPClient();
                        client.connect("ftp.leg.uct.ac.za");
                        client.login("anonymous", "hello");
                        
			map = new HashMap<String,String>();
			File list = new File("list.txt");
			Scanner input = new Scanner(list);
			while(input.hasNextLine()){
				String line = input.nextLine();
				if (line.startsWith("/")){
					String package_name = line.substring(line.lastIndexOf("/")+1);
					map.put(package_name,line);
				}
			}
			sinAPTik install = new sinAPTik(init,client);
			install.start(map);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
