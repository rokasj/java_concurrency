/*
   Interval.java
   @Author R. Juozapavicius
   3. Uzduotis. Apibreztinio integralo skaiciavimas

   Intervalo, kuriame skaiciuojamas integralas klase.
*/

package prog3;

public class Interval {

    double a;
    double b;
    double epsilon;
    double S;

    public Interval(double a, double b, double epsilon, double S) {

        this.a = a;
        this.b = b;
        this.epsilon = epsilon;
        this.S = S;

    }

}
