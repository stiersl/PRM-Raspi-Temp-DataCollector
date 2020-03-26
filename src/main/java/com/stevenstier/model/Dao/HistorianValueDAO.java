package com.stevenstier.model.Dao;

import java.time.Instant;

public interface HistorianValueDAO {

  public boolean insertHistorianValue(long varId, Instant sampletime, Double varValue, int Quality);
}
