package org.coder.from.casterly.rock.poker.game;

import java.util.*;

import org.coder.from.casterly.rock.poker.core.*;


public enum PokerHandType{
	

	STRAIGHT_FLUSH	( 9, "[Straight Flush]"	){
		public Tuple getHandType( int[] ranks, char[] suits ){
			return Poker.straightFlush( ranks, suits );
		}
	},
	
	FOUR_OF_A_KIND	( 8, "[Four of a Kind]"	){
		public Tuple getHandType( int[] ranks, char[] suits ){
			return Poker.fourOfAKind( ranks );
		}
	},
	
	FULL_HOUSE		( 7, "[Full House]"		){
		public Tuple getHandType( int[] ranks, char[] suits ){
			return Poker.fullHouse( ranks );
		}
	},
	
	FLUSH			( 6, "[Flush]"			){
		public Tuple getHandType( int[] ranks, char[] suits ){
			return Poker.flush( ranks, suits );
		}
	},
	
	STRAIGHT		( 5, "[Straight]"		){
		public Tuple getHandType( int[] ranks, char[] suits ){
			return Poker.straight( ranks );
		}
	},
	
	THREE_OF_A_KIND	( 4, "[Three of a Kind]"){
		public Tuple getHandType( int[] ranks, char[] suits ){
			return Poker.threeOfAKind( ranks );
		}
	},
	
	TWO_PAIR		( 3, "[Two Pair]"		){
		public Tuple getHandType( int[] ranks, char[] suits ){
			return Poker.twoPair( ranks );
		}
	},
	
	ONE_PAIR		( 2, "[One Pair]"		){
		public Tuple getHandType( int[] ranks, char[] suits ){
			return Poker.onePair( ranks );
		}
	},

	HIGH_CARD		( 1, "[High Card]"		){
		public Tuple getHandType( int[] ranks, char[] suits ){
			return Poker.highCard( ranks );
		}
	};
	
	
	private final int rank;
	private final String description;
	private final static Map<Integer, PokerHandType> TYPE_MAP = new HashMap<Integer, PokerHandType>( 20 );
	private final static PokerHandType[] ORDERED_HAND_TYPE = { STRAIGHT_FLUSH, FOUR_OF_A_KIND, FULL_HOUSE, FLUSH, STRAIGHT, THREE_OF_A_KIND, TWO_PAIR, ONE_PAIR, HIGH_CARD };
	
	
	static{
		for( PokerHandType type : PokerHandType.values() ){
			TYPE_MAP.put( type.rank, type );
		}
	}
	
	
	PokerHandType( int rank, String description ){
		this.rank 			= rank;
		this.description 	= description;
	}
	
	
	public final int rank( ){
		return rank;
	}
	
	
	public final String description( ){
		return description;
	}
	
	
	public static final PokerHandType[] getOrderedHandType( ){
		return ORDERED_HAND_TYPE;
	}
	
	
	public static final PokerHandType getHandType( int rank ){
		return TYPE_MAP.get( rank );
	}
	
	
	public abstract Tuple getHandType( int[] ranks, char[] suits );

	
}
