package com.bouncingdata.plfdemo.datastore.pojo.model;

import java.util.List;
import java.util.Set;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class Scraper extends BcDataScript {
  @Persistent(mappedBy="scraper")
  private List<Dataset> datasets;
  
  
  @Persistent(table="Scraper_tags")
  @Join (column="id_OID")
  @Element (column="id_EID")
  private   Set<Tag> tags;
  
  public List<Dataset> getDatasets() {
    return datasets;
  }

  public void setDatasets(List<Dataset> datasets) {
    this.datasets = datasets;
  }

  @Override
  public Set<Tag> getTags() {
    return tags;
  }
  @Override
  public void setTags(Set<Tag> tags) {
    this.tags = tags;
  }
}
