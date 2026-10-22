import java.util.HashSet;
import java.util.Set;
public class ExactSetEvaluator {
    private final Set<String> exact = new HashSet<>();
    private final BloomFilter bloom;
    public ExactSetEvaluator(BloomFilter bloom) {
        this.bloom = bloom;
    }
    public boolean insert(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }

        String normalized = UrlNormalizer.normalize(url);

        if (exact.contains(normalized)) {
            return false;
        }

        bloom.insert(normalized);
        exact.add(normalized);
        return true;
    }
    public int exactSize() {
        return exact.size();
    }
}
