package org.coder.from.casterly.rock.poker.game;

import java.util.*;
import org.junit.*;

import org.coder.from.casterly.rock.poker.core.Pair;
import org.coder.from.casterly.rock.poker.core.Tuple;
import org.coder.from.casterly.rock.poker.game.Poker;

import static org.junit.Assert.*;
import static org.coder.from.casterly.rock.poker.game.PokerHandType.*;


public class TestPoker{

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
	public void testCardComparator( ){
		
		String[] hand1 		= {"3S","4C","JS","7S","8C"};
		hand1				= poker.removePictures(hand1);
		Arrays.sort( hand1, Poker.CARD_COMPARATOR );
		assertTrue( "After sorting, Hand " + Arrays.toString(hand1) + " must have 11S as the highest card.", ("11S".equalsIgnoreCase(hand1[0]) ));
		
		String[] hand2 		= {"3S","AC","JS","2S","2C"};
		hand2				= poker.removePictures(hand2);
		Arrays.sort( hand2, Poker.CARD_COMPARATOR );
		assertTrue( "After sorting, Hand " +  Arrays.toString(hand2) + " must have 14C as the highest card.", ("14C".equalsIgnoreCase(hand2[0]) ));
		
	}
	
	
	@Test
	public void testRemovePictures( ){
		
		String[] handWithPictures1 		= {"AS","4C","JS","7S","KC"};
		String[] handWithoutPictures1	= poker.removePictures( handWithPictures1 );
		assertTrue( "Hand without pictures musn't be null!", (handWithoutPictures1 != null) && handWithoutPictures1.length > 0 );
		assertTrue( "Hand without pictures must have the same size as with pictures.", (handWithoutPictures1.length == handWithoutPictures1.length) );
		assertArrayEquals("Hand without pictures must match the expected hand.", handWithoutPictures1, new String[]{"14S","4C","11S","7S","13C"} ); 
		
		
		String[] handWithPictures2 		= {"2S","4C","7S","11C"};
		String[] handWithoutPictures2	= poker.removePictures( handWithPictures2 );
		assertTrue( "Hand without pictures musn't be null!", (handWithoutPictures2 != null) && handWithoutPictures2.length > 0 );
		assertTrue( "Hand without pictures must have the same size as with pictures.", (handWithoutPictures2.length == handWithoutPictures2.length) );
		assertArrayEquals("Hand without pictures must match the expected hand.", handWithPictures2, handWithoutPictures2 ); 
	}

	 
	@Test
	public void testRemoveRank( ){
		
		int removeRank1					= 11;
		int[] originalRank1 			= {14,4,11,7,13};
		int[] removedRank1				= Poker.removeRank( removeRank1, originalRank1 );
		
		int[] expectedRank1AfterRemove	= {14,4,7,13};
		assertArrayEquals("Removing " + removeRank1 + " from " + Arrays.toString( originalRank1 ) + " should return " + expectedRank1AfterRemove, expectedRank1AfterRemove, removedRank1 );
		
		int removeRank2					= 100;
		int[] originalRank2 			= {10,2,12,3,7};
		int[] removedRank2				= Poker.removeRank( removeRank2, originalRank2 );
		
		int[] expectedRank2AfterRemove	= {10,2,12,3,7};
		assertArrayEquals("Removing " + removeRank2 + " from " + Arrays.toString( originalRank2 ) + " should return " + expectedRank2AfterRemove, expectedRank2AfterRemove, removedRank2 );
				
	}
	
	
	@Test
	public void testExtractHand( ){
		
		String[] handWithPictures1 		= {"AS","4C","JS","7S","KC"};
		String[] handWithoutPictures1	= poker.removePictures( handWithPictures1 );
		
		Pair<int[], char[]> pair1		= poker.extractHand( handWithoutPictures1 );
		assertTrue( "Extracted pair musn't be null", (pair1 != null) );
		
		int[] ranks1 			 		= pair1.getFirst();
		assertTrue( "Extracted ranks musn't be null", (ranks1 != null) );
		assertTrue( "Length of extracted ranks must equal length of original hand.", ( handWithPictures1.length == ranks1.length) );
		assertArrayEquals("Extracted ranks must match expected rank of [14, 4, 11, 7, 13].", new int[]{14, 4, 11, 7, 13}, ranks1 );
		
		char[] suits1 					= pair1.getSecond();
		assertTrue( "Extracted suits musn't be null", (suits1 != null) );
		assertTrue( "Length of extracted suits must equal length of original hand.", ( handWithPictures1.length == suits1.length) );
		assertArrayEquals("Extracted suits must match expected rank of [S, C, S, S, C].", new char[]{'S', 'C', 'S', 'S', 'C'}, suits1 );
			
		
		
		
		String[] handWithPictures2 		= {"2D","5C","8H","4H","10C"};
		String[] handWithoutPictures2	= poker.removePictures( handWithPictures2 );
		
		Pair<int[], char[]> pair2		= poker.extractHand( handWithoutPictures2 );
		assertTrue( "Extracted pair musn't be null", (pair2 != null) );
		
		int[] ranks2 			 		= pair2.getFirst();
		assertTrue( "Extracted ranks musn't be null", (ranks2 != null) );
		assertTrue( "Length of extracted ranks must equal length of original hand.", ( handWithPictures2.length == ranks2.length) );
		assertArrayEquals("Extracted ranks must match expected rank of [2, 5, 8, 4, 10].", new int[]{2, 5, 8, 4, 10}, ranks2 );
		
		char[] suits2 					= pair2.getSecond();
		assertTrue( "Extracted suits musn't be null", (suits2 != null) );
		assertTrue( "Length of extracted suits must equal length of original hand.", ( handWithPictures2.length == suits2.length) );
		assertArrayEquals("Extracted suits must match expected rank of [D, C, H, H, C].", new char[]{'D', 'C', 'H', 'H', 'C'}, suits2 );
		
	}
	
	
	//HowMany = 4, Ranks = [3, 6, 6, 6, 6], Returns 6
	//HowMany = 3, Ranks = [8, 7, 7, 7, 2], Returns 7
	//HowMany = 2, Ranks = [8, 6, 4, 3, 2], Returns -1
	@Test
	public void testKind( ){
		
		int[] originalRank1 			= {4,2,6,9,10};
		int ofKind1						= Poker.kind( 1, originalRank1 );
		assertTrue("Rank " + Arrays.toString( originalRank1) + " contains one of all the cards.", ( -1 != ofKind1) );
		
		int[] originalRank2 			= {14,2,11,7,11};
		int ofKind2						= Poker.kind( 2, originalRank2 );
		assertTrue("Rank " + Arrays.toString( originalRank2) + " contains one pair of 11.", (11 == ofKind2) );
		int[] originalRank3 			= {14,2,8,7,11};
		int ofKind3						= Poker.kind( 2, originalRank3 );
		assertTrue("Rank " + Arrays.toString( originalRank3) + " doesnt contain a pair of anything.", (-1 == ofKind3) );
		
		int[] originalRank4 			= {2,2,11,7,2};
		int ofKind4						= Poker.kind( 3, originalRank4 );
		assertTrue("Rank " + Arrays.toString( originalRank4) + " contains three 2s.", (2 == ofKind4) );
		int[] originalRank5 			= {7,7,11,4,5};
		int ofKind5						= Poker.kind( 3, originalRank5 );
		assertTrue("Rank " + Arrays.toString( originalRank5) + " doesn't contain 3 of anything.", (-1 == ofKind5) );
		
		int[] originalRank6 			= {7,7,11,7,7};
		int ofKind6						= Poker.kind( 4, originalRank6 );
		assertTrue("Rank " + Arrays.toString( originalRank6) + " contains four 7s.", (7 == ofKind6) );
		
				
	}
	
	
	@Test
	public void testHighCardRank( ){
		
		String[] highCard1			= new String[]{"8H", "10H", "2C", "KH", "9S"};
		highCard1					= poker.removePictures( highCard1 );
		Tuple handCardTuple1		= poker.getHandRank( highCard1 );
		
		assertTrue( Arrays.toString( highCard1 ) + " has a rank of " + HIGH_CARD.rank(), (HIGH_CARD.rank() == handCardTuple1.getRank()) );
		assertTrue( "High card from " + Arrays.toString( highCard1 ) + " is 13.", (13 == handCardTuple1.getHighcard()) );
		assertArrayEquals( "Remaining card rank from " + Arrays.toString( highCard1 ) + " are [10, 9, 8, 2].", new int[]{10, 9, 8, 2}, handCardTuple1.getRemainingCards() );
		
	}
	
	
	@Test
	public void testOnePairRank( ){
	
		String[] onePairCard1		= new String[]{"4H", "10H", "2C", "4C", "9S"};
		onePairCard1				= poker.removePictures( onePairCard1 );
		Tuple onePairTuple1			= poker.getHandRank( onePairCard1 );
		
		assertTrue( Arrays.toString( onePairCard1 ) + " has a rank of " + ONE_PAIR.rank(), (ONE_PAIR.rank() == onePairTuple1.getRank()) );
		assertTrue( "High card from " + Arrays.toString( onePairCard1 ) + " is 4 (pair).", ( 4 == onePairTuple1.getHighcard()) );
		assertArrayEquals( "Remaining card ranks from " + Arrays.toString( onePairCard1 ) + " are [10, 9, 2].", new int[]{10, 9, 2}, onePairTuple1.getRemainingCards() );
		
	}
	
	
	@Test
	public void testTwoPairRank( ){
	
		String[] twoPairCard1		= new String[]{"4H", "10H", "2C", "4C", "10S"};
		twoPairCard1				= poker.removePictures( twoPairCard1 );
		Tuple twoPairTuple1			= poker.getHandRank( twoPairCard1 );
		
		assertTrue( Arrays.toString( twoPairCard1 ) + " has a rank of " + TWO_PAIR.rank(), (TWO_PAIR.rank() == twoPairTuple1.getRank()) );
		assertTrue( "High card from " + Arrays.toString( twoPairCard1 ) + " is 10 (pair).", ( 10 == twoPairTuple1.getHighcard()) );
		assertArrayEquals( "Remaining card ranks from " + Arrays.toString( twoPairCard1 ) + " are [4, 2].", new int[]{4, 2}, twoPairTuple1.getRemainingCards() );
			
	}
	
	
	@Test
	public void testThreeOfAKindRank( ){
	
		String[] threeKindCard1		= new String[]{"2H", "10H", "10C", "5C", "10S"};
		threeKindCard1				= poker.removePictures( threeKindCard1 );
		Tuple threeCardTuple1		= poker.getHandRank( threeKindCard1 );
		
		assertTrue( Arrays.toString( threeKindCard1 ) + " has a rank of " + THREE_OF_A_KIND.rank(), (THREE_OF_A_KIND.rank() == threeCardTuple1.getRank()) );
		assertTrue( "High card from " + Arrays.toString( threeKindCard1 ) + " is 10 (three of 10).", ( 10 == threeCardTuple1.getHighcard()) );
		assertArrayEquals( "Remaining card ranks from " + Arrays.toString( threeKindCard1 ) + " are [5, 2].", new int[]{5, 2}, threeCardTuple1.getRemainingCards() );
			
	}
	
	
	@Test
	public void testStraightRank( ){
	
		String[] straightCard1		= new String[]{"2H", "5H", "6C", "4C", "3S"};
		straightCard1				= poker.removePictures( straightCard1 );
		Tuple straightTuple1		= poker.getHandRank( straightCard1 );
		
		assertTrue( Arrays.toString( straightCard1 ) + " has a rank of " + STRAIGHT.rank(), (STRAIGHT.rank() == straightTuple1.getRank()) );
		assertTrue( "High card from " + Arrays.toString( straightCard1 ) + " is 6 (Straight starting with 6).", ( 6 == straightTuple1.getHighcard()) );
		assertArrayEquals( "Remaining card ranks from " + Arrays.toString( straightCard1 ) + " are [5, 4, 3, 2].", new int[]{5, 4, 3, 2}, straightTuple1.getRemainingCards() );
			
	}
	
	
	
	@Test
	public void testFlushRank( ){
	
		String[] flushCard1			= new String[]{"6C", "5C", "7C", "6C", "JC"};
		flushCard1					= poker.removePictures( flushCard1 );
		Tuple flushTuple1			= poker.getHandRank( flushCard1 );
		
		assertTrue( Arrays.toString( flushCard1 ) + " has a rank of " + FLUSH.rank(), (FLUSH.rank() == flushTuple1.getRank()) );
		assertTrue( "High card from " + Arrays.toString( flushCard1 ) + " is 11 (Flush starting with 11).", ( 11 == flushTuple1.getHighcard()) );
		assertArrayEquals( "Remaining card ranks from " + Arrays.toString( flushCard1 ) + " are [7, 6, 6, 5].", new int[]{7, 6, 6, 5}, flushTuple1.getRemainingCards() );
			
	}
	
	
	
	@Test
	public void testFullHouseRank( ){
	
		String[] fullHouseCard1		= new String[]{"6C", "5H", "5H", "6C", "5H"};
		fullHouseCard1				= poker.removePictures( fullHouseCard1 );
		Tuple fullHouseTuple1		= poker.getHandRank( fullHouseCard1 );
		
		assertTrue( Arrays.toString( fullHouseCard1 ) + " has a rank of " + FULL_HOUSE.rank(), (FULL_HOUSE.rank() == fullHouseTuple1.getRank()) );
		assertTrue( "High card from " + Arrays.toString( fullHouseCard1 ) + " is 5 (Flush with 3 5s and 2 6s6).", ( 5 == fullHouseTuple1.getHighcard()) );
		assertArrayEquals( "Remaining card ranks from " + Arrays.toString( fullHouseCard1 ) + " are [6].", new int[]{6}, fullHouseTuple1.getRemainingCards() );
			
	}
	
	
	
	@Test
	public void testFourOfAKindRank( ){
	
		String[] fourOfAKindCard1	= new String[]{"JH", "JC", "JS", "7D", "JD"};
		fourOfAKindCard1			= poker.removePictures( fourOfAKindCard1 );
		Tuple fourOfAKindTuple1		= poker.getHandRank( fourOfAKindCard1 );
		
		assertTrue( Arrays.toString( fourOfAKindCard1 ) + " has a rank of " + FOUR_OF_A_KIND.rank(), (FOUR_OF_A_KIND.rank() == fourOfAKindTuple1.getRank()) );
		assertTrue( "High card from " + Arrays.toString( fourOfAKindCard1 ) + " is 11 (Four of 11).", ( 11 == fourOfAKindTuple1.getHighcard()) );
		assertArrayEquals( "Remaining card ranks from " + Arrays.toString( fourOfAKindCard1 ) + " is [7].", new int[]{7}, fourOfAKindTuple1.getRemainingCards() );
			
	}
	
	
	@Test
	public void testStraightFlush( ){
	
		String[] straightFlushCard1	= new String[]{"JH", "10H", "7H", "9H", "8H"};
		straightFlushCard1			= poker.removePictures( straightFlushCard1 );
		Tuple straightFlushTuple1	= poker.getHandRank( straightFlushCard1 );
		
		assertTrue( Arrays.toString( straightFlushCard1 ) + " has a rank of " + STRAIGHT_FLUSH.rank(), (STRAIGHT_FLUSH.rank() == straightFlushTuple1.getRank()) );
		assertTrue( "High card from " + Arrays.toString( straightFlushCard1 ) + " is 11 (Straight Flush with of 11).", ( 11 == straightFlushTuple1.getHighcard()) );
		assertArrayEquals( "Remaining card ranks from " + Arrays.toString( straightFlushCard1 ) + " is [10, 9, 8, 7].", new int[]{10, 9, 8, 7}, straightFlushTuple1.getRemainingCards() );
			
	}
	
	
	
}
