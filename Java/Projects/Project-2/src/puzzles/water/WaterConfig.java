package puzzles.water;

import java.util.ArrayList;

import solver.Configuration;

public class WaterConfig implements Configuration {
    private int goalAmount;
    private ArrayList<Bucket> buckets;
    private int parent;

    public WaterConfig(int amount, ArrayList<Bucket> buckets, int parent) {
        goalAmount = amount;
        this.buckets = buckets;
        this.parent = parent;
    }

    public int getGoalAmount() {
        return goalAmount;
    }

    public ArrayList<Bucket> getBuckets() {
        return buckets;
    }

    @Override
    public boolean isSolution() {
        for (Bucket actual : getBuckets()) {
            if (actual.getCurrent() == goalAmount) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ArrayList<Configuration> getSuccessors() {
        WaterConfig typeCastValue = this;
        ArrayList<Bucket> actualBuckets = typeCastValue.getBuckets();
        ArrayList<Configuration> returnArr = new ArrayList<Configuration>();
        for (int i = 0; i < actualBuckets.size(); i++) {
            WaterConfig conf = createConfig(typeCastValue);
            if (conf.tryFill(i)) {
                returnArr.add(conf);
            }
        }

        for (int i = 0; i < actualBuckets.size(); i++) {
            WaterConfig conf = createConfig(typeCastValue);
            if (conf.tryEmpty(i)) {
                returnArr.add(conf);
            }
        }

        for (int i = 0; i < actualBuckets.size(); i++) {
            for (int j = 0; j < actualBuckets.size(); j++) {
                WaterConfig conf = createConfig(typeCastValue);
                if (conf.tryTransfer(i, j)) {
                    returnArr.add(conf);
                }
            }
        }
        
        return returnArr;
    }

    public WaterConfig createConfig(WaterConfig config) {
        ArrayList<Bucket> actualBuckets = config.getBuckets();

        ArrayList<Bucket> bucketList = new ArrayList<>();

        for (Bucket bucket : actualBuckets) {
            Bucket newBuck = new Bucket(bucket.getMax());
            newBuck.setCurrent(bucket.getCurrent());
            bucketList.add(newBuck);
        }

        WaterConfig conf = new WaterConfig(goalAmount, bucketList, config.parent + 1);
        return conf;
    }

    public boolean tryFill(int i) {
        Bucket bucket = this.buckets.get(i);
        if (!bucket.isFull()) {
            bucket.fill();
            return true;
        }
        return false;
    }

    public boolean tryEmpty(int i) {
        Bucket bucket = this.buckets.get(i);
        if (!bucket.isEmpty()) {
            bucket.empty();
            return true;
        }

        return false;
    }

    public boolean tryTransfer(int i, int j) {
        if (i != j) {
            Bucket bucket1 = buckets.get(i);
            Bucket bucket2 = buckets.get(j);
            int bucket1Start = bucket1.getCurrent();
            int bucket2Start = bucket2.getCurrent();
            transfer(bucket1, bucket2);

            return !(bucket1.getCurrent() == bucket1Start && bucket2.getCurrent() == bucket2Start);
        }

        return false;
    }

    private int transfer(Bucket one, Bucket two) {
        int leftOver = two.transfer(one.getCurrent());
        one.setCurrent(leftOver);
        return leftOver;
    }


    @Override
    public boolean equals(Object o) {
        WaterConfig config = (WaterConfig) o;
        ArrayList<Bucket> actualBuckets = config.getBuckets();

        return buckets.equals(actualBuckets);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (Bucket a : buckets) {
            hash += a.getCurrent() * a.getMax();
        }

        return hash;
    }

    @Override
    public String toString() {
        return String.format("Config: %s %d", buckets.toString(), parent);
    }
}
