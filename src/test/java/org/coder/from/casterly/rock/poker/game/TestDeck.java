package org.coder.from.casterly.rock.poker.game;

import java.util.*;
import org.junit.*;

import org.coder.from.casterly.rock.poker.core.*;
import org.coder.from.casterly.rock.poker.game.CardHand;
import org.coder.from.casterly.rock.poker.game.Deck;

import static org.junit.Assert.*;


public class TestDeck{

	private Deck deck;
	
	@Before
	public void init( ){
		deck  = new Deck();
	}
	
	
	@Test
	public void testInit( ){
		assertTrue( "Deck musn't be null!", (deck != null) );
		assertTrue( "Deck size must equal the product of SUITS and CARDS.",  Deck.getDeckSize()  == (SUITS.COUNT * RANKS.COUNT) );
	}
	
	
	@Test
	public void testInitialDeck( ){
		String[] initialDeck	  	= deck.getInitialDeck();
		Set<String> initialDeckSet 	= new HashSet<String>(Arrays.asList(initialDeck));
		
		assertTrue( "Initial deck must be valid.", initialDeck != null && initialDeck.length > 0 );
		assertTrue( "Initial deck size must equal the product of SUITS and CARDS.",  initialDeck.length  == (SUITS.COUNT * RANKS.COUNT) );
		
		for( SUITS suit: SUITS.values() ){
		
			for( RANKS rank: RANKS.values() ){
				String card = rank.getShortName() + suit.getShortName();
				assertTrue( "Initial card deck must contain all the possible cards." + card, ( initialDeckSet.contains(card) ) );
			}
			
		}
				
	}
	

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidDealtHands( ){
		int handCount 		= 100;
		int cardCount 		= 200;
		String[] players	= new String[]{};
		
		deck.dealCards( handCount, cardCount, players );
			
	}

		
	@Test
	public void testValidDealtHands( ){
		
		int handCount 		= 5;
		int cardCount 		= 5; 
		String[] players	= new String[]{"TestPlayer1", "TestPlayer2", "TestPlayer3", "TestPlayer4", "TestPlayer5"};
		
		CardHand cardHands 	= deck.dealCards( handCount, cardCount, players );	
		assertTrue( "Dealt card hand must be valid.", cardHands != null  );
		
		Set<String[]> hands	= cardHands.getAllHands();
		assertTrue( "Number of dealt hands must equal expected hand count of  " + handCount, (hands.size() == handCount) );
		
		for( String[] hand : hands ){
			assertTrue( "Number of dealt cards in " + hand + " must equal expected card count of  " + cardCount, (hand.length == handCount) );
		}
		
	}
	
	
}
