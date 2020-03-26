# PRM-Raspi-Temp-DataCollector
Process Reliability Monitor - Raspberry PI Temperature Data Collector
Java Application which runs on a RaspPi which
 monitors Temperature using a DS18B20 Probe conntected 
 via 1 Wire protocol. - This will send data to the PRM - Historian Server via API.
 --NOTE--- This is currently in development. -
Tested Hardware--
 RaspBerry PI 3 Model B+ ( loaded with Raspian Buster (10))
 installed java, maven,  
 sudo apt-get install oracle-java8-installer
 
 DS18B20 Temperature Probe 

Wiring
    White ---- GND
    Orange/white ---- 3.3V
   Blue/white ---- GPI04
(add a 4.7 KOhm Resister between GPIO4 and 3.3V)
--- To get the 1 wire interface working

- Enable the 1-Wire Interface remote GPIO Interface
sudo modprobe w1-gpio
sudo modprobe w1-therm
cd /sys/bus/w1/devices
ls -- you should see a folder like 28-0000081bf384 (note this folder you will need it for the configuration)
cd 28-0000081bf384
cat w1_slave (displays the contents of the file -- this is where the temperature is written to and where the application will read from)
 the t=xxxxx represent the temperature measurement in DEG C *1000 (i.e 20187 = 20.187 Deg C). this is the string were going to be looking for
 
