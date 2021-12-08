package test;

import org.junit.jupiter.api.*;
import toy.Condition;
import toy.IToy;
import toy.ToyFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * A JUnit test class for the toy.car class.
 *
 * @author RIT CS
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRCCar {
    /**
     * car
     */
    private static IToy car;
    /**
     * Used to test that expected System.out print's happen
     */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeAll
    public static void setUp() {
        // set seed to the random number generator
        ToyFactory.setSeed(1);
        // creating a car with random values for the name, msrp, etc.
        car = ToyFactory.randomRCCar();
    }

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(System.out);
    }

    @Test
    @Order(1)
    public void testInit() {
        Assertions.assertEquals("METAKOO RC",
                car.getName(), "car name");
        Assertions.assertEquals(Condition.MINT,
                car.getCondition(), "car condition");
        Assertions.assertEquals(0,
                car.getDegradationLevel(),
                "car level of degradation");
        Assertions.assertEquals(6000000,
                car.getProductCode(),
                "car product code");
        Assertions.assertEquals(34.05,
                car.getMSRP(),
                "car msrp");
        Assertions.assertEquals(34.05,
                car.getResaleValue(),
                "car resale value");
        Assertions.assertEquals("METAKOO RC [product code=6000000, MSRP=34.05, degradation level=0%, " +
                        "condition=MINT, resale value=34.05, battery type=C, number of batteries=3, " +
                        "battery level=100%, speed=266]",
                car.toString(),
                "car toString");
    }

    @Test
    @Order(2)
    /**
     * Create a new car with a unique code product
     */
    public void testInit2() {
        IToy car2 = ToyFactory.randomRCCar();
        Assertions.assertEquals(6000001, car2.getProductCode());
    }

    @Test
    @Order(3)
    public void testPlay() {
        // call play and compare the play output
        car.play();
        Assertions.assertEquals("METAKOO RC races in circles at 266 mph!"
                        + System.getProperty("line.separator") +
                        "After play, METAKOO RC's condition is NEAR_MINT" +
                        System.getProperty("line.separator"),
                outContent.toString());
        // then compare the toString output
        Assertions.assertEquals("METAKOO RC [product code=6000000, MSRP=34.05, " +
                        "degradation level=15%, condition=NEAR_MINT, resale value=30.65, " +
                        "battery type=C, number of batteries=3, battery level=70%, speed=266]",
                car.toString());
    }

    @Test
    @Order(4)
    public void testReplaceBatteries() {
        while (car.getCondition() != Condition.POOR) {
            car.play();
        }
        Assertions.assertEquals("METAKOO RC races in circles at 266 mph!"
                        + System.getProperty("line.separator") +
                        "After play, METAKOO RC's condition is GOOD" +
                        System.getProperty("line.separator") +
                        "METAKOO RC races in circles at 266 mph!"
                        + System.getProperty("line.separator") +
                        "After play, METAKOO RC's condition is GOOD" +
                        System.getProperty("line.separator") +
                        "METAKOO RC races in circles at 266 mph!" +
                        System.getProperty("line.separator") +
                        "After play, METAKOO RC's condition is FAIR" +
                        System.getProperty("line.separator") +
                        "The batteries are dead! Let's replace them..."
                        + System.getProperty("line.separator") +
                        "METAKOO RC races in circles at 266 mph!"
                        + System.getProperty("line.separator") +
                        "After play, METAKOO RC's condition is POOR"
                        + System.getProperty("line.separator"),
                outContent.toString());
    }

    @Test
    @Order(6)
    public void testOverPlay() {
        while (car.getDegradationLevel() < 100) {
            car.play();
        }
        Assertions.assertEquals(100, car.getDegradationLevel());
        Assertions.assertEquals(Condition.BROKEN, car.getCondition());
        Assertions.assertEquals(0, car.getResaleValue());
        // the level of degradation cannot exceed 100, the condition must be broken
        car.play();
        Assertions.assertEquals(100, car.getDegradationLevel());
        Assertions.assertEquals(Condition.BROKEN, car.getCondition());
    }
}
