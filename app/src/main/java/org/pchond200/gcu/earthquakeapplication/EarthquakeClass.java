package org.pchond200.gcu.earthquakeapplication;

public class EarthquakeClass {

    public String title;
    private String link;
    public String description;
    private String pubDate;
    private String category;
    private String geo_lat;
    private String geo_long;


    public EarthquakeClass(String atitle, String link, String description, String pubDate,
                           String category, String geo_lat, String geo_long)
    {
        this.title = atitle;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.category = category;
        this.geo_lat = geo_lat;
        this.geo_long = geo_long;
    }

    public EarthquakeClass()
    {
        title = getTitle();
        link = getLink();
        description = getDescription();
        pubDate = "";
        category = "";
        geo_lat = "";
        geo_long = "";
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String atitle) {
        title = atitle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String alink) {
        link = alink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String adescription) {
        description = adescription;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String apubDate) {
        pubDate = apubDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String acategory) {
        category = acategory;
    }

    public String getGeo_lat() {
        return geo_lat;
    }

    public void setGeo_lat(String ageo_lat) {
        geo_lat = ageo_lat;
    }

    public String getGeo_long() {
        return geo_long;
    }

    public void setGeo_long(String ageo_long) {
        geo_long = ageo_long;
    }


}
