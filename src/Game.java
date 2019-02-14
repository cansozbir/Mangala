import java.util.Random;
import java.util.Scanner;

public class Game {
    Scanner inp = new Scanner (System.in);
    Pit[] row1 = new Pit[6];
    Pit[] row2 = new Pit[6];
    int sonhamle;
    Pit t1 = new Pit (0);
    Pit t2 = new Pit (0);
    boolean playable = true;
    String winner ="non";
    boolean sira ;

    public boolean ilk ( ) {
        Random r = new Random ( );
        int x = r.nextInt (2);
        if ( x == 0 )
            return true;   //"Player1";
        else
            return false; //"Player2";
    }

    public String siraKimde (boolean sira) {
        if (sira)
            return "Player1";
        else
            return "Player2";
    }

    public Game ( ) {   /// initialize
        for (int i = 0; i < 6; i++) {
            row1[i] = new Pit ( );
            row2[i] = new Pit ( );
        }
        this.sira = ilk ();
        System.out.println ("\t\t\tOYUN BASLADI" );

        System.out.println ("Oyuna "+ siraKimde(sira)+ " baslayacak..." );
    }

    public void Hamle ( ) {
        Pit [] rowa; // o an sira sahibi oyuncunun kendi kuyularini temsil ediyor
        Pit [] rowb; // o an sira sahibi oyuncunun rakip kuyularini temsil ediyor
        Pit t; //       o an sira sahibi oyuncunun hazine kuyusunu temsil ediyor
        Pit t_enemy ;// o anda rakip oyuncunun hazine kuyusunu temsil ediyor
        if (this.sira) {
            rowa = this.row1;
            rowb = this.row2;
            t = this.t1;
            t_enemy = this.t2;
        }
        else {
            rowa = this.row2;
            rowb = this.row1;
            t = this.t2;
            t_enemy = this.t1;
        }
        System.out.println(siraKimde(sira));
        int index;
        do {
            System.out.print ("hamle giriniz : ");
            index = inp.nextInt ( ) - 1;
            if (( index < 0 || index >5 ) || rowa[index].getStone()==0 )
                System.out.println ("Gecersiz hamle.." );
        }while (( index < 0 || index >5 ) || rowa[index].getStone()==0 );
        int stonesInIndex = rowa[index].getStone ( );
        int iter = index;
        rowa[index].setStone (0);

        if (stonesInIndex==1) {
            rowa[iter].setStone (0);
            if (iter<5) {
                rowa[iter+1].setStone (rowa[iter+1].getStone () +1);
            }
            else { // if iter ==  5
                t.setStone (t.getStone ( ) + 1);
            }
            this.sonhamle = iter;
            status (rowa,rowb,t,t_enemy);
        }
        else {
            while ( stonesInIndex > 0 ) {
                this.sonhamle = iter;
                if ( iter < 6 ) {
                    rowa[iter].setStone (rowa[iter].getStone ( ) + 1);
                    iter++;
                    stonesInIndex--;
                } else if ( iter == 6 ) {
                    t.setStone (t.getStone ( ) + 1);
                    iter++;
                    stonesInIndex--;

                } else if ( iter != 13 ) {
                    rowb[iter - 7].setStone (rowb[iter - 7].getStone ( ) + 1);
                    iter++;
                    stonesInIndex--;
                } else {
                    iter = iter-13;
                }
            }
            status (rowa,rowb,t,t_enemy);
        }
    }

    public void status (Pit [] rowa , Pit[] rowb , Pit t, Pit t_enemy) {

        if (this.sonhamle != 6) {
            if (this.sonhamle < 6) {
                if (rowa[this.sonhamle].getStone () == 1 && rowb[5-this.sonhamle].getStone () > 0 ) {
                    t.setStone (t.getStone ()+rowa[this.sonhamle].getStone () + rowb[5-this.sonhamle].getStone ());
                    rowa[this.sonhamle].setStone (0);
                    rowb[5-this.sonhamle].setStone (0);
                }

            }
            else {
                if (rowb[this.sonhamle-7].getStone ()%2 == 0) {
                    t.setStone (t.getStone () + rowb[this.sonhamle-7].getStone ());
                    rowb[this.sonhamle-7].setStone (0);
                }

            }
            if (!kazanan (rowa, t, t_enemy ))
                this.sira = !this.sira;

        }

    }

    public  boolean kazanan (Pit [] row , Pit my_treasury , Pit enemy_treasury) {
        boolean isGameFinished = true ;
        for (Pit each : row) {
            if (each.getStone () != 0)
                isGameFinished=false;
        }
        if (isGameFinished) {
            this.winner = siraKimde(sira);
            my_treasury.setStone(my_treasury.getStone() + enemy_treasury.getStone());
            enemy_treasury.setStone(0);
            playable = false;
        }

        return isGameFinished;
    }

    public void tahtayiYaz () {

        System.out.print ("      " );
        for (int i=5 ; i>=0 ; i--) {
            System.out.print ("["+ row2[i].getStone ()+"] " );
        }

        System.out.println ("\n[" +t2.getStone () + "]\t\t\t\t\t\t\t\t[" +t1.getStone () + "]");

        System.out.print ("      " );

        for (Pit each : row1) {
            System.out.print ("["+ each.getStone ()+"] " );
        }
        System.out.println ("\n---------------------------------------------" );
    }

    public void gameLoop () {
        while ( playable ) {
            tahtayiYaz ( );
            Hamle ( );

        }

        tahtayiYaz ( );
        System.out.println (winner + " KAZANDI \\o/" );
    }

}