package goeuro.testdev.processing;

import goeuro.testdev.exceptions.ApplicationException;
import goeuro.testdev.exceptions.ErrorCode;
import goeuro.testdev.resources.City;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class with builder allowing the creation of a csv file containing data regarding cities provided.
 * <p>
 * Created by Benoit on 21/05/2016.
 */
@Log4j2
public class CSVWriter {

    private static final String HEADERS = "_id,name,type,latitude,longitude";

    /**
     * Cities to be written in the csv file
     */
    private City[] withCities;
    /**
     * File where the csv data will be written. If none, default name will be "default.csv".
     */
    private String writeTo = "default.csv";

    @Builder
    public CSVWriter(City[] withCities, String writeTo) {
        this.withCities = withCities;
        if (writeTo != null && !writeTo.isEmpty()) {
            this.writeTo = writeTo;
        }
    }

    /**
     * Launch the writing process for the cities provided.
     *
     * @throws ApplicationException file system exception detected when writing to the csv file
     */
    public void execute() throws ApplicationException {
        log.info("Building the CSV file");
        StringBuilder builder = buildCSVFile(withCities);
        try {
            log.info("Writing the CSV file");
            Files.write(Paths.get(writeTo), builder.toString().getBytes());
            log.debug("CSV file written");
        } catch (IOException | UnsupportedOperationException | SecurityException ex) {
            throw new ApplicationException(ErrorCode.FILE_SYSTEM_ERROR, "Problem writing the csv file: " + ex.getMessage());
        }
    }

    private static StringBuilder buildCSVFile(City[] cities) {
        StringBuilder builder = new StringBuilder(HEADERS);
        builder.append("\n");
        if (cities != null) {
            for (City city : cities) {
                log.debug("City found ({})", city.toCSV());
                builder.append(city.toCSV());
                builder.append("\n");
            }
        }
        return builder;
    }
}
