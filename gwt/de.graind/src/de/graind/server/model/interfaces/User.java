package de.graind.server.model.interfaces;

import java.io.Serializable;
import java.util.Date;

public interface User extends Serializable {

  public int getId();

  public String getName();

  public TemplateType getDefaultType();

  public int getRefreshrate();

  public Date getLastRefresh();

}
