/*
   Worker.java
   @Author R. Juozapavicius
   3. Uzduotis. Apibreztinio integralo skaiciavimas

   Darbiniu giju klase.
*/

package prog3;

import java.util.Stack;
import java.io.IOException;

public class Worker extends Thread {

    //-----------------------------------------
    // Global parameters
    static double integral = 0;                              // Aproximated integral
    static int nThreads = 0;                                 // Number working threads
    static LockObject lockObject = new LockObject();         // Lock for synchronization
    static Stack<Interval> stack = new Stack<Interval>();    // Job stack
    static boolean debug = false;
    //-----------------------------------------

    public Worker() { }

    //-----------------------------------------
    // Thread.run()
    // Parallel Adaptive Simpson's Quadrature.
    public void run() {

        double c1, h1, d1, e1, fa1, fb1, fc1, fd1, fe1, Sleft1, Sright1, S21;
        double c2, h2, d2, e2, fa2, fb2, fc2, fd2, fe2, Sleft2, Sright2, S22;
        Interval interval1 = null;
        Interval interval2 = null;

        while (true) {

            synchronized(lockObject) {
                debugInfo(0);
                if ((!stack.isEmpty()) && (interval1 = stack.pop()) != null) { } else break;
                if ((!stack.isEmpty()) && (interval2 = stack.pop()) != null) { }
                debugInfo(1);

            }

            c1 = (double) (interval1.a + interval1.b)/2;
            h1 = interval1.b - interval1.a;
            d1 = (double) (interval1.a + c1)/2;
            e1 = (double) (c1 + interval1.b)/2;
            fa1 = f(interval1.a);
            fb1 = f(interval1.b);
            fc1 = f(c1);
            fd1 = f(d1);
            fe1 = f(e1);
            Sleft1 = ((double) (h1/12))*(fa1 + 4*fd1 + fc1);
            Sright1 = ((double) (h1/12))*(fc1 + 4*fe1 + fb1);
            S21 = Sleft1 + Sright1;
            S22 = 0;
            c2 = 0;
            Sleft2 = 0;
            Sright2 = 0;
            if (interval2 != null) {
                c2 = (double) (interval2.a + interval2.b)/2;
                h2 = interval2.b - interval2.a;
                d2 = (double) (interval2.a + c2)/2;
                e2 = (double) (c2 + interval2.b)/2;
                fa2 = f(interval2.a);
                fb2 = f(interval2.b);
                fc2 = f(c2);
                fd2 = f(d2);
                fe2 = f(e2);
                Sleft2 = ((double) (h2/12))*(fa2 + 4*fd2 + fc2);
                Sright2 = ((double) (h2/12))*(fc2 + 4*fe2 + fb2);
                S22 = Sleft2 + Sright2;
            }
            synchronized(lockObject) {
                    debugInfo(2, interval1.a, interval1.b);
                    if (Math.abs(S21 - interval1.S) <= 15*interval1.epsilon) {
                        integral += S21;
                        debugInfo(3);
                    } else if ((double) interval1.epsilon/2 != 0) {
                        debugInfo(4);
                        stack.push(new Interval(interval1.a, c1, (double) interval1.epsilon/2, Sleft1));
                        stack.push(new Interval(c1, interval1.b, (double) interval1.epsilon/2, Sright1));
                        debugInfo(5);
                    }
                    if (interval2 != null) {
                        debugInfo(2, interval2.a, interval2.b);
                        if (Math.abs(S22 - interval2.S) <= 15*interval2.epsilon) {
                            integral += S22;
                            debugInfo(3);
                        } else if ((double) interval2.epsilon/2 != 0) {
                            debugInfo(4);
                            stack.push(new Interval(interval2.a, c2, (double) interval2.epsilon/2, Sleft2));
                            stack.push(new Interval(c2, interval2.b, (double) interval2.epsilon/2, Sright2));
                            debugInfo(5);
                        }
                    }

            }

        }

    }

    public void debugInfo(int i) {
        if (debug) {
            System.out.print("Press Enter");
            try {
                System.in.read();
            } catch (IOException exc) {

            }
            switch (i) {
                case 0: {
                    System.out.println("Thread " + this + " is checking stack.");
                    break;
                }
                case 1: {
                    System.out.println("Thread " + this + " got work unit from stack.");
                    break;
                }
                case 2: {
                    System.out.println("Thread " + this + " is checking integral part's precision.");
                    break;
                }
                case 3: {
                    System.out.println("Thread " + this + " established that precision is appropriate.");
                    break;
                }
                case 4: {
                    System.out.println("Thread " + this + " established that precision is not appropriate.");
                    break;
                }
                case 5: {
                    System.out.println("Thread " + this + " pushed two work units to stack.");
                    break;
                }
            }
        }

    }

    public void debugInfo(int i, double a, double b) {
        if (debug) {
            System.out.print("Press Enter");
            try {
                System.in.read();
            } catch (IOException exc) {

            }
            switch (i) {
                case 0: {
                    System.out.println("Thread " + this + " is checking stack.");
                    break;
                }
                case 1: {
                    System.out.println("Thread " + this + " got work unit from stack.");
                    break;
                }
                case 2: {
                    System.out.println("Thread " + this + " is checking integral's (that is in [" + a + ", " + b + "] interval) precision.");
                    break;
                }
                case 3: {
                    System.out.println("Thread " + this + " established that precision is appropriate.");
                    break;
                }
                case 4: {
                    System.out.println("Thread " + this + " established that precision is not appropriate.");
                    break;
                }
                case 5: {
                    System.out.println("Thread " + this + " pushed two work units to stack.");
                    break;
                }
            }
        }

    }

    //-----------------------------------------
    // Launchs threads and calculates definite integral
    // Returns working time
    static double CalculateIntegral(double a, double b, double precision) throws Exception {

        long time0 = System.currentTimeMillis();

        double c, h, fa, fb, fc, S;

        c = (double) (a + b)/2;
        h = b - a;
        fa = f(a);
        fb = f(b);
        fc = f(c);
        S = ((double) (h/6))*(fa + 4*fc + fb);

        integral = 0;
        stack.push(new Interval(a, b, precision, S));

        // Create and start threads
        Worker[] aThreads = new Worker[nThreads];

        for (int i = 0; i < nThreads; i++) {
            (aThreads[i] = new Worker()).start();
        }

        for (int i = 0; i < nThreads; i++) {
                aThreads[i].join();
        }

        long time1 = System.currentTimeMillis();
        double dtime = (time1-time0)/1000.;
        return dtime;
    }

    static double f(double x) {

        return Math.sin(x);

    }

    //-----------------------------------------

}
