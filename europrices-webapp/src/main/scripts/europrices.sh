#!/bin/bash
# Launch script
#nohup java -cp "lib/*:conf" com.danidemi.europrice.EuroPricesWebApp prod &> /dev/null &
java -cp "lib/*:conf" com.danidemi.europrice.EuroPricesWebApp prod
