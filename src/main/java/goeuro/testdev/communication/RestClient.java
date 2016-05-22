package goeuro.testdev.communication;

import goeuro.testdev.exceptions.ApplicationException;
import goeuro.testdev.resources.City;

/**
 * Interface offering methods for contacting a remote endpoints.
 * <p>
 * Created by Benoit on 21/05/2016.
 */
public interface RestClient {

    /**
     * Recover all cities corresponding to the name provided.
     *
     * @param cityName name of the city to be searched
     * @return Array of cities
     * @throws ApplicationException if a problem gathering the cities occurs
     */
    City[] getCitiesFromName(String cityName) throws ApplicationException;
}
