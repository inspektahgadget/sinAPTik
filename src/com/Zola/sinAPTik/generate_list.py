from ftplib import FTP
import os
def main():
	uct_leg = FTP('ftp.leg.uct.ac.za');
	uct_leg.login();
	print(uct_leg.getwelcome());
	#going to the debian dir
	uct_leg.cwd('pub/linux/debian/')
	uct_leg.dir();
if __name__=='__main__':
	main()
