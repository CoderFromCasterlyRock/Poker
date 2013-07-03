package org.coder.from.casterly.rock.poker.game;

import java.util.*;
import java.util.Map.Entry;

public final class CardHand{

	private final Map<String[], String> playerHandMap;
	
	public CardHand(  ){
		this.playerHandMap = new LinkedHashMap<String[], String>();
	}

	
	public final void addHand( String player, String[] hand ){
		playerHandMap.put( hand, player );
	}
	
	
	public final String getPlayer( String[] hand ){
		return playerHandMap.get( hand );
	}
	
	
	public final Set<String[]> getAllHands(){
		return playerHandMap.keySet();
	}
	
	
	public final Collection<String> getAllPlayers(){
		return playerHandMap.values();
	}
	

	public final Set<Entry<String[], String>> getCardEntry(){
		return playerHandMap.entrySet();
	}
	
		
	
	
}
