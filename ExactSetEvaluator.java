import java.util.HashSet;
import java.util.Set;
public class ExactSetEvaluator {
    private final Set<String> exact = new HashSet<>();
    private final BloomFilter bloom;
    public ExactSetEvaluator(BloomFilter bloom) {
        this.bloom = bloom;
    }
    public int exactSize() {
        return exact.size();
    }
}
