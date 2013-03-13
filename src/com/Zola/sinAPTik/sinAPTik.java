package com.Zola.sinAPTik;

import it.sauronsoftware.ftp4j.FTPClient;
import java.util.HashMap;
import java.util.Scanner;
class sinAPTik{
	String package_name;
	HashMap<String,String> packages;
        FTPClient uct_leg;
	public sinAPTik(String package_name,FTPClient ftp){
		this.package_name = package_name;
                this.uct_leg = ftp;
	}
	public void start(HashMap<String,String> list){
		packages = list;
		fix(this.package_name);
	}
	/*
	Recursive method for finding the dependecies
	and downloading a package
	*/
	public void fix(String fix_package){
		try{
			//getting the dependecies
			System.out.println("Package being resolved: "+fix_package);
			Process cmd1 = Runtime.getRuntime().exec("dpkg --info "+fix_package);//"+fix_package+".tmp");
			cmd1.waitFor();
				//if there are errors
			Scanner cmdOutput = new Scanner(cmd1.getInputStream());
			while(cmdOutput.hasNextLine()){
				String curr_line = cmdOutput.nextLine();
				//if found the line that contains the dependecies
				if (curr_line.startsWith(" Depends:")){
					curr_line=curr_line.replace("Depends: ","");
					String[] packs = curr_line.split(",");
					//for each dependecy, download it and fix it's dependecies
					for (String curr_pack:packs){
						curr_pack=curr_pack.trim();
						if (curr_pack.contains(">=")){
							curr_pack = curr_pack.replace("(>= ","");
							curr_pack = curr_pack.replace(")","");
						}
						String[] packAndversion = curr_pack.split(" ");
						String lib = packAndversion[0];
						for (String k:packages.keySet()){
							if (k.contains(lib)){
								String path =packages.get(k);
                                                                System.out.println("Downaloding: "+path);
                                                                java.io.File new_file =new java.io.File(k);
                                                                try{
                                                                uct_leg.download(path, new java.io.File(k));
                                                                //fix(k);
                                                                }catch(Exception e){
                                                                        e.printStackTrace();
                                                                }
							}
						}
					}
				}
			}
			System.out.println("\t-End of cmd output-\t");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
