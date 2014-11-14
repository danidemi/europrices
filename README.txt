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
	
	from local...
	
	$ mvn clean install assembly:single -Popenshift
	$ rhc app stop europrices
	$ rhc scp europrices upload target/europrice-webapp-1.0-SNAPSHOT-openshift.tar.gz app-root/data
	$ scp target/europrice-webapp-1.0-SNAPSHOT-openshift.tar.gz 53f85f24e0b8cd2cb7000125@europrices-danidemi.rhcloud.com:app-root/data
	$ rhc app start europrices
	
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
		
	rhc authorization		
		You can check authorization tokens created under your account by running. 
	
	rhc account logout
		Tokens can be deleted by running
		
* Software

	$ java -version
	java version "1.7.0_71"
	OpenJDK Runtime Environment (rhel-2.5.3.1.el6-i386 u71-b14)
	OpenJDK Server VM (build 24.65-b04, mixed mode)

	$ mvn -version
	Apache Maven 3.0.4 (r1232336; 2012-12-18 14:36:37-0500)
	Maven home: /usr/share/java/apache-maven-3.0.4
	Java version: 1.7.0_71, vendor: Oracle Corporation
	Java home: /usr/lib/jvm/java-1.7.0-openjdk-1.7.0.71/jre
	Default locale: en_US, platform encoding: UTF-8
	OS name: "linux", version: "2.6.32-504.el6.x86_64", arch: "i386", family: "unix"

		
* Once logged

	$OPENSHIFT_DATA_DIR
	
* References

	DIY cartridge: https://developers.openshift.com/en/diy-overview.html
		
