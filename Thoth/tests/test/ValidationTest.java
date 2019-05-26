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
}