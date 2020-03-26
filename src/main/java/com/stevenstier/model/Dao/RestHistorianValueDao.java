package com.stevenstier.model.Dao;

import java.time.Instant;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.stevenstier.prm.model.HistorianValue;

@Component
public class RestHistorianValueDao implements HistorianValueDAO{
 private String base_url;
  public RestHistorianValueDao(String base_url){
    this.base_url = base_url;
  }
  private RestTemplate restTemplate = new RestTemplate();

  @Override
  public boolean insertHistorianValue(long varId, Instant sampletime, Double varValue, int quality) {
    
    boolean result = false;
    
    HistorianValue historianvalueToInsert = new HistorianValue();
    historianvalueToInsert.setVarId(varId);
    
    historianvalueToInsert.setTimeStamp(sampletime.toString());
    historianvalueToInsert.setVarValue(String.valueOf(varValue));
    historianvalueToInsert.setQuality(quality);
    
    String url = base_url + "?id=" + varId;
    result = restTemplate.postForObject(url, historianvalueToInsert, Boolean.class);
    
    return result;
  }
}