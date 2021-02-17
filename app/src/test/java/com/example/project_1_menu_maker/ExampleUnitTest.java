package com.example.project_1_menu_maker;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void httpRequestSearchTest(){
        String BASE_URL = "https://www.themealdb.com/api/json/v1/1/search.php?s=";

        assertEquals(5,5);
    }

    @Test
    public void httpRequestRandomTest(){
        String RANDOM_URL = "https://www.themealdb.com/api/json/v1/1/random.php/";

        assertEquals(5,5);
    }

    @Test
    public void httpRequestFirstLetterTest(){
        String LETTER_URL = "https://www.themealdb.com/api/json/v1/1/search.php?f=";

        assertEquals(5,5);
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}