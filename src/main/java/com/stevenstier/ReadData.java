package com.stevenstier;

import java.time.Instant;

import com.stevenstier.model.Dao.HistorianValueDAO;
import com.stevenstier.model.Dao.RestHistorianValueDao;

public class ReadData 
{
  // path to where to find the one Wire device Files
  private static final String W1_DEVICE_PATH = "/sys/bus/w1/devices/";
  // device name with the above path //
  private static final String DEVICE_NAME = "28-0000081bf384";
  // Name of file to read
  private static final String FILE_NAME = "/w1_slave";
  // Variable ID of the variable to write to - see the variables table
  private static final Integer VAR_ID = 2;
  //location of the Historian API
  //private static final String BASE_URL = "http://prm-test.herokuapp.com/api/variableHistory";
  private static final String BASE_URL = "http://192.168.100.42:8080/prm/api/variableHistory/";
  private HistorianValueDAO historianValueDAO;
  private OneWireTemperatureFileReader oneWireTemperatureFileReader;

  public static void main(String[] args) {
    System.out.println("*****Temp Read Program Starting*****");
    ReadData application = new ReadData();
    application.run();
    System.out.println("*****Program Exiting*****");
  }
  public ReadData() {
    historianValueDAO = new RestHistorianValueDao(BASE_URL);
    oneWireTemperatureFileReader = new OneWireTemperatureFileReader(W1_DEVICE_PATH + DEVICE_NAME + FILE_NAME);
  }
  public void run(){
    try{
      Double lastMeasurement = null;
      System.out.println("Press Any Key to exit reading!.");
      do {
        // Get the current time - In GMT0 
        Instant sampletime = Instant.now();
        System.out.print("SampleTime(GMT0)=" + sampletime);
        // get the tempurature from the file
        String currentTemperature = oneWireTemperatureFileReader.readTemp();
        System.out.println("/Temp=" + currentTemperature  + " DegF");
        
        try {
          Double currentMeasurement = Double.parseDouble(currentTemperature);
          if (lastMeasurement != currentMeasurement){
          // set the quality here to 192 (good reading-see OPC spec) 
          // might wanna look at sending up quality to API
          int quality = 192;
          // send a post request to API to write the datapoint
          historianValueDAO.insertHistorianValue(VAR_ID, sampletime, currentMeasurement, quality);
          lastMeasurement = currentMeasurement;
        }
       
        } catch (NumberFormatException e){
          System.out.println("Error in Converting Temp to Float ");
        } catch (Exception e){ 
        System.out.println("Other exception when trying to write to API");
        }

        // wait 10 seconds
        Thread.sleep(10000);
      } while (System.in.available() == 0);
    } catch (Exception e){ 
      System.out.println("Other exception in Run");
    }
  }
}