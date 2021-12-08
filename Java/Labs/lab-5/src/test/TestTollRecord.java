package test;

import org.junit.jupiter.api.*;
import tolls.EntryComparator;
import tolls.TollRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestTollRecord {

    static TollRecord abc1234;
    static TollRecord def1234;
    static TollRecord ghi1234;
    static TollRecord jkl1234;
    static TollRecord mno1234;
    static TollRecord pqr1234;
    static ArrayList<TollRecord> expectedOrder;

    @BeforeAll
    public static void setUp() {
        // create an assortment of tolls.TollRecord objects
        abc1234 = new TollRecord("abc1234");
        abc1234.setEntry("48A");


        def1234 = new TollRecord("def1234");
        def1234.setEntry("45");
        def1234.setExit("34");

        ghi1234 = new TollRecord("ghi1234");
        ghi1234.setEntry("48A");
        ghi1234.setExit("45");

        jkl1234 = new TollRecord("jkl1234");
        jkl1234.setEntry("45");
        jkl1234.setExit("17");

        mno1234 = new TollRecord("mno1234");
        mno1234.setEntry("17");
        mno1234.setExit("24");

        pqr1234 = new TollRecord("pqr1234");
        pqr1234.setEntry("48");
        pqr1234.setExit("31");


        expectedOrder= new ArrayList<>();
        expectedOrder.add(abc1234);
        expectedOrder.add(def1234);
        expectedOrder.add(ghi1234);
        expectedOrder.add(jkl1234);
        expectedOrder.add(mno1234);
        expectedOrder.add(pqr1234);
    }


    @Test
    @Order(1)
    public void testContains() {
   //     System.out.println("Activity 1: list contains");
        ArrayList<TollRecord> stockList = new ArrayList<>();
        stockList.add(abc1234);
        stockList.add(def1234);
        stockList.add(ghi1234);
        stockList.add(new TollRecord("jkl1234"));
        stockList.add(pqr1234);

        Assertions.assertEquals(true,stockList.contains(abc1234));
        Assertions.assertEquals(true,stockList.contains(def1234));
        Assertions.assertEquals(false,stockList.contains(jkl1234),"A record is only equal if it has the same tag, entry, and exit");
        Assertions.assertEquals(false,stockList.contains(mno1234));

    }
    @Test
    @Order(2)
    public void testTreeOrder() {
        // System.out.println("\nActivity 2: print tree of records by decreasing tag name");
        TreeSet<TollRecord> stockTree1 = new TreeSet<>();
        stockTree1.add(mno1234);
        stockTree1.add(abc1234);
        stockTree1.add(ghi1234);
        stockTree1.add(def1234);
        stockTree1.add(jkl1234);
        stockTree1.add(mno1234);  // intentionally add a duplicate
        stockTree1.add(pqr1234);

        int i = 0;
        for (TollRecord record : stockTree1) {
            Assertions.assertEquals(expectedOrder.get(i), record);
            i++;
        }
    }
    @Test
    @Order(3)
    public void testEntryComparator() {

        TreeSet<TollRecord> stockTree1 =  new TreeSet<>(new EntryComparator());
        stockTree1.add(mno1234);
        stockTree1.add(abc1234);
        stockTree1.add(ghi1234);
        stockTree1.add(def1234);
        stockTree1.add(jkl1234);
        stockTree1.add(mno1234);  // intentionally add a duplicate
        stockTree1.add(pqr1234);
        ArrayList<TollRecord> expectedOrder2 = new ArrayList<>();
        expectedOrder2.add(mno1234);
        expectedOrder2.add(def1234);
        expectedOrder2.add(jkl1234);
        expectedOrder2.add(pqr1234);
        expectedOrder2.add(abc1234);
        expectedOrder2.add(ghi1234);

        // make sure the two collections are the same size
        Assertions.assertEquals(expectedOrder2.size(), stockTree1.size());
 
        int i = 0;
        for (TollRecord record : stockTree1) {
            Assertions.assertEquals(expectedOrder2.get(i), record);
            i++;
        }
    }

    @Test
    @Order(4)
    public void testMap() {
        HashMap<String, TollRecord> oldObject = new HashMap<>();
        HashMap<TollRecord, Integer> stockMap = new HashMap<>();
        stockMap.put(mno1234, 2);
        stockMap.put(abc1234, 3);
        stockMap.put(ghi1234, 4);
        stockMap.put(def1234, 3);
        stockMap.put(jkl1234, 2);
        stockMap.put(mno1234, 3);  // update to 3 axles
        stockMap.put(pqr1234, 2);
        setUp();//this will create new objects

        for( TollRecord x : expectedOrder) {
            oldObject.put(x.getTag(),x);
        }

        //test HashCode

        for( TollRecord x : expectedOrder) {
            Assertions.assertEquals(true,oldObject.get(x.getTag()).hashCode() == x.hashCode());
        }

        //test Map
        Assertions.assertEquals(3,stockMap.get(abc1234));
        Assertions.assertEquals(4,stockMap.get(ghi1234));
        Assertions.assertEquals(3,stockMap.get(def1234));
        Assertions.assertEquals(2,stockMap.get(jkl1234));
        Assertions.assertEquals(3,stockMap.get(mno1234));
        Assertions.assertEquals(2,stockMap.get(pqr1234));
    }

}
