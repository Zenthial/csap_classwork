package test;

import org.junit.jupiter.api.*;
import toy.Condition;
import toy.IToy;
import toy.ToyFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * A JUnit test class for the toy.Scooter class.
 *
 * @author RIT CS
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestScooter {
    /**
     * Used to test that expected System.out print's happen
     */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    /**
     * scooter
     */
    private static IToy scooter;

    @BeforeAll
    public static void setUp() {
        // set seed to the random number generator
        ToyFactory.setSeed(1);
        // creating a scooter with random values for the name, msrp, etc.
        scooter = ToyFactory.randomScooter();
    }

    @BeforeEach
    public void setUpStreams(){
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(System.out);
    }

    @Test
    @Order(1)
    public void testInit() {
        Assertions.assertEquals("Micro Maxi",
                scooter.getName(), "Scooter name");
        Assertions.assertEquals(Condition.MINT,
                scooter.getCondition(), "Scooter condition");
        Assertions.assertEquals(0,
                scooter.getDegradationLevel(),
                "Scooter level of degradation");
        Assertions.assertEquals(9000000,
                scooter.getProductCode(),
                "Scooter product code");
        Assertions.assertEquals(52.14,
                scooter.getMSRP(),
                "Scooter msrp");
        Assertions.assertEquals(52.14,
                scooter.getResaleValue(),
                "Scooter resale value");
        Assertions.assertEquals("Micro Maxi [product code=9000000, MSRP=52.14, " +
                        "degradation level=0%, condition=MINT, " +
                        "resale value=52.14, color=Gold, wheels=2, odometer=0]",
                scooter.toString(),
                "Scooter toString");
    }

    @Test
    @Order(2)
    /**
     * Create a new scooter with a unique code product
     */
    public void testInit2() {
        IToy scooter2 = ToyFactory.randomScooter();
        Assertions.assertEquals(9000001, scooter2.getProductCode());
    }

    @Test
    @Order(3)
    public void testPlay() {
        // call play and compare the play output
        scooter.play();
        Assertions.assertEquals("After play, Micro Maxi's condition is MINT"
                        + System.getProperty("line.separator"),
                outContent.toString());
        // then compare the toString output
        Assertions.assertEquals("Micro Maxi [product code=9000000, MSRP=52.14, degradation level=5%, " +
                "condition=MINT, resale value=52.14, color=Gold, wheels=2, odometer=2]", scooter.toString());
    }

    @Test
    @Order(4)
    public void testPlayAgain() {
        // call play and compare the play output, the second time it should change its condition
        scooter.play();
        Assertions.assertEquals("After play, Micro Maxi's condition is NEAR_MINT"
                        + System.getProperty("line.separator"),
                outContent.toString());
        // then compare the toString output
        Assertions.assertEquals("Micro Maxi [product code=9000000, MSRP=52.14, degradation level=10%, " +
                "condition=NEAR_MINT, resale value=46.93, color=Gold, wheels=2, odometer=4]", scooter.toString());
    }

    @Test
    @Order(5)
    public void testOverPlay() {
        while (scooter.getDegradationLevel() < 100) {
            scooter.play();
        }
        Assertions.assertEquals(100, scooter.getDegradationLevel());
        Assertions.assertEquals(Condition.BROKEN, scooter.getCondition());
        Assertions.assertEquals(0, scooter.getResaleValue());
        // the level of degradation cannot exceed 100, the condition must be broken
        scooter.play();
        Assertions.assertEquals(100, scooter.getDegradationLevel());
        Assertions.assertEquals(Condition.BROKEN, scooter.getCondition());
    }
}
