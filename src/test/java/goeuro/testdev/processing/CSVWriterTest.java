package goeuro.testdev.processing;

import goeuro.testdev.exceptions.ApplicationException;
import goeuro.testdev.resources.City;
import goeuro.testdev.resources.GeoPosition;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Integration tests for the writer.
 * <p>
 * Created by Benoit on 21/05/2016.
 */
public class CSVWriterTest {

    private static final String WRITE_FILE = "result.csv";
    private static final String DEFAULT_WRITE_FILE = "default.csv";
    private static City defaultCity;
    private static CSVWriter notConfiguredWriter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void before() {
        GeoPosition geoPosition = new GeoPosition();
        geoPosition.setLat(10F);
        geoPosition.setLng(10F);
        defaultCity = new City();
        defaultCity.setId(1L);
        defaultCity.setName("test");
        defaultCity.setType("location");
        defaultCity.setGeoPosition(geoPosition);

        notConfiguredWriter = CSVWriter
                .builder()
                .build();
    }

    @Before
    public void setup() throws IOException {
        Files.deleteIfExists(Paths.get(WRITE_FILE));
        Files.deleteIfExists(Paths.get(DEFAULT_WRITE_FILE));
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(WRITE_FILE));
        Files.deleteIfExists(Paths.get(DEFAULT_WRITE_FILE));
    }


    @Test
    public void whenNoParameterThenDefaultFile() throws Exception {
        notConfiguredWriter.execute();
        assertTrue("File not created", Files.exists(Paths.get(DEFAULT_WRITE_FILE)));
    }

    @Test
    public void whenNoParametersThenHeaders() throws Exception {
        notConfiguredWriter.execute();
        assertEquals("Incorrect number of lines", 1, Files.readAllLines(Paths.get(DEFAULT_WRITE_FILE)).size());
    }

    @Test
    public void whenFilenameOnlyThenExists() throws Exception {
        CSVWriter csvWriter = CSVWriter
                .builder()
                .writeTo(WRITE_FILE)
                .build();
        csvWriter.execute();
        assertTrue("File not created", Files.exists(Paths.get(WRITE_FILE)));
    }

    @Test
    public void whenFilenameOnlyThenHeader() throws Exception {
        CSVWriter csvWriter = CSVWriter
                .builder()
                .writeTo(WRITE_FILE)
                .build();
        csvWriter.execute();
        assertEquals("Incorrect number of lines", 1, Files.readAllLines(Paths.get(WRITE_FILE)).size());
    }

    @Test
    public void whenCitiesThenLinesPlus1() throws Exception {
        City[] cities = new City[2];
        cities[0] = defaultCity;
        cities[1] = defaultCity;
        CSVWriter csvWriter = CSVWriter
                .builder()
                .withCities(cities)
                .build();
        csvWriter.execute();
        assertEquals("Incorrect number of lines", 3, Files.readAllLines(Paths.get(DEFAULT_WRITE_FILE)).size());
    }

    @Test
    public void whenFileExistsThenReplace() throws Exception {
        City[] cities = new City[2];
        cities[0] = defaultCity;
        cities[1] = defaultCity;
        CSVWriter csvWriter = CSVWriter
                .builder()
                .withCities(cities)
                .build();
        csvWriter.execute();
        notConfiguredWriter.execute();
        assertEquals("Incorrect number of lines", 1, Files.readAllLines(Paths.get(DEFAULT_WRITE_FILE)).size());
    }

    @Test
    public void whenFileIncorrectThenException() throws Exception {
        CSVWriter csvWriter = CSVWriter
                .builder()
                .writeTo("/dsdsds/dsdsd")
                .build();
        expectedException.expect(ApplicationException.class);
        csvWriter.execute();
    }
}