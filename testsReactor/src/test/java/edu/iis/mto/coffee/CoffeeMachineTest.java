package edu.iis.mto.coffee;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import edu.iis.mto.coffee.machine.CoffeeGrinder;
import edu.iis.mto.coffee.machine.MilkProvider;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CoffeeMachineTest {

    @Mock
    private CoffeeGrinder grinder;
    @Mock
    private MilkProvider milkProvider;
    @Mock
    private CoffeeReceipes receipes;

    @Test
    void itCompiles() {
        assertEquals(true,true);
    }

}
