package org.coder.from.casterly.rock.poker.core;


public enum SUITS{
	
	HEARTS	("H"),
	SPADES	("S"),
	CLUBS	("C"),
	DIAMONDS("D"); 

	
	private final String shortName;
	
	public final static int COUNT = SUITS.values().length;
	
	private SUITS( String shortName ){
		this.shortName = shortName;
	}
	
	
	public final static int getCount( ){
		return COUNT;
	}
	
	
	public final String getFullName( ){
		return name();
	}
	
	
	public final String getShortName( ){
		return shortName;
	}
	
	
}
