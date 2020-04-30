package main;

public class Genre {
	private String name;
	private int count;
	
	public Genre(String name, int count) {
		this.name = name;
		this.count = count;
	}
	
	public Genre(String name) {
		this(name, 0);
	}
	
	public void increment() {
		count++;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public boolean equals(Object o) {
		return o instanceof Genre && ((Genre) o).name.equals(this.name);
	}
	
	public String toString() {
		return name + ": " + count;
	}
}
