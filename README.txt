App

* Developing

** GIT

	Just to avoid put the password every time

	$ git config --global credential.helper cache

	$ git remote -v
	openshift	ssh://53f85f24e0b8cd2cb7000125@europrigices-danidemi.rhcloud.com/~/git/europrices.git/
	origin	https://github.com/danidemi/europrices.git

	remove a local branch
	
	$ git branch -D openshift-master

	remove a remote branch

	$ git push origin --delete serverfix

	or in general...

	$ git push <remote> --delete <remote_branch>

	To push a local branch to a remote that is not the local's origin...

	$ git push openshift master

	Or more in general

	$ git push <remote> <local-branch>

* Build

	In base dir
	
	mvn clean install
	cd europrices-webapp
	mvn clean install assembly:single
	
* Deploy

	$OPENSHIFT_DATA_DIR - Where things can be placed

	$OPENSHIFT_DEPLOYMENTS_DIR/current/repo/ - Where the current deployed version is
	
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
		
