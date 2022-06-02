package javagraph;

/**
 * 
 * @author Jakub Miętki
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javagraph.fileManager.openNode;

public class openNodeTest {

    @Test
    void readFromStream_returnValuesTest() {

        // given
        int lineNumber = 11;
        int colNumber = 10;

        int actNumber_gora = 1;
        int actNumber_dol = 21;
        int actNumber_lewa = 10;
        int actNumber_prawa = 12;
        int result_dol = 0;
        int result_gora = 0;
        int result_lewa = 0;
        int result_prawa = 0;
        // when

        try {
            openNode openNode = new openNode();

            Method method = openNode.getClass().getDeclaredMethod("readFromStream", int.class, int.class, int.class);
            method.setAccessible(true);
            result_dol = (int) method.invoke(openNode, actNumber_dol, lineNumber, colNumber);

            result_gora = (int) method.invoke(openNode, actNumber_gora, lineNumber, colNumber);

            result_lewa = (int) method.invoke(openNode, actNumber_lewa, lineNumber, colNumber);
            result_prawa = (int) method.invoke(openNode, actNumber_prawa, lineNumber, colNumber);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // then

        Assertions.assertEquals(3, result_dol);
        Assertions.assertEquals(2, result_gora);
        Assertions.assertEquals(1, result_prawa);
        Assertions.assertEquals(0, result_lewa);

    }

}
