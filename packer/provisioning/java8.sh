#!/bin/bash -x

#that makes 'add-apt-repository' available
sudo apt-get -y install software-properties-common
  
#add the java repo without confirmation  
sudo add-apt-repository ppa:webupd8team/java -y

#prevent java installer to ask for confirmation 
echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections

#actually install java
sudo apt-get update
sudo apt-get -y install oracle-java8-installer

# sudo update-java-alternatives -s java-8-oracle (this seems to be not needed)
sudo apt-get -y install oracle-java8-set-default

