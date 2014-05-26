package test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.City;
import core.City_Graph;
import core.Move;
import core.Owner;
import core.VertexSet;



public class City_GraphTest {
	
	City_Graph city_graph = new City_Graph();
	
	@Before
	public void init(){
		city_graph.loadMap("res/test_zettel2_1.mp");
	}
	
	
	// test Gerald
	@Test
	public void getNeighbourhoodTest(){
		VertexSet set = new VertexSet();
		set.addVertex(new City(2, 213, 150, Owner.Neutral));
		set.addVertex(new City(3, 387, 150, Owner.Neutral));
		set.addVertex(new City(6, 300, 300, Owner.Rom));
		
		
		City testCity = city_graph.getCity(1);
		assertTrue(city_graph.getNeighbourhood(testCity).equals(set));
	}
	
	
	// test Gerald
	@Test 
	public void getNeighbourhoodTestNoNeighours() {
		
		City_Graph city_graph2 = new City_Graph();

		city_graph2.loadMap("res/singleCity.mp");
		
		City testCity = city_graph2.getCity(0);
		VertexSet emptySet = new VertexSet();
		assertTrue(city_graph2.getNeighbourhood(testCity).equals(emptySet));
		
	}
	
	
	// test Johannes
	@Test
	public void getVertexSetNeigourhoodTest(){
		VertexSet s = new VertexSet();
		s.addVertex(city_graph.getCity(6));
		VertexSet v = city_graph.getVertexSetNeighbourhood(s);
		assertTrue(!v.containsID(6));
	}
	
	
	// test johannes
	@Test
	public void getVertexSetNeigourhoodTest2(){
		VertexSet s = new VertexSet();
		s.addVertex(city_graph.getCity(0));
		s.addVertex(city_graph.getCity(1));
		VertexSet v = city_graph.getVertexSetNeighbourhood(s);
		
		VertexSet expect = new VertexSet();
		expect.addVertex(city_graph.getCity(9));
		expect.addVertex(city_graph.getCity(3));
		expect.addVertex(city_graph.getCity(6));
		expect.addVertex(city_graph.getCity(2));
		
		assertTrue(v.equals(expect));
	}
	
	
	
	// test Gerald
	@Test
	public void getConnectedComponentsTest(){
		VertexSet set = new VertexSet();
		
		set.addVertex(new City(4, 213, 250, Owner.Rom));
		set.addVertex(new City(6, 300, 300, Owner.Rom));
		set.addVertex(new City(5, 387, 250, Owner.Rom));
		
		City testCity = city_graph.getCity(4);
		assertTrue(city_graph.getConnectedComponents(testCity).equals(set));
	}
	
	
	// test Gerald
	@Test	
	public void getConnectedComponentsTestZero() {
		
		City_Graph city_graph2 = new City_Graph();

		city_graph2.loadMap("res/singleCity.mp");
		
		City testCity = city_graph2.getCity(0);
		VertexSet emptySet = new VertexSet();
		assertTrue(city_graph2.getNeighbourhood(testCity).equals(emptySet));
	}
	
	
	// test Johannes
	@Test
	public void getScoreTest(){
		assertEquals(city_graph.getScore(Owner.Rom), 6);
	}
	
	
	// test Johannes
	@Test
	public void getScoreTest2(){
		assertEquals(city_graph.getScore(Owner.Cathargo), 2);
	}
	
	
	// test kai
	@Test
	public void createMoveFromStringTest() {
		Move move = "R 5";
		
		assertEquals(move.toString(),"R 5");
	}
	
	//test Kai
	@Test
	public void createMoveFromStringTest() {
		Move move = "C 3";
		
		assertEquals(move.toString(),"C 3");
	}
	
	
	// test kai
	@Test
	public void convertGameStateToStringTest() {
		
		City_Graph city_graph2 = new City_Graph();

		city_graph2.loadMap("res/test.mp");
		
		assertEquals(city_graph2.convertGameStateToString(),"CNNR");
	}
	
	@Test
	public void convertGameStateToStringTest2() {
		
		City_Graph city_graph2 = new City_Graph();

		city_graph2.loadMap("res/test2.mp");
		
		assertEquals(city_graph2.convertGameStateToString(),"CRNNNNRNNC");
	}
	
	
	// test kai
	@Test
	public void gameStateTransitionTest() {
		City_Graph city_graph2 = new City_Graph();

		city_graph2.loadMap("res/test.mp");
		
		Move move = new Move("R 2");
		
		city_graph2 = city_graph2.gameStateTransition(move);
		
		assertEquals(city_graph2.convertGameStateToString(),"NRNR");
	}
	
	@Test
	public void gameStateTransitionTest2() {
		City_Graph city_graph2 = new City_Graph();

		city_graph2.loadMap("res/test2.mp");
		
		Move move = new Move("R 2");
		
		city_graph2 = city_graph2.gameStateTransition(move);
		
		assertEquals(city_graph2.convertGameStateToString(),"NRNR");
	}
	
		
	// test kai
	@Test
	public void printOutGameStateTransitionTest() {
		GameStateTest gameState = new GameStateTest();
		
	}
}
