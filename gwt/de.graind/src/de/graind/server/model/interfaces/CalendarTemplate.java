package de.graind.server.model.interfaces;

import java.io.Serializable;

public interface CalendarTemplate extends Serializable {

  public TemplateType getType();

  public String getTodaysPicture();

}
