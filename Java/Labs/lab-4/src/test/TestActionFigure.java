package test;

import org.junit.jupiter.api.*;
import toy.Condition;
import toy.IToy;
import toy.ToyFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * A JUnit test class for the toy.ActionFigure class.
 *
 * @author RIT CS
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestActionFigure {
    /**
     * Used to test that expected System.out print's happen
     */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    /**
     * actionFigure
     */
    private static IToy actionFigure;

    @BeforeAll
    public static void setUp() {
        // set seed to the random number generator
        ToyFactory.setSeed(1);
        // creating a actionFigure with random values for the name, msrp, etc.
        actionFigure = ToyFactory.randomActionFigure();
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
        Assertions.assertEquals("Sneaky Harry",
                actionFigure.getName(), "actionFigure name");
        Assertions.assertEquals(Condition.MINT,
                actionFigure.getCondition(), "actionFigure condition");
        Assertions.assertEquals(0,
                actionFigure.getDegradationLevel(),
                "actionFigure level of degradation");
        Assertions.assertEquals(5000000,
                actionFigure.getProductCode(),
                "actionFigure product code");
        Assertions.assertEquals(16.14,
                actionFigure.getMSRP(),
                "actionFigure msrp");
        Assertions.assertEquals(16.14,
                actionFigure.getResaleValue(),
                "actionFigure resale value");
        Assertions.assertEquals("Sneaky Harry [product code=5000000, MSRP=16.14, " +
                        "degradation level=0%, condition=MINT, resale value=16.14, " +
                        "hair color=Pink, eye color=Purple, kung-fu grip=false]",
                actionFigure.toString(),
                "actionFigure toString");
    }

    @Test
    @Order(2)
    /**
     * Create a new action figure with a unique code product
     */
    public void testInit2(){
        IToy actionFigure2= ToyFactory.randomActionFigure();
        Assertions.assertEquals(5000001, actionFigure2.getProductCode());
    }

    @Test
    @Order(3)
    public void testPlay() {
        //call play and compare the play output
        actionFigure.play();
        Assertions.assertEquals("After play, Sneaky Harry's condition is NEAR_MINT"
                        + System.getProperty("line.separator"),
                outContent.toString());
        // then compare the toString output
        Assertions.assertEquals("Sneaky Harry [product code=5000000, MSRP=16.14, " +
                        "degradation level=10%, condition=NEAR_MINT, resale value=14.53, " +
                        "hair color=Pink, eye color=Purple, kung-fu grip=false]",
                actionFigure.toString());
    }

    @Test
    @Order(4)
    public void testOverPlay() {
        while (actionFigure.getDegradationLevel() < 100) {
            actionFigure.play();
        }
        Assertions.assertEquals(100, actionFigure.getDegradationLevel());
        Assertions.assertEquals(Condition.BROKEN, actionFigure.getCondition());
        Assertions.assertEquals(0, actionFigure.getResaleValue());
        // the level of degradation cannot exceed 100, the condition must be broken
        actionFigure.play();
        Assertions.assertEquals(100, actionFigure.getDegradationLevel());
        Assertions.assertEquals(Condition.BROKEN, actionFigure.getCondition());
    }
}
