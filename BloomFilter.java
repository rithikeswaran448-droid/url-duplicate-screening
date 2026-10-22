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
  
}
