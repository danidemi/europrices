App

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

* RedHat Console (rhc)

	rhc setup 
		First setup.
	
	rhc apps
		List available apps.
		
	rhc ssh <app_name>
		Access the "app" through ssh.
		
* Once logged

	$OPENSHIFT_DATA_DIR
		
