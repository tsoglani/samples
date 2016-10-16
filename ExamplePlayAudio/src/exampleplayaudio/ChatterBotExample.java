/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artif_intel;

/**
 *
 * @author tsoglani
 */
import com.google.code.chatterbotapi.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatterBotExample {

    public SecondExample(String str) {

        try {
            ChatterBotFactory factory = new ChatterBotFactory();

            ChatterBot bot1 = factory.create(ChatterBotType.CLEVERBOT);
            ChatterBotSession bot1session = bot1.createSession();

            ChatterBot bot2 = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
            ChatterBotSession bot2session = bot2.createSession();

            String s = str;
//        while (true) {

            System.out.println("que> " + s);

            s = bot2session.think(s);
            System.out.println("ans- " + s);
//
//            s = bot1session.think(s);
//        }
        } catch (Exception ex) {
            Logger.getLogger(SecondExample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws Exception {
new SecondExample("where is athens");
    }

}
