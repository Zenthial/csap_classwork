package store;

import toy.Condition;
import toy.IToy;
import toy.ToyFactory;

import java.util.LinkedList;

/**
 * The Toy Store! It starts empty, but when stocked with toys, customers may
 * buy, play with, and sell the used toys back to the store.
 */
public class AlsToyBarn {

    /**
     * Using a linked list for more efficient remove from index 0.
     */
    private final LinkedList<IToy> stock;

    /**
     * Creates a new toy store and stocks it with the specified number of toys.
     *
     * @param numberOfToys number of toy to stock in the shelves
     * @param seed         the seed for the toy's type random number generator
     */
    public AlsToyBarn(int numberOfToys, int seed) {
        stock = new LinkedList<>();
        ToyFactory.setSeed(seed);
        for (int i = 0; i < numberOfToys; i++) {
            addToy(ToyFactory.makeToy());
        }
    }

    /**
     * Adds the specified toy to the store's stock. If the toy is
     * {@link Condition#BROKEN broken}, it is discarded.
     *
     * @param toy The toy to add to the store's stock.
     * @return The resale value of the toy.
     */
    public double addToy(IToy toy) {
        if (toy.getCondition() == Condition.BROKEN) {
            System.out.println(toy + " is broken and discarded.");
        } else {
            System.out.println(toy + " is added to the stock.");
            stock.add(toy);
        }
        return toy.getResaleValue();
    }

    /**
     * A customer impulsively buys the first toy that they see; this method
     * removes and returns the first toy in the available stock.
     *
     * @return The first toy in the available stock.
     */
    public IToy purchaseToy() {
        return stock.remove(0);
    }

    /**
     * Returns a count of the toys available in the store's stock.
     *
     * @return The number of toys available in the store's stock.
     */
    public int availableStock() {
        return stock.size();
    }
}
