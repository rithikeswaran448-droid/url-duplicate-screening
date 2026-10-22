public class BloomFilter{
private final long[] bits;
	    private final int m;
	    private final int k;
	    private int inserted = 0;

	    public BloomFilter(int m, int k) {
	        if (m <= 0 || k <= 0) {
	            throw new IllegalArgumentException("m and k must be positive.");
	        }

	        this.m = m;
	        this.k = k;
	        this.bits = new long[(int) (((long) m + 63) / 64)];
	    }

	    private static long fnv(String s, long seed) {
	        long h = seed;
	        for (int i = 0; i < s.length(); i++) {
	            h ^= s.charAt(i);
	            h *= 0x100000001b3L;
	        }
	        return h;
	    }
  private int position(String url, int i) {
	        long h1 = fnv(url, 0xcbf29ce484222325L);
	        long h2 = fnv(url, 0x84222325cbf29ce4L) | 1L;
	        return (int) Math.floorMod(h1 + (long) i * h2, (long) m);
	    }

	    private void setBit(int p) {
	        bits[p >> 6] |= (1L << (p & 63));
	    }

	    private boolean getBit(int p) {
	        return (bits[p >> 6] & (1L << (p & 63))) != 0;
	    }

	    public void insert(String rawUrl) {
	        String url = UrlNormalizer.normalize(rawUrl);
	        for (int i = 0; i < k; i++) {
	            setBit(position(url, i));
	        }
	        inserted++;
	    }
	public boolean mightContain(String rawUrl) {
	        String url = UrlNormalizer.normalize(rawUrl);
	        for (int i = 0; i < k; i++) {
	            if (!getBit(position(url, i))) {
	                return false;
	            }
	        }
	        return true;
	    }

	    public String query(String url) {
	        return mightContain(url)
	                ? "POSSIBLY PRESENT"
	                : "DEFINITELY NOT PRESENT";
	    }
public int countSetBits() {
	        int count = 0;
	        for (long word : bits) {
	            count += Long.bitCount(word);
	        }
	        return count;
	    }

	    public double occupancy() {
	        return (double) countSetBits() / m;
	    }
	public double theoreticalFpRate(int n) {
	        return Math.pow(1 - Math.exp(-(double) k * n / m), k);
	    }

	    public int getM() {
	        return m;
	    }

	    public int getK() {
	        return k;
	    }

	    public int getInserted() {
	        return inserted;
	    }

	    public long memoryBytes() {
	        return bits.length * 8L;
	    }
}
