package test;

import org.junit.jupiter.api.*;
import toy.Condition;
import toy.IToy;
import toy.ToyFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * A JUnit test class for the toy.Robot class.
 *
 * @author RIT CS
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRobot {
    /**
     * robot
     */
    private static IToy robot;
    /**
     * Used to test that expected System.out print's happen
     */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeAll
    public static void setUp() {
        // set seed to the random number generator
        ToyFactory.setSeed(1);
        // creating a robot with random values for the name, msrp, etc.
        robot = ToyFactory.randomRobot();
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
        Assertions.assertEquals("Soundwave",
                robot.getName(), "Robot name");
        Assertions.assertEquals(Condition.MINT,
                robot.getCondition(), "Robot condition");
        Assertions.assertEquals(0,
                robot.getDegradationLevel(),
                "Robot level of degradation");
        Assertions.assertEquals(7000000,
                robot.getProductCode(),
                "Robot product code");
        Assertions.assertEquals(93.70,
                robot.getMSRP(),
                "robot msrp");
        Assertions.assertEquals(93.70,
                robot.getResaleValue(),
                "Robot resale value");
        Assertions.assertEquals("Soundwave [product code=7000000, MSRP=93.70, degradation level=0%, condition=MINT," +
                        " resale value=93.70, battery type=C, number of batteries=3, battery level=100%, " +
                        "sound=One shall stand, one shall fall.]",
                robot.toString(),
                "Robot toString");
    }

    @Test
    @Order(2)
    /**
     * Create a new robot with a unique code product
     */
    public void testInit2() {
        IToy robot2 = ToyFactory.randomRobot();
        Assertions.assertEquals(7000001, robot2.getProductCode());
    }

    @Test
    @Order(3)
    public void testPlay() {
        // call play and compare the play output
        robot.play();
        Assertions.assertEquals("Soundwave goes 'One shall stand, one shall fall.'"
                        + System.getProperty("line.separator") +
                        "After play, Soundwave's condition is VERY_GOOD" +
                        System.getProperty("line.separator"),
                outContent.toString());
        // then compare the toString output
        Assertions.assertEquals("Soundwave [product code=7000000, MSRP=93.70, " +
                "degradation level=20%, condition=VERY_GOOD, resale value=70.28, " +
                "battery type=C, number of batteries=3, battery level=75%, " +
                "sound=One shall stand, one shall fall.]", robot.toString());
    }

    @Test
    @Order(5)
    public void testReplaceBatteries() {
        while (robot.getCondition() != Condition.BROKEN) {
            robot.play();
        }
        Assertions.assertEquals("Soundwave goes 'One shall stand, one shall fall.'"
                        + System.getProperty("line.separator") +
                        "After play, Soundwave's condition is GOOD" +
                        System.getProperty("line.separator") +
                        "Soundwave goes 'One shall stand, one shall fall.'"
                        + System.getProperty("line.separator") +
                        "After play, Soundwave's condition is FAIR" +
                        System.getProperty("line.separator") +
                        "Soundwave goes 'One shall stand, one shall fall.'" +
                        System.getProperty("line.separator") +
                        "After play, Soundwave's condition is POOR" +
                        System.getProperty("line.separator") +
                        "The batteries are dead! Let's replace them..."
                        + System.getProperty("line.separator") +
                        "Soundwave goes 'One shall stand, one shall fall.'"
                        + System.getProperty("line.separator") +
                        "After play, Soundwave's condition is BROKEN"
                        + System.getProperty("line.separator"),
                outContent.toString());
    }

    @Test
    @Order(6)
    public void testOverPlay() {
        while (robot.getDegradationLevel() < 100) {
            robot.play();
        }
        Assertions.assertEquals(100, robot.getDegradationLevel());
        Assertions.assertEquals(Condition.BROKEN, robot.getCondition());
        Assertions.assertEquals(0, robot.getResaleValue());
        // the level of degradation cannot exceed 100, the condition must be broken
        robot.play();
        Assertions.assertEquals(100, robot.getDegradationLevel());
        Assertions.assertEquals(Condition.BROKEN, robot.getCondition());
    }
}
