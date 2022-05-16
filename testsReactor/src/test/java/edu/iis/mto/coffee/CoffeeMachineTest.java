package edu.iis.mto.coffee;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import edu.iis.mto.coffee.machine.*;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.TreeMap;

@ExtendWith(MockitoExtension.class)
class CoffeeMachineTest {

    @Mock
    private CoffeeGrinder grinder;
    @Mock
    private MilkProvider milkProvider;
    @Mock
    private CoffeeReceipes receipes;

    private CoffeeMachine coffeeMachine;
    private CoffeeOrder coffeeOrder;
    private Map<CoffeeSize,Integer> waterAmount;
    @BeforeEach
    void setUp(){
        coffeeOrder=CoffeeOrder.builder().withSize(CoffeeSize.STANDARD).withType(CoffeeType.CAPUCCINO).build();
        coffeeMachine=new CoffeeMachine(grinder,milkProvider,receipes);
        waterAmount = new TreeMap<>();
        waterAmount.put(CoffeeSize.STANDARD,5);
    }

    @Test
    void coffeeMachineReturnReadyCoffee() throws GrinderException {
        when(!grinder.grind(any())).thenReturn(true);
        when(receipes.getReceipe(any())).thenReturn(CoffeeReceipe.builder().withMilkAmount(10).withWaterAmounts(waterAmount).build());
        Coffee result=coffeeMachine.make(coffeeOrder);
        assertEquals(Status.READY,result.getStatus());
    }

    @Test
    void coffeeMachinedoesNotHaveCoffeBeans() throws GrinderException {
        when(!grinder.grind(any())).thenReturn(false);
        when(receipes.getReceipe(any())).thenReturn(CoffeeReceipe.builder().withMilkAmount(10).withWaterAmounts(waterAmount).build());
        Coffee result=coffeeMachine.make(coffeeOrder);
        String expectedMessage="no coffee beans available";
        assertEquals(Status.ERROR,result.getStatus());
        assertEquals(expectedMessage,result.getMessage());
    }

    @Test
    void coffeeMachineHasBrokenHeater() throws HeaterException, GrinderException {
        when(!grinder.grind(any())).thenReturn(true);
        when(receipes.getReceipe(any())).thenReturn(CoffeeReceipe.builder().withMilkAmount(10).withWaterAmounts(waterAmount).build());
        doThrow(HeaterException.class).when(milkProvider).heat();
        Coffee result=coffeeMachine.make(coffeeOrder);
        System.out.println(result.getMessage());
        assertEquals(Status.ERROR,result.getStatus());
    }

    @Test
    void coffeeMachineDoesNotHaveRecipeForCoffeeOrder() {
        Coffee result=coffeeMachine.make(coffeeOrder);
        assertEquals(Status.ERROR,result.getStatus());
    }

    @Test
    void coffeeMachineShouldCallGrinderMilkProviderReceipesMethodsWithGoodAmountOfTimes() throws GrinderException, HeaterException {
        when(!grinder.grind(any())).thenReturn(true);
        when(receipes.getReceipe(any())).thenReturn(CoffeeReceipe.builder().withMilkAmount(10).withWaterAmounts(waterAmount).build());
        coffeeMachine.make(coffeeOrder);
        verify(receipes,times(4)).getReceipe(any());
        verify(grinder,times(1)).grind(any());
        verify(milkProvider,times(1)).heat();
        verify(milkProvider,times(1)).pour(anyInt());
    }

}
