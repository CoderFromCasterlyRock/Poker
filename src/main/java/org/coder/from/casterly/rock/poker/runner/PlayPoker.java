package org.coder.from.casterly.rock.poker.runner;

import java.util.*;

import org.coder.from.casterly.rock.poker.core.*;
import org.coder.from.casterly.rock.poker.game.*;
import org.coder.from.casterly.rock.poker.stat.HandFrequency;


public class PlayPoker{

	private final String[] players;
		
	public PlayPoker( ){
		this.players	= new String[]{"Mr Jones", "Nazgul", "Tyrion", "Vic", "Bilbo"};
	}
	
	
	public final void play( int handCount, int cardCount ){
		
		Deck cardDeck					= new Deck( );
		Poker poker						= new Poker( );
		CardHand cardHand				= cardDeck.dealCards( handCount, cardCount, players );
		Pair<Tuple, String[]> pair 		= poker.playPoker( cardHand );
		String[] winningHand			= pair.getSecond();
		String winningPlayer			= cardHand.getPlayer( winningHand );
				
		System.out.println( " ");
		System.out.println( "=============================================================================");
		System.out.println( "[WINNER] " + winningPlayer + " ==> " + Arrays.toString( winningHand ) );
	
	}
	
	
	public static void main( String[] args ){

		PlayPoker playPoker 		= new PlayPoker( );
		
		HandFrequency.computeHandFrequencies( playPoker.players, 5, 5, 100000 );
		playPoker.play( 5, 5 );	
		
	}

}
