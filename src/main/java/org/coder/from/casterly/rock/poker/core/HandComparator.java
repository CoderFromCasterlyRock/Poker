package org.coder.from.casterly.rock.poker.core;

import java.util.*;

/*
 *	Given
 *	[4H, 2S, 10D, 3H, 8C] vs [5H, 6H, 2H, 3S, 6C]
 *  
 *  Converts to
 *  [4, 2, 10, 3, 8] vs [5, 6, 2, 3, 6] 
 *  
 *  and then sorts them in descending order!
 *	
 */

public final class HandComparator implements Comparator<String>{

	@Override
	public int compare( String card1, String card2 ){
		
		int rank1 = Integer.valueOf( card1.substring( 0, card1.length() -1 ) );
		int rank2 = Integer.valueOf( card2.substring( 0, card2.length() -1 ) );
		
		return ( rank2 - rank1 );
	
	}
	

}
