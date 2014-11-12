App

* Developing

	git remote -v
	openshift	ssh://53f85f24e0b8cd2cb7000125@europrices-danidemi.rhcloud.com/~/git/europrices.git/
	origin	https://github.com/danidemi/europrices.git


* Build

	In base dir
	
	mvn clean install
	cd europrices-webapp
	mvn clean install assembly:single
	
* Deploy
	$OPENSHIFT_DATA_DIR
	
* Run

** WebApp

	start
	nohup java -cp "lib/*:conf" com.danidemi.europrice.EuroPricesWebApp prod &> /dev/null &
	
	end
	kill $(jps | grep EuroPricesWebApp | awk '{print $1}')
	
	

* URLs

	http://europrices.danidemi.com/





OpenShift

* Action Hooks

	The script needs to be executable; run the chmod x <scriptname> command to ensure this.

* RedHat Console (rhc)

	rhc setup 
		First setup.
	
	rhc apps
		List available apps.
		
	rhc ssh <app_name>
		Access the "app" through ssh.
		
* Once logged

	$OPENSHIFT_DATA_DIR
	
* References

	DIY cartridge: https://developers.openshift.com/en/diy-overview.html
		
