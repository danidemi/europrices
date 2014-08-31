#!/bin/bash
# Launch script
#nohup java -cp lib/*:conf:extracp com.danidemi.europrice.EuroPrices &> /dev/null &
java -cp lib/*:conf:extracp com.danidemi.europrice.EuroPrices
