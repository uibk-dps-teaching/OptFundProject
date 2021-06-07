package at.uibk.dps.optfund.ant_colony.selector;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntEdge;

import java.util.List;
import java.util.Random;

/**
 * Selector base on Roulette Wheel Sampling
 */
public class RouletteWheelSelector implements Selector {

    private final Random rnd = new Random(0);

    @Override
    public AbstractAntEdge select(List<AbstractAntEdge> elements, List<Double> weights) {

        if(elements == null || elements.size() == 0) {
            return null;
        }

        if(weights == null || weights.size() != elements.size()) {
            throw new IllegalArgumentException("count of weights does not match count of elements");
        }

        // create interval array
        double[] intervals = new double[weights.size()];
        double sum = 0;

        for(int i = 0; i < weights.size(); i++) {
            double w = weights.get(i);
            intervals[i] = i > 0
                    ? w + intervals[i-1]
                    : w;
            sum += w;
        }

        AbstractAntEdge chosenElement = null;
        double val = rnd.nextDouble() * sum;
        for(int i = 0; i < intervals.length; i++) {
            if(val <= intervals[i]) {
                chosenElement = elements.get(i);
                break;
            }
        }
        return chosenElement;
    }
}
