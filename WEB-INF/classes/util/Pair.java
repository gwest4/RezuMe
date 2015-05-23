package util;

public class Pair implements Comparable<Double> {
	private Double key;
	private Integer val;
	
	public Pair(Double key, Integer val) {
		this.key = key;
		this.val = val;
	}
	
	public Double getKey() {
		return this.key;
	}
	
	public Integer getVal() {
		return this.val;
	}
	
	
	public int compareTo(Pair that) {
		if (this == that) return 0;
		return -1;
	}
	
	@Override
	public int compareTo(Double that) {
		final int BEFORE = -1;
		final int EQUAL = 0;
		final int AFTER = 1;
		
		if (this.getKey() < that) return BEFORE;
		if (this.getKey() > that) return AFTER;
		if (this.getKey() == that) return EQUAL;

		return Integer.MIN_INTEGER;
	}
	
}
