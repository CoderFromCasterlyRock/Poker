package org.coder.from.casterly.rock.poker.core;

import java.util.*;

import org.apache.commons.lang3.StringUtils;


public final class Tuple{

	private final int rank;
	private final int highcard;
	private final String message;
	private final int[] remainingCards; 
	
	public final static Tuple INVALID	= new Tuple(-1, -1, "Invalid");
	
	
	public Tuple( int rank, int highcard, String message, int ... remainingCards ){
		this.rank 			= rank;
		this.highcard		= highcard;
		this.message		= message;
		this.remainingCards	= remainingCards;
	}

	
	public final int getRank(){
		return rank;
	}


	public final int getHighcard(){
		return highcard;
	}
	
	
	public final String getMessage(){
		return message;
	}
	
	
	public final int[] getRemainingCards(){
		return remainingCards;
	}
	
	
	public static final boolean isInvalid( Tuple tuple ){
		return ( (tuple == null) || (INVALID.highcard ==tuple.highcard && INVALID.rank == tuple.rank) );
	}
		

	public final static Tuple getMax( Tuple firstTuple, Tuple secondTuple ){
		
		int firstRank 		= firstTuple.getRank();
		int firstHigh 		= firstTuple.getHighcard();
		int[] firstRemains	= firstTuple.getRemainingCards();

		int secondRank 		= secondTuple.getRank();
		int secondHigh 		= secondTuple.getHighcard();
		int[] secondRemains	= secondTuple.getRemainingCards();
		
		
		if( firstRank > secondRank ){
			return firstTuple;
			
		}else if ( firstRank < secondRank ){
			return secondTuple;
		
		}else{
			
			if( firstHigh > secondHigh ){
				return firstTuple;
			
			}else if ( firstHigh < secondHigh ){
				return secondTuple;
			
			}else{
				
				int minSize = Math.min(firstRemains.length, secondRemains.length);
				
				for( int i =0; i< minSize; i++ ){
					
					if( firstRemains[i] > secondRemains[i]){
						return firstTuple;
					
					}else if( firstRemains[i] < secondRemains[i]){
						return secondTuple;
					}
				
				}
			}
			
		}
		
		return firstTuple;
		
	}
		
	
	
	@Override
	public int hashCode( ){
		
		final int prime = 31;
		int result 		= 1;
		result 			= prime * result + highcard;
		result 			= prime * result + ((message == null) ? 0 : message.hashCode());
		result 			= prime * result + rank;
		result 			= prime * result + Arrays.hashCode(remainingCards);
		
		return result;
	}


	@Override
	public boolean equals( Object obj ){
		
		if( this == obj ) return true;
		if( obj == null) return false;
		if( getClass() != obj.getClass() ) return false;
		
		Tuple other = (Tuple) obj;
		
		if( rank != other.rank ) return false;
		if( highcard != other.highcard ) return false;
		if( !Arrays.equals(remainingCards, other.remainingCards) ) return false;
		
		return true;
	}
	
	
	@Override
	public final String toString(){
		
		StringBuilder builder = new StringBuilder( 128 );

		builder.append( message );
		builder.append( StringUtils.rightPad("", 10) );
		builder.append( " High: ").append( RANKS.getReverseValue( String.valueOf( highcard ) ));
		builder.append( StringUtils.rightPad("", 10) );
		builder.append( " Rest: ").append( Arrays.toString(remainingCards) );
				
		return builder.toString();
		
	}
	

}

