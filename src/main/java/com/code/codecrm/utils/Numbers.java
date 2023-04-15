package com.code.codecrm.utils;

public enum Numbers {
	ZERO(0),ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6);

	private final int value;

	private Numbers(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
