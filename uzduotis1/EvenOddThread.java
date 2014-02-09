/*
 * EvenOddThread.java
 * @author Rokas Juozapavicius
 * 1. Uzduotis. Lyginiu-nelyginiu musis
 */

package prog1;

/*
   Gijos klase (lyginio arba nelyginio tipo).
*/

public class EvenOddThread extends Thread {
    // Gijos objekto specifiniai duomenys.
    static boolean sync = false;
    MutualField mutualField;
    int skaicius;
   
    // Konstruktorius, skirtas perduoti duomenis gijos objektui.
    public EvenOddThread(MutualField mutualField, int skaicius) {
        this.mutualField = mutualField;
        this.skaicius = skaicius;
    }

    // Metodas, vykdomas paleidus gija.
    // Thread.run().
    public void run() {

        System.out.println("Gija " + this + " paleista");
        // Ciklas (didesnis iteraciju skaicius padidina duomenu skaitymo/atnaujinimo konflikto galimybe).
        for (int i = 0; i < 1000000; i++) {
            if (sync) {
                // Sinchronizuoto bloko pradzia.
                // Sio bloko viduje galima saugiai skaityti/modifikuoti objekto bendrus laukus.
                synchronized(mutualField) {
                    // Kontrolinis spausdinimas, kad isitikinti vienalaikiu giju veikimu .
                    //System.out.println("Gija " + this + " pries atnaujinant bendra kintamaji: " + mutualField.n);
                    // Kvieciamas metodas, kuris modifikuoja objekto lauko reiksme.
                    keistiBendraLauka(mutualField);
                    // Kontrolinis spausdinimas, kad isitikinti vienalaikiu giju veikimu.
                    //System.out.println("Gija " + this + " atnaujino bendra kintamaji: " + mutualField.n);
                }
            } else {
                // Nesinchronizuoto bloko pradzia.
                // Gauti rezultatai ivykdzius si bloka gali buti nekorektiski.
                {
                    // Kontrolinis spausdinimas, kad isitikinti vienalaikiu giju veikimu.
                    //System.out.println("Gija " + this + " pries atnaujinant bendra kintamaji: " + mutualField.n);
                    // Kvieciamas metodas, kuris modifikuoja objekto lauko reiksme
                    keistiBendraLauka(mutualField);
                    // Kontrolinis spausdinimas, kad isitikinti vienalaikiu giju veikimu.
                    //System.out.println("Gija " + this + " atnaujino bendra kintamaji: " + mutualField.n);
                }
            }
        }
        System.out.println("Gija " + this + " baigia darba");

    }
   
    // Metodas paleidziantis gijas darbui ir isvedantis rezultata.
    public static void pradeti() {
        // Sukuriamas objektas, kuri bendrai naudos kelios gijos.
        MutualField mutualField = new MutualField();
        // Pradine reiksme.
        mutualField.n = 0;

        try { 
            // Sukuriama ir startuojama lyginio tipo gija.
            Thread t1 = new EvenOddThread(mutualField, 0); 
            t1.start();

            // Sukuriama ir startuojama nelyginio tipo gija.
            Thread t2 = new EvenOddThread(mutualField, 1);
            t2.start();

            // Laukiama, kol abi gijos baigs darba. 
            t1.join(); t2.join();

            // Isvedamas galutinis rezultatas.
            System.out.println("Rezultatas n = " + mutualField.n + ".");
        } catch (InterruptedException exc) {
            System.out.println("Ivyko klaida " + exc);
        }

    }

    public void keistiBendraLauka(MutualField mutualField) {
       mutualField.priskirti(skaicius);
       if (mutualField.n != this.skaicius) {
                System.out.println("Ivyko kolizija!" + mutualField.n + " " + this.skaicius);
            }
     }

}
