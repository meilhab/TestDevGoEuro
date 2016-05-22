package goeuro.testdev;

import goeuro.testdev.communication.RestClient;
import goeuro.testdev.communication.RestClientImpl;
import goeuro.testdev.exceptions.ApplicationException;
import goeuro.testdev.exceptions.ErrorCode;
import goeuro.testdev.processing.CSVWriter;
import goeuro.testdev.resources.City;
import lombok.extern.log4j.Log4j2;

/**
 * Entry point of the application. Only one argument should be provided for a correct behaviour.
 * <p>
 * Created by Benoit on 21/05/2016.
 */
@Log4j2
public class Application {

    public static void main(String[] args) {
        log.info("Starting the programme");
        if (args.length != 1) {
            outputErrorMessage(ErrorCode.INVALID_NUMBER_PARAMETER, "Usage: java -jar GoEuroTest.jar \"CITYNAME\"");
        }

        try {
            RestClient restClient = new RestClientImpl();
            City[] jsonForCity = restClient.getCitiesFromName(args[0]);
            CSVWriter
                    .builder()
                    .withCities(jsonForCity)
                    .writeTo("results.csv")
                    .build()
                    .execute();
            log.info("Ending the programme");
        } catch (ApplicationException ex) {
            outputErrorMessage(ex.getErrorCode(), ex.getReason());
        }
    }

    private static void outputErrorMessage(ErrorCode errorCode, String message) {
        log.error("An error has been detected during the execution");
        log.error(message);
        System.exit(errorCode.getValue());
    }
}
