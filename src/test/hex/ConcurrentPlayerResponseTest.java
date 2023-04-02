package test.hex;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.*;

import main.hex.*;

public class ConcurrentPlayerResponseTest {
	
	@Test
	public void placingMovePlacesMove() {
		var response = new ConcurrentPlayerResponse();
		var move = mock(Move.class);
		response.placeMove(move);
		assertEquals(move, response.getMove());
	}
	
	@Test(expected = HexException.class)
	public void placingMultipleMovesThrowsException() {
		var response = new ConcurrentPlayerResponse();
		var move = mock(Move.class);
		response.placeMove(move);
		response.placeMove(move);
	}
	
	@Test(expected = HexException.class)
	public void placingNullMoveThrowsException() {
		new ConcurrentPlayerResponse().placeMove(null);
	}
	
	@Test
	public void gettingMoveWhenNoneIsPlacedReturnsNull() {
		assertEquals(null, new ConcurrentPlayerResponse().getMove());
	}
	
	@Test
	public void settingErrorPlacesError() {
		var response = new ConcurrentPlayerResponse();
		var error = mock(Throwable.class);
		response.setError(error);
		assertEquals(error, response.getError());
	}
	
	@Test
	public void gettingErrorWhenNoneIsSetReturnsNull() {
		assertEquals(null, new ConcurrentPlayerResponse().getError());
	}
	
	@Test(expected = HexException.class)
	public void settingNullErrorThrowsException() {
		new ConcurrentPlayerResponse().setError(null);
	}
}
