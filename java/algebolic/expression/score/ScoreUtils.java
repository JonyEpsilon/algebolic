package algebolic.expression.score;

import java.util.List;

/**
 * Created by jony on 15/10/2014.
 */
public class ScoreUtils {

    public static double chiSquaredAbs(List<Double> a, List<Double> b) {
        double sum = 0.0;
        for (int i = 0; i < a.size(); i++) {
            sum += Math.abs(a.get(i) - b.get(i));
        }
        return sum;
    }
}
