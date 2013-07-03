package org.coder.from.casterly.rock.poker.game;

import java.util.*;
import java.util.Map.*;

import org.apache.commons.lang3.StringUtils;
import org.coder.from.casterly.rock.poker.core.*;

import static org.coder.from.casterly.rock.poker.core.Tuple.*;
import static org.coder.from.casterly.rock.poker.game.PokerHandType.*;


public class Poker{

	public final static Comparator<String> CARD_COMPARATOR  = new HandComparator();
	
	
	/**
	 * @param Takes a set of hands
	 * [4D, 4S, 5D, 7C, 9D] [6D, JS, 2C, KS, 4H] [AC, 10H, 7S, 8C, QH] [10D, 5S, 9H, JD, 10C]
	 * 
	 * 1) Replace picture cards with numerical equivalent so, [6S,   4C,  JS,  7S,  KC]  -->  [ 6S,  4C, 11S, 7S, 13C]
	 * 2) Returns the best hand among the set of hands according to the rules of Poker [10D, 5S, 9H, JD, 10C]
	 */
	public final Pair<Tuple, String[]> playPoker( CardHand cardHand ){
		
		Tuple maxHandRank 				= null;
		String[] maxHand 				= null;
		
		System.out.println(" " );
		System.out.println("Hand Analysis:: " );
				
		for( Entry<String[], String> entry : cardHand.getCardEntry() ){
			
			String[] currentHand		= entry.getKey();
			String playerName			= entry.getValue();
			
			String[] picturelessHand	= removePictures( currentHand );
			Tuple currentRank 			= getHandRank( picturelessHand );
			
			System.out.println( StringUtils.rightPad(playerName, 20) + ": " + currentRank );
		
						
			if ( maxHandRank == null ){
				maxHand		= currentHand;
				maxHandRank = currentRank;
				continue;
			}
			
			
			Tuple maxTuple				= Tuple.getMax( maxHandRank, currentRank );
			maxHand						= ( maxTuple == currentRank ) ? currentHand : maxHand;
			maxHandRank					= maxTuple;
	
		}
		
		return new Pair<Tuple, String[]>( maxHandRank, maxHand );
				
	}
	
	
	/**
	 * @param Takes in a single hand of cards such as [6S, 4C, JS, 7S, KC] and returns its rank according to the rules of poker.
	 * 
	 * 
	 * 2) Sort the hand in descending order of the ranks so,  [6S,   4C, 11S,  7S, 13C]  -->  [13C, 11S,  7S, 6S,  4C]
	 * 3) Split the hand into array of ranks and suits so,    [13C, 11S,  7S,  6S,  4C]  -->  [ 13,  11,   7,  6,   4], [C, S,  S, S, C]
	 * 4) Figure out the type of hand and return a Tuple that assigns a numerical value to the hand.
	 * 
	 * @return  
	 */
	
	public final Tuple getHandRank( String[] newHand ){
		
		String[] hand			= Arrays.copyOf(newHand, newHand.length);
		Arrays.sort( hand, CARD_COMPARATOR );
		
		Pair<int[], char[]> pair = extractHand( hand );
		int[] ranks 			 = pair.getFirst();
		char[] suits 			 = pair.getSecond();
		
		Tuple result 			 = INVALID;
		
		for( PokerHandType handType : PokerHandType.getOrderedHandType( ) ){
			result = handType.getHandType( ranks, suits );
			if ( INVALID != result ){
				break;
			}
		}
	
		return result;
		
	}
	
	
	public static Tuple straightFlush( int[] ranks, char[] suits ){
		
		int firstRank	 = ranks[ 0 ];
		char firstSuit	 = suits[ 0 ];
				
		for( int i = 1; i< ranks.length; i ++ ){
			if( firstSuit != suits[i] ){
				return INVALID;
			}
			
			if( (firstRank - ranks[i] ) != 1 ){
				return INVALID;
			}
			
			firstRank = ranks[i];
		}

		return new Tuple( STRAIGHT_FLUSH.rank(), ranks[0], STRAIGHT_FLUSH.description(), Arrays.copyOfRange(ranks, 1, ranks.length) );
		
	}
	
	
	//[4, 4, 4, 4, 3]
	//[7, 5, 5, 5, 5]
	public static final Tuple fourOfAKind( int[] ranks ){
		int fourOfAKind 		= kind( 4, ranks );
		boolean isFourOfAKind 	= ( fourOfAKind != -1 );
		
		return ( !isFourOfAKind ) ? INVALID : new Tuple( FOUR_OF_A_KIND.rank(), fourOfAKind, FOUR_OF_A_KIND.description(), removeRank(fourOfAKind, ranks) );
	}
	
	
	public static final Tuple threeOfAKind( int[] ranks ){
		int threeOfAKind 		= kind( 3, ranks );
		boolean isThreeOfAKind 	= ( threeOfAKind != -1 );
		
		return ( !isThreeOfAKind ) ? INVALID : new Tuple( THREE_OF_A_KIND.rank(), threeOfAKind, THREE_OF_A_KIND.description(), removeRank(threeOfAKind, ranks) );
	}
	
	
	public static final Tuple fullHouse( int[] ranks ){
		int threeOfAKind 	= kind( 3, ranks );
		int twoOfAKind 		= kind( 2, removeRank(threeOfAKind, ranks) );
		
		return ( threeOfAKind == -1 || twoOfAKind == -1 ) ? INVALID : new Tuple( FULL_HOUSE.rank(), threeOfAKind, FULL_HOUSE.description(), twoOfAKind );
	}
	
	
	public static final Tuple flush( int[] ranks, char[] suits ){
		char firstSuit	 = suits[ 0 ];
		
		for( int i = 1; i< suits.length; i ++ ){
			if( firstSuit != suits[i] ){
				return INVALID;
			}
		}
		
		return new Tuple( FLUSH.rank(), ranks[0], FLUSH.description(), Arrays.copyOfRange(ranks, 1, ranks.length) );
	}
	
	
	public static final Tuple straight( int[] ranks ){
		
		int firstRank	 = ranks[ 0 ];
		
		for( int i = 1; i< ranks.length; i ++ ){
			if( (firstRank - ranks[i]) != 1 ){
				return INVALID;
			}
			
			firstRank = ranks[i];
		}
		
		return new Tuple( STRAIGHT.rank(), ranks[0], STRAIGHT.description(), Arrays.copyOfRange(ranks, 1, ranks.length) );
	}
	
	
	public static final Tuple twoPair( int[] ranks ){
		int firstPairRank	= kind( 2, ranks );
		int secondPairRank 	= kind( 2, removeRank(firstPairRank, ranks) );
		
		int highestPair		= Math.max(firstPairRank, secondPairRank);
		int smallerPair		= ( firstPairRank == highestPair ) ? secondPairRank : firstPairRank;
		int remainingRank	= removeRank( smallerPair, removeRank(highestPair, ranks) )[ 0 ];
		
		return ( firstPairRank == -1 || secondPairRank == -1 ) ? INVALID : new Tuple(TWO_PAIR.rank(), highestPair, TWO_PAIR.description(), smallerPair, remainingRank );
	}

	
	public static final Tuple onePair( int[] ranks ){
		int onePair			= kind( 2, ranks );
		return ( onePair == -1 ) ? INVALID : new Tuple( ONE_PAIR.rank(), onePair, ONE_PAIR.description(), removeRank(onePair, ranks) );
	}
	
	
	public static final Tuple highCard( int[] ranks ){
		return new Tuple( HIGH_CARD.rank(), ranks[0], HIGH_CARD.description(), Arrays.copyOfRange(ranks, 1, ranks.length) );
	}
	
	
	
	/**
	 * @param	Hand  = [5S, 8C,  7S,  3D,  5S]
	 * 
	 * @return  Ranks = [5,   8,   7,   3,   5]
	 * @return  Suits = [S,   C,   S,   D,   S]
	 */
	protected final Pair<int[], char[]> extractHand( String[] hand ){
		
		int size		= hand.length;
		int[] ranks 	= new int[ size ];
		char[] suits 	= new char[ size ];
		
		for( int i =0; i < size; i++ ){
			String card = hand[ i ];
			int length	= card.length();
			int sLength	= card.length()-1;
			
			ranks[ i ]	= Integer.valueOf( card.substring( 0, sLength ) );
			suits[ i ]	= card.substring( sLength, length ).charAt( 0 );
		}
				
		return new Pair<int[], char[]>(ranks, suits);
	
	}
	
	
	//HowMany = 4, Ranks = [3, 6, 6, 6, 6], Returns 6
	//HowMany = 3, Ranks = [8, 7, 7, 7, 2], Returns 7
	//HowMany = 2, Ranks = [8, 6, 4, 3, 2], Returns -1
	public final static int kind( int howMany, int[] ranks ){
		
		Map<Integer, Integer> countMap = new HashMap<Integer, Integer>( ranks.length * 2 );
		
		for( int rank : ranks ){
			Integer count = countMap.get( rank );
			int increment = ( count == null ) ? 1 : ( count + 1 );
			countMap.put( rank,  increment );
		}
		
		int kindOf 		  = -1;
		for( Entry<Integer, Integer> entry : countMap.entrySet() ){
			int key = entry.getKey();
			int val = entry.getValue();
			
			if ( howMany == val ){
				kindOf   = key;
				break;
			}
		}
		
		return kindOf;
	
	}	
	
	
	//Replaces picture cards with numerical equivalent so [6S,   4C,  JS,  7S,  KC]  -->  [ 6S,  4C, 11S, 7S, 13C]
	public final String[] removePictures( String[] hand ){
		
		String[] picturesLessHand = new String[hand.length];
		
		for( int i =0; i < hand.length; i++ ){
			String card			= hand[ i ];
			String suit 		= card.substring( card.length()-1, card.length() );
			String rank 		= card.substring( 0, card.length()-1 );
			String piclessRank	= RANKS.getValue( rank );
			
			picturesLessHand[i]	= piclessRank + suit ;
		}
			
		return picturesLessHand;
	}
	
	
	//Removes a rank from rank array.
	//Remove 4 from [6,4,11,7,13] should return [6,11,7,13] 
	public final static int[] removeRank( int rankToRemove, int[] ranks ){
		
		List<Integer> removedRanks 	= new LinkedList<Integer>();
		for( int rank : ranks ){
			if( rank != rankToRemove ){
				removedRanks.add( rank );
			}
		}
		
		int i	 					= 0; 
		int[] removedRankArray	 	= new int[ removedRanks.size()];
		for( Integer rank : removedRanks ){
			removedRankArray[i++] = rank;
		}
		
		return removedRankArray;
				
	}


}

	