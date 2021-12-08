package test;

import org.junit.jupiter.api.*;
import toy.Condition;
import toy.IToy;
import toy.ToyFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * A JUnit test class for the toy.Doll class.
 *
 * @author RIT CS
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestDoll {
    /**
     * Used to test that expected System.out print's happen
     */
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    /**
     * doll
     */
    private static IToy doll;

    @BeforeAll
    public static void setUp() {
        // set seed to the random number generator
        ToyFactory.setSeed(1);
        // creating a doll with random values for the name, msrp, etc.
        doll = ToyFactory.randomDoll();
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
        Assertions.assertEquals("Woody",
                doll.getName(), "doll name");
        Assertions.assertEquals(Condition.MINT,
                doll.getCondition(), "doll condition");
        Assertions.assertEquals(0,
                doll.getDegradationLevel(),
                "doll level of degradation");
        Assertions.assertEquals(3000000,
                doll.getProductCode(),
                "doll product code");
        Assertions.assertEquals(17.71,
                doll.getMSRP(),
                "doll msrp");
        Assertions.assertEquals(17.71,
                doll.getResaleValue(),
                "doll msrp");
        Assertions.assertEquals("Woody [product code=3000000, MSRP=17.71, degradation level=0%, condition=MINT, " +
                        "resale value=17.71, hair color=Purple, eye color=Black]",
                doll.toString(),
                "doll toString");
    }

    @Test
    @Order(2)
    /**
     * Create a new doll with a unique code product
     */
    public void testInit2() {
        IToy doll2 = ToyFactory.randomDoll();
        Assertions.assertEquals(3000001, doll2.getProductCode());
    }

    @Test
    @Order(3)
    public void testPlay() {
        //call play and compare the play output
        doll.play();
        Assertions.assertEquals("After play, Woody's condition is NEAR_MINT"
                        + System.getProperty("line.separator"),
                outContent.toString());
        // then compare the toString output
        Assertions.assertEquals("Woody [product code=3000000, MSRP=17.71, " +
                        "degradation level=17%, condition=NEAR_MINT, resale value=15.94, " +
                        "hair color=Purple, eye color=Black]",
                doll.toString());
    }

    @Test
    @Order(4)
    public void testOverPlay() {
        while (doll.getDegradationLevel() < 100) {
            doll.play();
        }
        Assertions.assertEquals(100, doll.getDegradationLevel());
        Assertions.assertEquals(Condition.BROKEN, doll.getCondition());
        Assertions.assertEquals(0, doll.getResaleValue());
        // the level of degradation cannot exceed 100, the condition must be broken
        doll.play();
        Assertions.assertEquals(100, doll.getDegradationLevel());
        Assertions.assertEquals(Condition.BROKEN, doll.getCondition());
    }
}
