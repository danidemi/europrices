package com.danidemi.europrice.tasks;

public class Tupla2<T1, T2> {

	private T1 one;
	private T2 two;

	public Tupla2(T1 one, T2 two) {
		super();
		this.one = one;
		this.two = two;
	}

	public Tupla2() {
	}

	public void setOne(T1 one){
		this.one = one;
	}
	
	public T1 getOne() {
		return one;
	}
	
	public void setTwo(T2 two) {
		this.two = two;
	}
	
	public T2 getTwo() {
		return two;
	}

}
