package goeuro.testdev.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Representation of the lat/long for a city.
 * <p>
 * Created by Benoit on 21/05/2016.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoPosition {

    @JsonProperty(value = "latitude", defaultValue = "0")
    private Float lat;
    @JsonProperty(value = "longitude", defaultValue = "0")
    private Float lng;

    @Override
    public String toString() {
        return lat + "," + lng;
    }
}
