/*
 * ResourceTaker.java
 * @author Rokas Juozapavicius
 * 2. Uzduotis. Semaforas kaip resursu skaitliukas.
 */

package prog2;

/*
   Gijos klase - resurso vartotojas.
*/

public class ResourceTaker extends Thread {

    // Gijos objekto specifiniai duomenys.
    ResourceCounter resourceCounter;
   
    // Konstruktorius, skirtas perduoti duomenis gijos objektui.
    public ResourceTaker(ResourceCounter resourceCounter) {
        this.resourceCounter = resourceCounter;
    }

    // Metodas, vykdomas paleidus gija.
    public void run() {

        System.out.println("Gija " + this + " paleista");
        for (int i = 0; i < 10; i++) {

            // Gijos-vartotojai naudoja turimus pakartotinio naudojimo resursus.
            // Resurso uzklausa - metodas request().
            resourceCounter.request();
            try {
                sleep(10);
            } catch (InterruptedException exc) {
                System.out.println("Gijos darbas buvo pertrauktas: " + exc);
            }
            resourceCounter.release();
        }
        System.out.println("Gija " + this + " baigia darba");

    }
   
    // Metodas paleidziantis gijas darbui.
    public static void pradeti() {

        // Sukuriamas resursu skaitliukas, kuri bendrai gali naudoti kelios gijos.
        // Esamu resursu skaicius nurodomas kuriant si objekta.
        ResourceCounter resourceCounter = new ResourceCounter(5);

        // Kontrolinis spausdinimas turimu resursu kiekis darbo pradzioje
        System.out.println("Resursu kiekis darbo pradzioje n = " + resourceCounter.numberAvailable() + ".");

        try { 
            // Sukuriamos ir startuojamos gijos (resursu vartotojai).
            Thread t1 = new ResourceTaker(resourceCounter);
            Thread t2 = new ResourceTaker(resourceCounter);
            Thread t3 = new ResourceTaker(resourceCounter);
            Thread t4 = new ResourceTaker(resourceCounter);
            Thread t5 = new ResourceTaker(resourceCounter);
            Thread t6 = new ResourceTaker(resourceCounter);
            Thread t7 = new ResourceTaker(resourceCounter);
            Thread t8 = new ResourceTaker(resourceCounter);

            t1.start();
            t2.start();
            t3.start();
            t4.start();
            t5.start();
            t6.start();
            t7.start();
            t8.start();

            // Laukiama, kol visos gijos baigs darba. 
            t1.join(); t2.join();
            t3.join(); t4.join();
            t5.join(); t6.join();
            t7.join(); t8.join();

            // Kontrolinis spausdinimas, skirtas patikrinti ar visi resursai yra grazinti.
            System.out.println("Resursu kiekis darbo pabaigoje n = " + resourceCounter.numberAvailable() + ".");

        } catch (InterruptedException exc) {
            System.out.println("Ivyko klaida " + exc);
        }

    }

}
