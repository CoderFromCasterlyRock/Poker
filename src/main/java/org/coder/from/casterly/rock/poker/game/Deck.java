package org.coder.from.casterly.rock.poker.game;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.coder.from.casterly.rock.poker.core.*;


public final class Deck{

	private final String[] initialDeck;
	
	private final static int DECK_SIZE	= SUITS.COUNT * RANKS.COUNT;
	
	public Deck(  ){
		
		int index			= 0;
		this.initialDeck	= new String[ DECK_SIZE ];
		
		for( SUITS suit : SUITS.values() ){
			for( RANKS rank : RANKS.values() ){
				initialDeck[ index ++] = new StringBuilder( rank.getShortName() ).append( suit.getShortName() ).toString(); 
			}
		}
	
	}
		
	
	protected final String[] getInitialDeck( ){
		return initialDeck;
	}
	
	public final static int getDeckSize(  ){
		return DECK_SIZE;
	}
		
	
	/*
	 * @param 
	 * HandCount = How many hands to deal, CardCount = How many cards in each hand
	 * 
	 * @return 
	 * Returns a set of shuffled hands
	 */
	public final CardHand dealCards( int handCount, int cardCount, String[] players ){
		
		if( handCount * cardCount > DECK_SIZE ){
			throw new IllegalArgumentException("We only have " + DECK_SIZE + " cards in the deck!" );
		}
		
		if( handCount != players.length ){
			throw new IllegalArgumentException("Players count must equal requested hand count of " + handCount );
		}
		
		System.out.println( " " );
		System.out.println("STARTING the Poker game with [" + handCount + "] players with [" + cardCount + "] cards each.");
		System.out.println( "");
		
		
		shuffleCards();
		
		int cardIndex 			= 0;
		CardHand cardHand		= new CardHand( );
		
		for ( int hIndex=0; hIndex < handCount; hIndex ++ ){
			
			String playerName	= players[ hIndex ];
			String[] hand 		= new String[ cardCount ];
			
			for ( int cIndex=0; cIndex < cardCount; cIndex ++ ){
				hand[cIndex]	= initialDeck[ cardIndex++ ];
			}
			
			cardHand.addHand( playerName, hand );
			System.out.println( StringUtils.rightPad(playerName, 10) + ": Dealt Hand " + Arrays.toString( hand ) );
		}
	
		return cardHand;
		
	}
	
	
	
	protected final void shuffleCards(  ){
		
		//Collections.shuffle( initialDeck );
		
		int maxShuffleIndex 			= DECK_SIZE - 1;
		
		for( int index=0; index < DECK_SIZE; index++ ){
			int randIndex 				= getRandomIndex( 0, maxShuffleIndex  );
			
			String currCard				= initialDeck[ index ];
			initialDeck[ index ]		= initialDeck[ randIndex ];
			initialDeck[ randIndex ]	= currCard;
		}
						
	}
	


	protected final int getRandomIndex( int Min, int Max ){
		return ( Min + (int)(Math.random() * ((Max - Min) + 1 ) ) );
	}
	
	
}
