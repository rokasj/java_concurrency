/*
 * Main.java
 * @author Rokas Juozapavicius
 * 1. Uzduotis. Lyginiu-nelyginiu musis
 */

package prog1;

/* 
   Lyginiu-nelyginiu musis.
   2 tipu gijos i ta pati lauka talpina reiksme.
   Lyginio tipo - lygine.
   Nelyginio tipo - nelygine.
   Laimi gija, kurios talpinama reiksme, pasibaigus programai,
   sutampa su giju keiciamo bendro lauko reiksme.
*/

public class Main {

    public static void main(String[] args) {

       try {
           if ((args[0] != null) && (args[0].equals("synchronized"))) {
               EvenOddThread.sync = true;
           }
       } catch (ArrayIndexOutOfBoundsException exc) {
            //System.out.println("Ivyko klaida " + exc);
       }
       System.out.println("Programa pradeda darba.");
       EvenOddThread.pradeti();
       System.out.println("Programa baigia darba.");

    }

}
