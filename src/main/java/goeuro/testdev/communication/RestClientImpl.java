package goeuro.testdev.communication;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import goeuro.testdev.exceptions.ApplicationException;
import goeuro.testdev.exceptions.ErrorCode;
import goeuro.testdev.resources.City;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

/**
 * Class using UniRest to contact the endpoint API and return the results.
 * <p>
 * Created by Benoit on 21/05/2016.
 */
@Log4j2
public class RestClientImpl implements RestClient {

    private static final String URL_SERVICE = "http://api.goeuro.com/api/v2/position/suggest/en/{CITY_NAME}";
    private static final long DEFAULT_CONNECTION_TIMEOUT = 10000;
    private static final long DEFAULT_SOCKET_TIMEOUT = 30000;

    @Override
    public City[] getCitiesFromName(String cityName) throws ApplicationException {
        String trimmedCity = cityName.trim();
        log.debug("Receiving city {} ", cityName);
        try {
            // configuration of the Unirest client
            Unirest.setObjectMapper(new CustomObjectMapper());
            Unirest.setTimeouts(DEFAULT_CONNECTION_TIMEOUT, DEFAULT_SOCKET_TIMEOUT);
            // request
            log.info("Rest request sent for {}", cityName);
            HttpResponse<City[]> response = Unirest
                    .get(URL_SERVICE)
                    .header("Content-Type", "application/json")
                    .routeParam("CITY_NAME", trimmedCity)
                    .asObject(City[].class);
            // results
            log.debug("Returning results");
            return (City[]) checkGetResults(response);
        } catch (UnirestException ex) {
            log.error("Problem contacting the API: {}", ex.getMessage());
            throw new ApplicationException(ErrorCode.COMMUNICATION_ERROR, "Problem contacting the API: " + ex.getMessage());
        } finally {
            shutdownUnirest();
        }
    }

    private Object checkGetResults(HttpResponse<?> response) throws ApplicationException {
        if (response.getStatus() != 200) {
            throw new ApplicationException(ErrorCode.COMMUNICATION_ERROR, "Invalid return code received: " + response.getStatus());
        }
        return response.getBody();
    }

    private void shutdownUnirest() {
        try {
            Unirest.shutdown();
        } catch (IOException ex) {
            log.warn("Failing closing Unirest: {}", ex.getMessage());
        }
    }
}
