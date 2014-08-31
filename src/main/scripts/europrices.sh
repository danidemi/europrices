#!/bin/bash
# Launch script
nohup java -cp lib/*:conf com.danidemi.europrice.EuroPrices &> /dev/null &
