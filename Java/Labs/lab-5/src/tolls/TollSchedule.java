package tolls;

import java.util.*;

/**
 * A class that encapsulate the toll schedule for 2 axel cars travelling on
 * the NY Thruway.
 *
 * @author: RIT CS
 */
public class TollSchedule {
    /**
     * Last exit on Thruway
     */
    public final static String LAST_EXIT = "50";

    /**
     * All associations between a toll booth (String) and interchange
     * name (String)
     */
    private static final Map<String, String> TOLL_BOOTHS;

    // initialize toll booths inside static area and make immutable
    static {
        Map<String, String> aMap = new TreeMap<>();
        aMap.put("17", "Newburgh I-84");
        aMap.put("18", "New Paltz");
        aMap.put("19", "Kingston");
        aMap.put("20", "Saugerties");
        aMap.put("21", "Catskill");
        aMap.put("21B", "Coxsackie");
        aMap.put("22", "Selkrik");
        aMap.put("23", "Albany (Downtown)");
        aMap.put("24", "Albany");
        aMap.put("25", "Schenectady E");
        aMap.put("25A", "Schenectady I-88");
        aMap.put("26",  "Schenectady W.");
        aMap.put("27", "Amsterdam");
        aMap.put("28", "Fultonville");
        aMap.put("29", "Canajoharie");
        aMap.put("29A", "Little Falls");
        aMap.put("30", "Herkimer");
        aMap.put("31", "Utica");
        aMap.put("32", "Westmoreland-Rome");
        aMap.put("33", "Verona-Rome");
        aMap.put("34", "Canastota");
        aMap.put("34A", "Syracuse I-481");
        aMap.put("35", "Syracuse E.");
        aMap.put("36", "Syracuse I-81");
        aMap.put("37", "Electronics Parkway");
        aMap.put("38", "Syracuse - Liverpool");
        aMap.put("39", "Syracuse W. I-690");
        aMap.put("40", "Weedsport");
        aMap.put("41", "Waterloo");
        aMap.put("42", "Geneva");
        aMap.put("43", "Manchester");
        aMap.put("44", "Canandaigua");
        aMap.put("45", "Rochester E. I-490");
        aMap.put("46", "Rochester I-390");
        aMap.put("47", "Leroy I-490");
        aMap.put("48", "Batavia");
        aMap.put("48A", "Pembroke");
        aMap.put("49", "Depew");
        aMap.put("50", "Buffalo I-290");

        TOLL_BOOTHS = Collections.unmodifiableMap(aMap);
    }
    /**
     * Each entry toll booth (String) maps to a collection of exit toll booth
     * (String) and associated fare (Double).
     */
    private static final Map<String, Map<String, Double>> SCHEDULE;

    // initialize schedule inside static area and make immutable
    static {
        // TBD - this needs to be all moved into a file.  Look away!  I can't
        // stand it! Nooooooooooo!!!

        Map<String, Map<String, Double>> aMap = new HashMap<>();
        Map<String, Double> fares = new HashMap<>();

        // 17 - Newburgh I-84
        double[] fare17 = {.75, 1.5, 1.95, 2.55, 3.05, 3.55, 3.85, 4.15, 4.45,
                4.15, 4.8, 5.35, 5.75, 6.30, 7.10, 7.55, 8.15, 8.65, 9.10, 9.50,
                10.2, 10.3, 10.5, 10.55, 10.65, 10.8, 11.5, 12.25, 12.55,
                13.2, 13.5, 13.7, 14.25, 15, 15.55, 16.1, 16.8, 16.95};
        int i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("17") > 0) {
                fares.put(tollBooth, fare17[i++]);
            }
        }
        aMap.put("17", new HashMap<>(fares));
        fares.clear();

        // 18 - New Paltz
        double[] fare18 = {.75, 1.2, 1.8, 2.3, 2.8, 3.1, 3.4, 3.7, 3.4, 4.1,
                4.6, 5, 5.55, 6.35, 6.8, 7.4, 7.9, 8.35, 8.75, 9.45, 9.55,
                9.75, 9.8, 9.9, 10.05, 10.75, 11.5, 11.85, 12.45, 12.75,
                12.95, 13.5, 14.25, 14.8, 15.35, 16.05, 16.2};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("18") > 0) {
                fares.put(tollBooth, fare18[i++]);
            }
        }
        aMap.put("18", new HashMap<>(fares));
        fares.clear();

        // 19 - Kingston
        double[] fare19 = {.5, 1.1, 1.6, 2.05, 2.4, 2.7, 2.95, 2.7, 3.35, 3.9,
                4.3, 4.85, 5.65, 6.05, 6.65, 7.15, 7.6, 8, 8.75, 8.85, 9.05,
                9.05, 9.15, 9.35, 10.05, 10.8, 11.1, 11.7, 12.05, 12.25, 12.75,
                13.5, 14.05, 14.6, 15.35, 15.5};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("19") > 0) {
                fares.put(tollBooth, fare19[i++]);
            }
        }
        aMap.put("19", new HashMap<>(fares));
        fares.clear();

        // 20 - Saugerties
        double[] fare20 = {.6, 1.1, 1.6, 1.95, 2.2, 2.5, 2.2, 2.9, 3.4, 3.85,
            4.4, 5.15, 5.6, 6.2, 6.7, 7.15, 7.55, 8.25, 8.35, 8.55, 8.6, 8.7,
            8.85, 9.55, 10.3, 10.65, 11.25, 11.6, 11.75, 12.3, 13.05,
            13.6, 14.15, 14.9, 15};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("20") > 0) {
                fares.put(tollBooth, fare20[i++]);
            }
        }
        aMap.put("20", new HashMap<>(fares));
        fares.clear();

        // 21 - Catskill
        double[] fare21 = {.5, 1, 1.35, 1.6, 1.9, 1.6, 2.3, 2.08, 3.25, 3.8,
            4.55, 5, 5.6, 6.1, 6.55, 6.95, 7.65, 7.8, 7.95, 8, 8.1, 8.3,
            8.95, 9.75, 10.05, 10.65, 11, 11.15, 11.7, 12.45, 13, 13.55,
            14.3, 14.45};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("21") > 0) {
                fares.put(tollBooth, fare21[i++]);
            }
        }
        aMap.put("21", new HashMap<>(fares));
        fares.clear();

        // 22 - Selkrik
        double[] fare22 = {.35, .65, .9, .65, 1.3, 1.85, 2.25, 2.8, 3.6, 4, 4.65,
            5.1, 5.55, 5.95, 6.7, 6.8, 7, 7, 7.1, 7.3, 8, 8.75, 9.05, 9.65, 10,
            10.2, 10.7, 11.45, 12, 12.55, 13.3, 13.45};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("22") > 0) {
                fares.put(tollBooth, fare22[i++]);
            }
        }
        aMap.put("22", new HashMap<>(fares));
        fares.clear();

        // 23 - Albany (Downtown)
        double[] fare23 = {.3, .6, .3, 1, 1.5, 1.9, 2.5, 3.25, 3.7, 4.3, 4.8,
            5.25, 5.65, 6.35, 6.45, 6.65, 6.7, 6.8, 6.95, 7.65, 8.4, 8.75,
            9.35, 9.65, 9.85, 10.4, 11.15, 11.7, 12.25, 12.95, 13.1};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("23") > 0) {
                fares.put(tollBooth, fare23[i++]);
            }
        }
        aMap.put("23", new HashMap<>(fares));
        fares.clear();

        // 24 - Albany
        double[] fare24 = {.3, 0, .7, 1.25, 1.65, 2.2, 2.95, 3.4, 4, 4.5, 4.95,
            5.35, 6.05, 6.2, 6.35, 6.4, 6.5, 6.7, 7.35, 8.15, 8.45, 9.05,
            9.4, 9.55, 10.1, 10.85, 11.4, 11.95, 12.7, 12.85};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("24") > 0) {
                fares.put(tollBooth, fare24[i++]);
            }
        }
        aMap.put("24", new HashMap<>(fares));
        fares.clear();

        // 25 - Schenectady E
        double[] fare25 = {0, .4, .95, 1.35, 1.9, 2.7, 3.1, 3.75, 4.25, 4.65,
            5.1, 5.8, 5.9, 6.1, 6.15, 6.25, 6.4, 7.1, 7.85, 8.15, 8.8,
            9.1, 9.3, 9.85, 10.6, 11.15, 11.65, 21.4, 12.55};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("25") > 0) {
                fares.put(tollBooth, fare25[i++]);
            }
        }
        aMap.put("25", new HashMap<>(fares));
        fares.clear();

        // 25A - Schenectady I-88
        double[] fare25a = {0, .55, .95, 1.5, 2.3, 2.75, 3.35, 3.85, 4.3, 4.7,
            5.4, 5.5, 5.7, 5.75, 5.85, 6, 6.7, 7.45, 7.75, 8.4, 8.7, 8.9,
            9.45, 10.2, 10.75, 11.3, 12, 12.15};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("25A") > 0) {
                fares.put(tollBooth, fare25a[i++]);
            }
        }
        aMap.put("25A", new HashMap<>(fares));
        fares.clear();

        // 26 - Schenectady W.
        double[] fare26 = {.55, .95, 1.5, 2.3, 2.75, 3.35, 3.85, 4.3, 4.7, 5.4,
            5.5, 5.7, 5.75, 5.85, 6, 6.7, 7.45, 7.75, 8.4, 8.7, 8.9, 9.45,
            10.2, 10.75, 11.3, 12, 12.15};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("26") > 0) {
                fares.put(tollBooth, fare26[i++]);
            }
        }
        aMap.put("26", new HashMap<>(fares));
        fares.clear();

        // 27 - Amsterdam
        double[] fare27 = {.45, 1, 1.75, 2.2, 2.8, 3.3, 3.75, 4.15, 4.85, 4.95,
            5.15, 5.2, 5.3, 5.45, 6.15, 6.9, 7.25, 7.85, 8.2, 8.35, 8.9,
            9.65, 10.2, 10.75, 11.5, 11.6};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("27") > 0) {
                fares.put(tollBooth, fare27[i++]);
            }
        }
        aMap.put("27", new HashMap<>(fares));
        fares.clear();

        // 28 - Fultonville
        double[] fare28 = {.6, 1.35, 1.8, 2.4, 2.9, 3.35, 3.75, 4.45, 4.55, 4.75,
            4.8, 4.9, 5.05, 5.75, 6.5, 6.85, 7.45, 7.8, 7.95, 8.5, 9.25, 9.8,
            10.35, 11.05, 11.2};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("28") > 0) {
                fares.put(tollBooth, fare28[i++]);
            }
        }
        aMap.put("28", new HashMap<>(fares));
        fares.clear();

        // 29 - Canajoharie
        double[] fare29 = {.8, 1.25, 1.85, 2.35, 2.8, 3.2, 3.9, 4, 4.2, 4.25,
            4.35, 4.5, 5.2, 5.95, 6.3, 6.9, 7.2, 7.4, 7.95, 8.7, 9.25,
            9.8, 10.5, 10.65};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("29") > 0) {
                fares.put(tollBooth, fare29[i++]);
            }
        }
        aMap.put("29", new HashMap<>(fares));
        fares.clear();

        // 29A - Little Falls
        double[] fare29a = {.45, 1.05, 1.55, 2, 2.4, 3.1, 3.25, 3.4, 3.45, 3.55,
            3.75, 4.4, 5.2, 5.5, 6.1, 6.45, 6.6, 7.15, 7.9, 8.45, 9, 9.75, 9.9};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("29A") > 0) {
                fares.put(tollBooth, fare29a[i++]);
            }
        }
        aMap.put("29a", new HashMap<>(fares));
        fares.clear();

        // 30 - Herkimer
        double[] fare30 = {.65, 1.15, 1.6, 2, 2.7, 2.8, 3, 3.05, 3.15, 3.3, 4,
            4.75, 5.05, 5.7, 6, 6.2, 6.75, 7.5, 8.05, 8.6, 9.3, 9.45};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("30") > 0) {
                fares.put(tollBooth, fare30[i++]);
            }
        }
        aMap.put("30", new HashMap<>(fares));
        fares.clear();

        // 31 - Utica
        double[] fare31 = {.5, .95, 1.35, 2.1, 2.2, 2.4, 2.4, 2.5, 2.7, 3.4, 4.15,
            4.45, 5.05, 5.4, 5.6, 6.1, 6.85, 7.4, 7.95, 8.7, 8.85};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("31") > 0) {
                fares.put(tollBooth, fare31[i++]);
            }
        }
        aMap.put("31", new HashMap<>(fares));
        fares.clear();

        // 32 - Westmoreland-Rome
        double[] fare32 = {.45, .9, 1.6, 1.7, 1.9, 1.9, 2.05, 2.2, 2.9, 3.65, 3.95,
            4.55, 4.9, 5.10, 5.6, 6.40, 6.90, 7.45, 8.20, 8.35};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("32") > 0) {
                fares.put(tollBooth, fare32[i++]);
            }
        }
        aMap.put("32", new HashMap<>(fares));
        fares.clear();

        // 33 - Verona-Rome
        double[] fare33 = {.45, 1.15, 1.25, 1.45, 1.5, 1.6, 1.75, 2.45, 3.2, 3.5,
            4.15, 4.45, 4.65, 5.2, 5.95, 6.5, 7.05, 7.75, 7.9};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("33") > 0) {
                fares.put(tollBooth, fare33[i++]);
            }
        }
        aMap.put("33", new HashMap<>(fares));
        fares.clear();

        // 34 - Canastota
        double[] fare34 = {.75, .85, 1.05, 1.05, 1.15, 1.35, 2.05, 2.8, 3.1, 3.7,
            4.05, 4.25, 4.75, 5.55, 6.05, 6.6, 7.35, 7.5};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("34") > 0) {
                fares.put(tollBooth, fare34[i++]);
            }
        }
        aMap.put("34", new HashMap<>(fares));
        fares.clear();

        // 35 - Syracuse E.
        double[] fare35 = {.2, .25, .35, .5, 1.2, 1.95, 2.3, 2.9, 3.25, 3.4, 3.95,
            4.7, 5.25, 5.8, 6.55, 6.65};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("35") > 0) {
                fares.put(tollBooth, fare35[i++]);
            }
        }
        aMap.put("35", new HashMap<>(fares));
        fares.clear();

        // 36 - Syracuse I-81
        double[] fare36 = {.15, .15, .35, 1, 1.8, 2.1, 2.7, 3.05, 3.2, 3.75,
            4.5, 5.05, 5.6, 6.35, 6.5};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("36") > 0) {
                fares.put(tollBooth, fare36[i++]);
            }
        }
        aMap.put("36", new HashMap<>(fares));
        fares.clear();

        // 37 - Electronics Parkway
        double[] fare37 = {.15, .3, 1, 1.75, 2.05, 2.65, 3, 3.2, 3.7, 4.5, 5,
            5.55, 6.3, 6.45};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("37") > 0) {
                fares.put(tollBooth, fare37[i++]);
            }
        }
        aMap.put("37", new HashMap<>(fares));
        fares.clear();

        // 38 - Syracuse - Liverpool
        double[] fare38 = {.2, .9, 1.65, 1.95, 2.55, 2.9, 3.1, 3.6, 4.4, 4.9,
            5.45, 6.2, 6.35};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("38") > 0) {
                fares.put(tollBooth, fare38[i++]);
            }
        }
        aMap.put("38", new HashMap<>(fares));
        fares.clear();

        // 39 - Syracuse W. I-690
        double[] fare39 = {.7, 1.5, 1.8, 2.4, 2.75, 2.9, 3.45, 4.2, 4.75, 5.3,
            6.05, 6.15};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("39") > 0) {
                fares.put(tollBooth, fare39[i++]);
            }
        }
        aMap.put("39", new HashMap<>(fares));
        fares.clear();

        // 40 - Weedsport
        double[] fare40 = {.8, 1.1, 1.7, 2.05, 2.2, 2.75, 3.5, 4.05, 4.6, 5.35, 5.5};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("40") > 0) {
                fares.put(tollBooth, fare40[i++]);
            }
        }
        aMap.put("40", new HashMap<>(fares));
        fares.clear();

        // 41 - Waterloo
        double[] fare41 = {.35, .95, 1.3, 1.45, 2, 2.75, 3.3, 3.85, 4.6, 4.7};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("41") > 0) {
                fares.put(tollBooth, fare41[i++]);
            }
        }
        aMap.put("41", new HashMap<>(fares));
        fares.clear();

        // 42 - Geneva
        double[] fare42 = {.65, .95, 1.15, 1.7, 2.45, 3, 3.55, 4.25, 4.4};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("42") > 0) {
                fares.put(tollBooth, fare42[i++]);
            }
        }
        aMap.put("42", new HashMap<>(fares));
        fares.clear();

        // 43 - Manchester
        double[] fare43 = {.35, .55, 1.05, 1.85, 2.35, 2.9, 3.65, 3.8};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("43") > 0) {
                fares.put(tollBooth, fare43[i++]);
            }
        }
        aMap.put("43", new HashMap<>(fares));
        fares.clear();

        // 44 - Canandaigua
        double[] fare44 = {.2, .75, 1.5, 2.05, 2.6, 3.3, 3.45};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("44") > 0) {
                fares.put(tollBooth, fare44[i++]);
            }
        }
        aMap.put("44", new HashMap<>(fares));
        fares.clear();

        // 45 - Rochester E. I-490
        double[] fare45 = {.55, 1.3, 1.85, 2.4, 3.15, 3.3};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("45") > 0) {
                fares.put(tollBooth, fare45[i++]);
            }
        }
        aMap.put("45", new HashMap<>(fares));
        fares.clear();

        // 46 - Rochester I-390
        double[] fare46 = {.8, 1.35, 1.85, 2.6, 2.75};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("46") > 0) {
                fares.put(tollBooth, fare46[i++]);
            }
        }
        aMap.put("46", new HashMap<>(fares));
        fares.clear();

        // 47 - Leroy I-490
        double[] fare47 = {.55, 1.1, 1.85, 2};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("47") > 0) {
                fares.put(tollBooth, fare47[i++]);
            }
        }
        aMap.put("47", new HashMap<>(fares));
        fares.clear();

        // 48 - Batavia
        double[] fare48 = {.55, 1.4, 1.45};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("48") > 0) {
                fares.put(tollBooth, fare48[i++]);
            }
        }
        aMap.put("48", new HashMap<>(fares));
        fares.clear();

        // 48A- Pembroke
        double[] fare48a = {.75, .9};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("48A") > 0) {
                fares.put(tollBooth, fare48a[i++]);
            }
        }
        aMap.put("48A", new HashMap<>(fares));
        fares.clear();

        // 49 - Depew
        double[] fare49  = {.15};
        i=0;
        for (String tollBooth : TOLL_BOOTHS.keySet()) {
            if (tollBooth.compareTo("49") > 0) {
                fares.put(tollBooth, fare49[i++]);
            }
        }
        aMap.put("49", new HashMap<>(fares));
        fares.clear();

        SCHEDULE = Collections.unmodifiableMap(aMap);
    }

    /**
     * This is a completely static class and should not be constructed.
     */
    private TollSchedule() {}

    /**
     * Get the fare from an enter tollbooth to an exit tollbooth
     * @param entry the enter booth
     * @param exit the exit booth
     * @return the fare
     * @throws TollRecordException if entry and exit booths are the same,
     *   or either tollbooth is not found
     */
    public static double getFare(String entry, String exit) throws TollRecordException {
        if (!TOLL_BOOTHS.containsKey(entry)) {
            throw new TollRecordException("Entry booth " + entry + " not found!");
        }
        if (!TOLL_BOOTHS.containsKey(exit)) {
            throw new TollRecordException("Exit booth " + exit + " not found!");
        }
        if (entry.compareTo(exit) == 0) {
            throw new TollRecordException("Enter and exit booths can't be the same!");
        }

        if (entry.compareTo(exit) < 0) {
            return SCHEDULE.get(entry).get(exit);
        } else {
            // swap enter and exit since tolls are only stored by
            // increasing booth
            return SCHEDULE.get(exit).get(entry);
        }
    }

    /**
     * Indicate whether a toll booth is valid or not
     * @param name the name of the booth
     * @return true if valid, false otherwise
     */
    public static boolean isValid(String name) { return TOLL_BOOTHS.containsKey((name)); }

    /**
     * Get the toll booth interchange name
     * @param name the name of the booth
     * @return the interchange name
     */
    public static String getInterchange(String name) { return TOLL_BOOTHS.get(name); }
}
