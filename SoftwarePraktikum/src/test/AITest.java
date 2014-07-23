package test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import core.*;

public class AITest {

	@Test
	public void WaspVsWaspTest() {

		City_Graph tester = new City_Graph();
		tester.loadMapByPath("res/test.mp");
		Wasp p1 = new Wasp(Player.Rom);
		Wasp p2 = new Wasp(Player.Cathargo);
		ConsolGame cg = new ConsolGame(null, tester, p1, p2, false);
		History history = cg.getHistory();

		History shouldBe = new History();
		shouldBe.addStateAsString("CNNR");
		shouldBe.addStateAsString("CNNR");
		shouldBe.addStateAsString("CNNR");

		for (int i = 0; i < 3; i++) {
			assertEquals(shouldBe.getGameStateAt(i), history.getGameStateAt(i));
		}

	}

	@Test
	public void WaspVsSlothTest() {
		City_Graph tester = new City_Graph();
		tester.loadMapByPath("res/test.mp");
		Wasp p1 = new Wasp(Player.Rom);
		Sloth p2 = new Sloth(Player.Cathargo);
		ConsolGame cg = new ConsolGame(null, tester, p1, p2, false);
		History history = cg.getHistory();

		History shouldBe = new History();
		shouldBe.addStateAsString("CNNR");
		shouldBe.addStateAsString("CNNR");
		shouldBe.addStateAsString("CCNR");
		shouldBe.addStateAsString("CCNR");
		shouldBe.addStateAsString("CCCN");
		shouldBe.addStateAsString("CCCN");
		shouldBe.addStateAsString("NNNN");
		shouldBe.addStateAsString("RNNN");
		shouldBe.addStateAsString("NCNN");
		shouldBe.addStateAsString("NCNN");
		shouldBe.addStateAsString("CCNN");
		shouldBe.addStateAsString("CCNN");
		shouldBe.addStateAsString("CCNN");

		for (int i = 0; i < 13; i++) {
			assertEquals(shouldBe.getGameStateAt(i), history.getGameStateAt(i));
		}

	}

	@Test
	public void SlothVsWaspTest() {
		City_Graph tester = new City_Graph();
		tester.loadMapByPath("res/test.mp");
		Sloth p1 = new Sloth(Player.Rom);
		Wasp p2 = new Wasp(Player.Cathargo);
		ConsolGame cg = new ConsolGame(null, tester, p1, p2, false);
		History history = cg.getHistory();

		History shouldBe = new History();
		shouldBe.addStateAsString("CNNR");
		shouldBe.addStateAsString("NRNR");
		shouldBe.addStateAsString("NRNR");
		shouldBe.addStateAsString("RRNR");
		shouldBe.addStateAsString("RRNR");
		shouldBe.addStateAsString("NNNN");
		shouldBe.addStateAsString("CNNN");
		shouldBe.addStateAsString("NRNN");
		shouldBe.addStateAsString("NRNN");
		shouldBe.addStateAsString("RRNN");
		shouldBe.addStateAsString("RRNN");
		shouldBe.addStateAsString("RRRN");
		shouldBe.addStateAsString("RRRN");
		shouldBe.addStateAsString("RRRN");

		for (int i = 0; i < 14; i++) {
			assertEquals(shouldBe.getGameStateAt(i), history.getGameStateAt(i));
		}
	}

	@Test
	public void SlothVsSlothTest() {
		City_Graph tester = new City_Graph();
		tester.loadMapByPath("res/test.mp");
		Sloth p1 = new Sloth(Player.Rom);
		Sloth p2 = new Sloth(Player.Cathargo);
		ConsolGame cg = new ConsolGame(null, tester, p1, p2, false);
		History history = cg.getHistory();

		History shouldBe = new History();
		shouldBe.addStateAsString("CNNR");
		shouldBe.addStateAsString("NRNR");
		shouldBe.addStateAsString("NRNR");
		shouldBe.addStateAsString("RRNR");
		shouldBe.addStateAsString("NNCN");
		shouldBe.addStateAsString("RNCN");
		shouldBe.addStateAsString("NCCN");

		for (int i = 0; i < 7; i++) {
			assertEquals(shouldBe.getGameStateAt(i), history.getGameStateAt(i));
		}
	}

}
