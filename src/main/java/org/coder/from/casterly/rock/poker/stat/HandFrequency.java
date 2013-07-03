package org.coder.from.casterly.rock.poker.stat;

import java.util.*;

import org.apache.commons.lang3.*;
import org.coder.from.casterly.rock.poker.core.*;
import org.coder.from.casterly.rock.poker.game.*;


public final class HandFrequency{

	
	public final static void computeHandFrequencies( String[] players, int handCount, int cardCount, int iterations ){
		
		System.out.println( "Starting to compute Hand Frequencies of  " );
		
		Deck cardDeck					= new Deck( );
		Poker poker						= new Poker( );
		int iterationCount				= handCount * iterations;
		Map<PokerHandType, Integer> map	= new HashMap<PokerHandType, Integer>( 20 );
		
		for( int index =0 ; index < iterations; index++ ){
	
			CardHand cardHand			= cardDeck.dealCards( handCount, cardCount, players );
			
			for( String[] hand : cardHand.getAllHands() ){
				
				String[] piclessHand	= poker.removePictures( hand );
				Tuple result 			= poker.getHandRank( piclessHand );
				int rank				= result.getRank();
				
				PokerHandType handType	= PokerHandType.getHandType( rank );
				Integer handTypeCount	= map.get( handType );
				int count				= ( handTypeCount == null ) ? 0 : ( handTypeCount + 1 ); 
				
				map.put(handType, count );
				
			}
		
		}
		
		System.out.println( "======================================================= " );
		System.out.println( "Hand Frequency: ");
		System.out.println( "======================================================= " );
		
		for( PokerHandType handType : PokerHandType.getOrderedHandType() ){
			int count  			= map.containsKey(handType) ? map.get( handType ) : 0;
			double frequency 	= (double) count/iterationCount;
			
			System.out.println( StringUtils.rightPad(handType.name(), 20 ) + " = " + frequency );	
		}
		
				
	}

}
