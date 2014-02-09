/*
 * Main.java
 * @author Rokas Juozapavicius
 * 2. Uzduotis. Semaforas kaip resursu skaitliukas.
 */

package prog2;

/* 
   Realizuoti paskirtojo varianto gijų sinchronizacijos primityvą:
   1.Semaforas kaip resursų skaitliukas.
   Inicializuojamas turimų resursų skaičiumi.
   Operacijos:
      request()         - laukia, kol resursas atsilaisvins,
      release()         - grąžinamas resursas,
      numberAvailable() - grąžinamas laisvų resursų skaičius
                                   (nesiblokuojant).

   ***************************************************************
   Modeline situacija:
   Tegu semaforas yra pakartotinio naudojimo resursu skaitliukas.
   Modeliuojama sistema, kurioje gijos gali prasyti resursu, o 
   baigus darba su resursu, ji grazina atgal i sistema.
   Jei sistemoje nera laisvu resursu, tuo metu resurso prasanti
   gija pereina i laukimo busena (kvieciamas wait() metodas), 
   o kitos gijos uzsiblokuoja. Gijai baigus darba resursas
   grazinamas (kvieciamas metodas release()), ir juo naudotis
   gali kitos gijos, kurios lauke resursu (jei tokiu yra).
   
*/

public class Main {

    public static void main(String[] args) {

       System.out.println("Programa pradeda darba.");
       ResourceTaker.pradeti();
       System.out.println("Programa baigia darba.");

    }

}
