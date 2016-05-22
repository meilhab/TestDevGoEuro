package goeuro.testdev.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Short representation of a City.
 * <p>
 * Created by Benoit on 21/05/2016.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class City {

    @JsonProperty(value = "_id")
    private Long id;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "type")
    private String type;
    @JsonProperty(value = "geo_position")
    private GeoPosition geoPosition;

    @Override
    public String toString() {
        return id + ","
                + name + ","
                + type + ","
                + (geoPosition != null ? geoPosition.toString() : "0,0");
    }
}
