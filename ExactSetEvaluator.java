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
    public boolean containsExact(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        return exact.contains(UrlNormalizer.normalize(url));
    }
    public int countFalsePositives(String[] unseenUrls) {
        int count = 0;
        for (String url : unseenUrls) {
            if (!containsExact(url) && bloom.mightContain(url)) {
                count++;
            }
        }
        return count;
    }
    public double observedFpRate(String[] unseenUrls) {
        if (unseenUrls == null || unseenUrls.length == 0) {
            return 0.0;
        }
        return (double) countFalsePositives(unseenUrls) / unseenUrls.length;
    }
    public int exactSize() {
        return exact.size();
    }
}
