package test;

import org.junit.jupiter.api.Test;
import utils.Validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidationTest {

    @Test
    void nameValidation() {
        String dlugieImie = "Anastazjakonstantkiewuczjfksdalhfdsallsdkfjldksaldfbercuebwuicerbwobceow";
        String imiemalymiliterami = "jakiesimie";
        String imieWielkimiLiterami = "AREK";
        String zawieraspacje = "Mo nika";
        String zawieraCyfry = "arek1";
        String zawieraznakinterpunkcyjny = "!bartek";
        //możliwe wpisanie bardzo długiego ciągu znaków
        assertEquals(true, Validation.nameValidation(dlugieImie));
        assertEquals(false, Validation.nameValidation(imiemalymiliterami));
        assertEquals(false, Validation.nameValidation(imieWielkimiLiterami));
        assertEquals(false, Validation.nameValidation(zawieraspacje));
        assertEquals(false, Validation.nameValidation(zawieraCyfry));
        assertEquals(false, Validation.nameValidation(zawieraznakinterpunkcyjny));
    }

    @Test
    void isBolean() {
        String zero = "0";
        String jeden = "1";
        assertEquals(true, Validation.isBolean(zero));
        assertEquals(true, Validation.isBolean(jeden));

        String testtrue = "true";
        String testfalse = "false";
        assertEquals(false, Validation.isBolean(testtrue));
        assertEquals(false, Validation.isBolean(testfalse));

        String testdouble = "0.0";
        assertEquals(false, Validation.isBolean(testdouble));
        String testnumber = "123";
        assertEquals(false, Validation.isBolean(testnumber));

    }

    @Test
    void zioCodeValidation() {
        String valid = "35-310";
        assertEquals(true, Validation.zipCodeValidation(valid));
        String invalid1 = "35+321";
        assertEquals(false, Validation.zipCodeValidation(invalid1));
        String invalid2 = "35-4213";
        assertEquals(false, Validation.zipCodeValidation(invalid2));
        String invalid3 = "323-32";
        assertEquals(false, Validation.zipCodeValidation(invalid3));
    }

    @Test
    void onlyCharsValidation() {
        String valid = "Kraków";
        assertEquals(false, Validation.onlyCharsValidation(valid));
        String valid2 = "Gorzów wielkopolski";
        assertEquals(false, Validation.onlyCharsValidation(valid2));
        String invalid1 = "Gorzów wielkopolski2";
        assertEquals(false, Validation.onlyCharsValidation(invalid1));
        String invalid2 = ".Gorzów wielkopolski";
        assertEquals(false, Validation.onlyCharsValidation(invalid2));
    }

    @Test
    void streetValidation() {
        String valid = "Powstańców warszawy 43";
        assertEquals(false, Validation.streetValidation(valid));
        String invalid = "Kraków";
        assertEquals(false, Validation.streetValidation(invalid));
        String valid2 = "Gorzów wielkopolski";
        assertEquals(false, Validation.streetValidation(valid2));
        String invalid1 = "Gorzów wielkopolski2";
        assertEquals(false, Validation.streetValidation(invalid1));
        String invalid2 = ".Gorzów wielkopolski";
        assertEquals(false, Validation.streetValidation(invalid2));
    }
}