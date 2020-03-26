package com.stevenstier;

import java.time.Instant;

import com.stevenstier.model.Dao.HistorianValueDAO;
import com.stevenstier.model.Dao.RestHistorianValueDao;

public class App 
{
  // path to where to find the one Wire device Files
  private static final String W1_DEVICE_PATH = "/sys/bus/w1/devices/";
  // device name //
  private static final String DEVICE_NAME = "28-0000081bf384";
  // Name of file to read
  private static final String FILE_NAME = "/w1_slave";
  private static final Integer VAR_ID = 2;
  //location of the Historian API
  //private static final String BASE_URL = "http://prm-test.herokuapp.com/api/variableHistory";
  private static final String BASE_URL = "http://192.168.100.136:8080/PRM-Historian/api/variableHistory";
  public static void main(String[] args) {
    System.out.println("*****Temp Read Program Starting*****");
    HistorianValueDAO historianValueDAO = new RestHistorianValueDao(BASE_URL);
    OneWireTemperatureFileReader myTempFileReader = new OneWireTemperatureFileReader(W1_DEVICE_PATH + DEVICE_NAME + FILE_NAME);
    try {
      System.out.println("Press Any Key to exit reading!.");
      do {
        
        System.out.println("Temp=" + myTempFileReader.readTemp() + " DegF");
          // Get the current time
          Instant sampletime = Instant.now();
          System.out.println("SampleTime=" + sampletime);
          try {
            Double varValue = Double.parseDouble(myTempFileReader.readTemp() );
            int quality = 192;
            historianValueDAO.insertHistorianValue(VAR_ID, sampletime, varValue, quality);
          } catch (Exception e){
            System.out.println("exception in converting to Double.");
          }
        // wait 10 seconds
        Thread.sleep(10000);
      } while (System.in.available() == 0);
    } catch (Exception e) {
      System.out.println("Exception in Main:");
    }
    System.out.println("*****Program Exiting*****");
  }


 }
