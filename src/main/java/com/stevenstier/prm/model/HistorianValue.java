package com.stevenstier.prm.model;

public class HistorianValue {
  private long varId;
  private String varValue;
  private String timeStamp;
  private Integer quality;
  
  public long getVarId() {
    return varId;
  }
  public void setVarId(Long varId) {
    this.varId = varId;
  }
  public String getVarValue() {
    return varValue;
  }
  public void setVarValue(String varValue) {
    this.varValue = varValue;
  }
  public String getTimeStamp() {
    return timeStamp;
  }
  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }
  public Integer getQuality() {
    return quality;
  }
  public void setQuality(Integer quality) {
    this.quality = quality;
  }
  
  
}
