package chess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by test on 7/29/2015.
 */
public class PlayerTest {

    Player p;
    @Before
    public void setUp() throws Exception {
        p = new Player(true);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSolveAmbiguity() throws Exception {
<<<<<<< HEAD
        assertEquals(true, p.solveAmbiguity("Nb1e2"));
=======
//        assertEquals(true, p.solveAmbiguity("N9e2"));
//        assertEquals(true, p.solveAmbiguity("N9e2"));
//        assertEquals(true, p.solveAmbiguity("N9e2"));
>>>>>>> 05be2ce2b11a1142d351ac17c2b903118bcd066c
    }
}
