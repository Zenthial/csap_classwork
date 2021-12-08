package toy;

import java.util.Random;

/**
 * A Toy Factory!
 */
public final class ToyFactory {
    /**
     * Used to generate random numbers.
     */
    private static final Random rand = new Random();

    /**
     * Type code used to indicate that the toy factory should make a scooter.
     */
    private static final int SCOOTER = 1;

    /**
     * Type code used to indicate that the toy factory should make a doll.
     */
    private static final int DOLL = 2;

    /**
     * Type code used to indicate that the toy factory should make an action
     * figure.
     */
    private static final int ACTION_FIGURE = 3;

    /**
     * The type code used to indicate that the toy factory should make an RC
     * car.
     */
    private static final int RC_CAR = 4;

    /**
     * The type code used to indicate that the toy factory should make a
     * robot.
     */
    private static final int ROBOT = 5;

    /**
     * The potential names for scooters.
     */
    private static final String[] SCOOTER_NAMES = {
        "Razor A", "Razor A5", "Mongoose Expo", "ENKEEO Kick", "Micro Maxi",
        "Royal Guard II", "Fuzion X-3", "Fuzion Z250 Pro", "VOKUL S2",
        "Xiaomi Mi", "WonderView"
    };

    /**
     * The potential colors for scooters.
     */
    private static final String[] SCOOTER_COLORS = {
      "Black", "Pink", "Green", "Blue", "Cyan", "Purple", "Gold", "Silver",
      "Red"
    };

    /**
     * The potential names for dolls.
     */
    private static final String[] DOLL_NAMES = {
      "Sally", "Johnny", "Barbie", "Ken", "Julio", "Ralph", "Jane", "Sarah",
      "Susan", "Benedetta", "Edwina", "Gloria", "Henriette", "Laura",
      "Marilee", "Norma", "Barbara Ann", "Barbara Lou", "Dora Lee", "Flossie",
      "Ginger", "Hilda", "Lucia Mary Lee", "Babs", "Betty", "Roger", "George",
      "Robert", "Harry", "Bruce", "Willy", "Johnson", "Pete", "Woody", "Buzz",
      "Larry"
    };

    /**
     * The potential colors for hair.
     */
    private static final String[] HAIR_COLOR = {
      "Blond", "Brown", "Brunette", "Purple", "Pink", "Red", "Black",
      "Ginger", "Green", "Blue"
    };

    /**
     * The potential colors for eyes.
     */
    private static final String[] EYE_COLOR = {
      "Blue", "Green", "Black", "Brown", "Purple", "Hazel"
    };

    /**
     * The potential prefixes for action figures.
     * The name of an action figure is the concatenation of one of those prefixes +
     * a doll's name (e.g. Strong Laura)
     */
    private static final String[] ACTION_FIGURE_PREFIXES = {
        "Action", "Lightning", "Strong", "Fast",  "Sneaky", "Super", "Tough",
        "'Lasers'", "Stretch", "G.I.", "Quiet"
    };

    /**
     * The potential names for RC Cars.
     */
    private static final String[] RC_CAR_NAMES = {
        "Fistone Truck", "Gizmovine RC", "SPESXFUN", "Hapinic RC", "Amicool",
        "METAKOO RC", "Hosim RC", "KOOWHEEL Car", "Distianert Truck",
        "JEYPOD IMDEN Car"
    };


    /**
     * The potential names for robots.
     */
    private static final String[] ROBOT_NAMES = {
        "Iron Giant", "Mechagodzilla", "Optimus Prime", "Reginald", "Vector",
        "Robby", "R2-D2", "C3PO", "BB8", "Megatron", "Shockwave", "Soundwave",
        "Bumblebee", "iRobot", "Roomba", "Data"
    };

    /**
     * The potential sounds that a robot may make.
     */
    private static final String[] ROBOT_SOUNDS = {
        "Bleep Blorp", "Death to humans!", "One shall stand, one shall fall.",
        "Beepeedeepee", "Oh dear!", "Starscream, you fool!", "Eject!",
        "*radio playing*", "Bzzzzzt!", "I...am...Suuuuperman.",
        "I am not a gun!"
    };

    /**
     * Set the seed to the random number generator
     * @param seed the seed
     */
    public static void setSeed(int seed) {
        rand.setSeed(seed);
    }

    /**
     * Factory method that makes and returns a random toy.
     *
     * @return A new, randomly selected toy.
     */
    public static IToy makeToy() {
        int toyType = randomInt(1, 5);

        switch (toyType) {
            case SCOOTER:
                return randomScooter();
        
            case DOLL:
                return randomDoll();

            case ACTION_FIGURE:
                return randomActionFigure();

            case RC_CAR:
                return randomRCCar();

            case ROBOT:
                return randomRobot();

            default:
                return null;
        }
    }


    /**
     * Makes and returns a scooter with a random name, MSRP, color, and
     * number of wheels (either 2 or 3).
     * The MSRP must be a value between 39.99 and 160.99
     *
     * @return The newly crafted scooter.
     */
    public static IToy randomScooter() {
        String name = randomString(SCOOTER_NAMES);
        double msrp = randomDouble(39.99, 160.99);
        String color = randomString(SCOOTER_COLORS);
        int wheels = randomInt(2, 3);

        // make the scooter and assign it to toy
        return new Scooter(name, msrp, color, wheels);
    }

    /**
     * Makes and returns a doll with a random name, MSRP, hair color, and eye
     * color.
     * The MSRP must be a value between 12.99 and 60.00
     *
     * @return The newly crafted doll.
     */
    public static IToy randomDoll() {
        // TODO YOUR CODE HERE
        // select the doll fields value in the following order:
        // 1 - select a random name
        // 2 - select a random MSRP (a value between 12.99 and 60.00)
        // 3 - select a random hair color
        // 4 - select a random eye color
        // make the doll and return it
        String name = randomString(DOLL_NAMES);
        double msrp = randomDouble(12.99, 60.00);
        String hairColor = randomString(HAIR_COLOR);
        String eyeColor = randomString(EYE_COLOR);

        return new Doll(name, msrp, hairColor, eyeColor);
    }

    /**
     * Makes and returns an action figure with a random name, MSRP, hair
     * color, eye color, and Kung-Fu Grip (tm) configuration.
     * The MSRP must be a value between 9.99 and 24.99
     *
      * @return The newly crafted action figure.
     */
    public static IToy randomActionFigure() {
        // TODO YOUR CODE HERE
        // select the action figure fields value in the following order:
        // 1 - select a random name
        // 2 - select a random MSRP (a value between 9.99 and 24.99)
        // 3 - select a random hair color
        // 4 - select a random eye color
        // 5 - select a random Kung-Fu Grip configuration (true or false)
        // make the action figure and return it
        String prefix = randomString(ACTION_FIGURE_PREFIXES);
        String name = randomString(DOLL_NAMES);
        double msrp = randomDouble(9.99, 24.99);
        String hairColor = randomString(HAIR_COLOR);
        String eyeColor = randomString(EYE_COLOR);
        boolean grip = randomBoolean();

        return new ActionFigure(prefix + " " + name, msrp, hairColor, eyeColor, grip);
    }

    /**
     * Makes and returns an RC Car with a random name, MSRP, battery type,
     * number of batteries, and scale speed.
     * The number of batteries must be a random value between 1 and 6.
     * The MSRP must be a random value between 19.99 and 159.99.
     * The scale speed must be a random value between 100 and 300.
     *
     * @return The newly crafted RC Car.
     */
    public static IToy randomRCCar() {
        // TODO YOUR CODE HERE
        // select the rc car fields value in the following order:
        // 1 - select a random name
        // 2 - select a random MSRP (a value between 19.99 and 159.99)
        // 3 - select a random battery type
        // 4 - select a random number of batteries (a value between 1 and 6)
        // 5 - select a random scale speed
        // make the RC car and return it

        String name = randomString(RC_CAR_NAMES);
        double msrp = randomDouble(19.99, 159.99);
        BatteryType type = randomBatteryType();
        int batteries = randomInt(1, 6);
        int speed = randomInt(100, 300);

        return new RCCar(name, msrp, type, batteries, speed);
    }

    /**
     * Makes and returns a Robot with a random name, MSRP, battery type,
     * number of batteries, and sound.
     * The number of batteries must be a random value between 1 and 6.
     * The MSRP must be a value between 25.99 and 699.99.
     *
     * @return The newly crafted robot.
     */
    public static IToy randomRobot() {
        // TODO YOUR CODE HERE
        // select the robot fields value in the following order:
        // 1 - select a random name
        // 2 - select a random MSRP (a value between 25.99 and 699.99)
        // 3 - select a random battery type
        // 4 - select a random number of batteries (a value between 1 and 6)
        // 5 - select a random sound
        // make the robot and return it
        String name = randomString(ROBOT_NAMES);
        double msrp = randomDouble(25.99, 699.99);
        BatteryType type = randomBatteryType();
        int batteries = randomInt(1, 6);
        String sound = randomString(ROBOT_SOUNDS);

        return new Robot(name, msrp, type, batteries, sound);
    }

    //////////////////////////////////////////////////
    //              UTILITY METHODS                 //
    /////////////////////////////////////////////////

    /**
     * Returns a random integer in the range [min, max]
     *
     * @param min The minimum value.
     * @param max The maximum value.
     *
     * @return A random integer in the range [min, max].
     */
    private static int randomInt(int min, int max) {
        int bound = max - min + 1;
        return rand.nextInt(bound) + min;
    }

    /**
     * Returns a random boolean (true or false).
     *
     * @return A random boolean.
     */
    private static boolean randomBoolean() {
        return rand.nextBoolean();
    }

    /**
     * Returns a random double in the range [min, max].
     *
     * @param min The minimum value.
     * @param max The maximum value.
     *
     * @return A random double in the range [min, max].
     */
    private static double randomDouble(double min, double max) {
        double bound = max - min;
        double temp = rand.nextDouble() * bound + min;
        temp = temp * 100;
        int rounded = (int)temp;
        return rounded / 100.0;
    }

    /**
     * Selects a random value from the specified string array.
     *
     * @param values The array of strings from which to choose a random value.
     *
     * @return The value that has been randomly selected from the array of
     * strings.
     */
    private static String randomString(String[] values) {
        int index = randomInt(0, values.length-1);
        return values[index];
    }

    /**
     * Returns a randomly selected battery type.
     *
     * @return A battery type.
     */
    private static BatteryType randomBatteryType() {
        int batteries = randomInt(0, 5);
        return BatteryType.values()[batteries];
    }
}
