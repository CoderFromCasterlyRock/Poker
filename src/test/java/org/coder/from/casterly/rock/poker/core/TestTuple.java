package org.coder.from.casterly.rock.poker.core;

import java.util.*;
import org.junit.*;

import org.coder.from.casterly.rock.poker.game.*;

import static org.junit.Assert.*;


public class TestTuple{

	private Poker poker;
	
	@Before
	public void init( ){
		poker  = new Poker();
	}
	
	
	@Test
	public void testInit( ){
		assertTrue( "Poker musn't be null!", (poker != null) );
	}
	
	
	@Test
	public void testMaxHandRank( ){
		
		CardHand cardHand			= new CardHand();
		
		String[] hand1				= poker.removePictures(  new String[]{"4C", "5H", "3S", "5S", "2S"} );
		String[] hand2				= poker.removePictures(  new String[]{"QC", "QS", "10S", "10D", "KC"} );
		String[] hand3				= poker.removePictures(  new String[]{"8D", "3H", "KD", "JC", "8H"} );
		
		cardHand.addHand("TestPlayer1", hand1 );
		cardHand.addHand("TestPlayer2", hand2 );
		cardHand.addHand("TestPlayer3", hand3 );
		
		Pair<Tuple, String[]> pair 	= poker.playPoker( cardHand );
		String[] winningHand		= pair.getSecond();
		System.out.println( "MAx: " + Arrays.toString(winningHand) );
										
		
	}

}
