/*
 * ResourceCounter.java
 * @author Rokas Juozapavicius
 * 2. Uzduotis. Semaforas kaip resursu skaitliukas.
 */

package prog2;

/*
   Klase aprasanti semafora kaip resursu skaitliuka.
   Resursai yra pakartotinio naudojimo resursai. 
*/

class ResourceCounter {

    // Sistemoje esamu laisvu resursu kiekis.
    private int resursai;
    
    public ResourceCounter(int resursai) {
        this.resursai = resursai;
    }

    // Metodas kvieciamas gijai prasant resurso.
    public synchronized void request() {

        while (resursai == 0) {
            try {
                System.out.println("Gija " + Thread.currentThread() + " laukia resurso. Like resursai: " + resursai);
                wait();
             } catch (InterruptedException exc) {
                System.out.println("Gijos darbas buvo pertrauktas: " + exc);
            }
        }
        resursai = resursai - 1;
        System.out.println("Gija " + Thread.currentThread() + " naudojasi resursu. Like resursai: " + resursai);

    }

    // Metodas kvieciamas gijai atlaisvinus resursa.
    public synchronized void release() {

        resursai = resursai + 1;
        notify();
        System.out.println("Gija " + Thread.currentThread() + " pasinaudojo resursu. Esami resursai: " + resursai);

    }

    // Metodas grazinantis sistemoje esamu laisvu resursu kieki.
    public int numberAvailable() {

        return resursai;

    }

}
