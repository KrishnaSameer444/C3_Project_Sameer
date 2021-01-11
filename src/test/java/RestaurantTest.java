import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    public Restaurant restaurant;

    @BeforeEach
    public void setup() {
    	LocalTime openingTime = LocalTime.parse("10:00:00");
        LocalTime closingTime = LocalTime.parse("12:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
    	Restaurant mockedrestaurant = Mockito.mock(Restaurant.class);
    	mockedrestaurant.closingTime = restaurant.closingTime;
    	mockedrestaurant.openingTime =restaurant.openingTime;
    	LocalTime OneHourBeforeClosingTime = LocalTime.of(11, 00);
    	Mockito.when(mockedrestaurant.getCurrentTime()).thenReturn(OneHourBeforeClosingTime);
    	Mockito.when(mockedrestaurant.isRestaurantOpen()).thenCallRealMethod();
    	
    	assertEquals(true, mockedrestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
    	Restaurant mockedrestaurant = Mockito.mock(Restaurant.class);
    	mockedrestaurant.closingTime = restaurant.closingTime;
    	mockedrestaurant.openingTime =restaurant.openingTime;
    	LocalTime OneHourBeforeOpeningTime = LocalTime.of(9, 00);
    	Mockito.when(mockedrestaurant.getCurrentTime()).thenReturn(OneHourBeforeOpeningTime);
    	Mockito.when(mockedrestaurant.isRestaurantOpen()).thenCallRealMethod();
    	
    	assertEquals(false, mockedrestaurant.isRestaurantOpen());
    	
    	LocalTime OneHourAfterClosingTime = LocalTime.of(13, 00);
    	Mockito.when(mockedrestaurant.getCurrentTime()).thenReturn(OneHourAfterClosingTime);
    	
    	assertEquals(false, mockedrestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

    	int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    
}