/*
   Main.java
   @Author R. Juozapavicius
   3. Uzduotis. Apibreztinio integralo skaiciavimas

   Duota tolydi neneigiama funkcija f(X) ir du taškai a,b.
   Rasti integralą  I[a,b] f(x) dx, aproksimuojant jį plotu figuros,
   aprėžtos ašimi x, kreive f(x) bei tiesėmis y = a, y = b.
   Sprendimo būdas - dalyti sritį [a,b] į daugelį mažesnių
   sričių vertikaliomis linijomis ir sumuoti pastarųjų plotus,
   kol pasieksime reikiamą tikslumą (žr. konspektus).
*/

package prog3;

public class Main {

    public static void main(String[] args) {

        double a, b;
        int nThreads;
        double precision;
        double dtime;

        try {
            if (args.length < 4
                   || ! ((nThreads = Integer.parseInt(args[0])) >= 0 && nThreads  <= 8 &&
                          (a = Double.parseDouble(args[1])) >= -499 && a <= 500 &&
                          (b = Double.parseDouble(args[2])) >= a && b <= 500 &&
                          (precision = Double.parseDouble(args[3])) >= 0.00000000000001 && precision <= 1)) {

                System.err.println("Multithreading performance test.");
                System.err.println("Parameters: number of threads: <1..8>");
                System.err.println("            interval a: <-499..500>  b: <a..500>");
                System.err.println("            precision: <0.00000000000001..1>");

                System.err.println("Make auto test: find workload (interval, precision) for > 1 sec...");

                try {
                    if (args[4].equals("debug")) {
                        Worker.debug = true;
                    }
                } catch (ArrayIndexOutOfBoundsException exc) {
                    // ...
                }

                a = -9;
                b = 10;
                Worker.nThreads = 1;
                precision = 0.00000000000001;

                while (a >= -499 && a <= 500 && b >= a && b <= 500) {
                    dtime = Worker.CalculateIntegral(a, b, precision);
                    if (dtime > 1.) break;
                    a -= 10; b += 10;
                }

                System.err.println("Test for: interval [" + a + ", " + b + "], precision " + precision);
                for (Worker.nThreads = 1; Worker.nThreads <= 8; Worker.nThreads += 1) {
                    dtime = Worker.CalculateIntegral(a, b, precision);
                    System.out.println("nThreads = " + Worker.nThreads + "\tIntegral: " + Worker.integral + "\tRunning time: " + dtime + "s");
                }

                System.out.println("#completed");
                System.exit(1);

            } else {

                try {
                    if (args[4].equals("debug")) {
                        Worker.debug = true;
                    }
                } catch (ArrayIndexOutOfBoundsException exc) {
                    // ...
                }

                System.err.println("Test for: interval [" + a + ", " + b + "], precision " + precision);
                if (nThreads != 0) {
                    Worker.nThreads = nThreads;
                    dtime = Worker.CalculateIntegral(a, b, precision);
                    System.out.println("nThreads = " + Worker.nThreads + "\tIntegral: " + Worker.integral + "\tRunning time: " + dtime + "s");
                    System.exit(0);
                } else {
                    for (Worker.nThreads = 1; Worker.nThreads <= 8; Worker.nThreads += 1) {
                        dtime = Worker.CalculateIntegral(a, b, precision);
                        System.out.println("nThreads = " + Worker.nThreads + "\tIntegral: " + Worker.integral + "\tRunning time: " + dtime + "s");
                    }
                }
            }

        } catch (Exception exc) {
            System.out.println(exc);
            exc.printStackTrace();
            System.exit(4);
        }

    }

}
