package org.coder.from.casterly.rock.poker.core;

import java.util.*;


public enum RANKS{
	
	TWO		("2", 	 "2"),
	THREE	("3", 	 "3"),
	FOUR	("4",	 "4"),
	FIVE	("5",	 "5"),
	SIX		("6",	 "6"),
	SEVEN	("7",	 "7"),
	EIGHT	("8",	 "8"),
	NINE	("9",	 "9"),
	TEN		("10", 	"10"),
	JACK	("J",  	"11"),
	QUEEN	("Q",  	"12"),
	KING	("K",  	"13"),
	ACE		("A",  	"14"); 

	private final String shortName;
	private final String value;
	
	public final static int COUNT 						= RANKS.values().length;
	private final static Map<String, String> FWD_MAP 	= new HashMap<String, String>( COUNT * 2);
	private final static Map<String, String> REV_MAP 	= new HashMap<String, String>( COUNT * 2);
	
	static{
		for( RANKS rank : RANKS.values() ){
			FWD_MAP.put(rank.shortName,  rank.value);
			REV_MAP.put(rank.value, rank.shortName);
		}
	}
	
	
	private RANKS( String shortName, String value ){
		this.shortName 		= shortName;
		this.value			= value;
	}
	
		
	public final String getFullName( ){
		return name();
	}
	
	
	public final String getShortName( ){
		return shortName;
	}
	
	
	public final static String getValue( String shortName ){
		String numericalValue = FWD_MAP.get( shortName );
		return ( numericalValue == null ) ? shortName : numericalValue;
	}
		
	public final static String getReverseValue( String value ){
		String shortName = REV_MAP.get( value );
		return ( shortName == null ) ? value : shortName;
	}
	
}

