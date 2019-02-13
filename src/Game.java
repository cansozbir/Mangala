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

    public Game ( ) {   /// initialize
        for (int i = 0; i < 6; i++) {
            row1[i] = new Pit ( );
            row2[i] = new Pit ( );
        }
        this.sira = ilk ();
        System.out.println ("\t\t\tOYUN BASLADI" );
        if (sira)
            System.out.println ("Oyuna Player1 baslayacak..." );
        else
            System.out.println ("Oyuna Player2 baslayacak..." );
    }

    public boolean ilk ( ) {
        Random r = new Random ( );
        int x = r.nextInt (2);
        if ( x == 0 )
            return true;   //"Player1";
        else
            return false; //"Player2";
    }

    public void Hamle ( ) {
        Pit [] rowa;
        Pit [] rowb;
        Pit t;
        if (this.sira) {
            rowa = this.row1;
            rowb = this.row2;
            t = this.t1;
            System.out.print("\nPlayer1 " );
        }
        else {
            rowa = this.row2;
            rowb = this.row1;
            t = this.t2;
            System.out.print("\nPlayer2 " );
        }
        int index;
        do {
            System.out.print ("hamle giriniz : ");
            index = inp.nextInt ( ) - 1;
            if ( index < 0 || index >5 )
                System.out.println ("Gecersiz hamle.." );
        }while ( index < 0 || index >5 );
        int stonesInIndex = rowa[index].getStone ( );
        int iter = index;
        rowa[index].setStone (0);

        if (stonesInIndex==1) {
            rowa[iter].setStone (0);
            if (iter<5) {
                rowa[iter+1].setStone (rowa[iter+1].getStone () +1);
            }
            else if (iter==5) {
                t.setStone (t.getStone ( ) + 1);
            }
            this.sonhamle = iter;
            status (rowa,rowb,t);
        }
        else {
            while ( stonesInIndex > 0 ) {
                this.sonhamle = iter;
                if ( iter < 6 ) {
                    rowa[iter].setStone (rowa[iter].getStone ( ) + 1);
                    iter++;
                    stonesInIndex--;
                } else if ( iter == 6 ) {   // bu kisimda hazinesine biraktigi icin sira degisimi olmayacak
                    t.setStone (t.getStone ( ) + 1);
                    iter++;
                    stonesInIndex--;

                } else if ( iter != 13 ) {
                    rowb[iter - 7].setStone (rowb[iter - 7].getStone ( ) + 1);
                    iter++;
                    stonesInIndex--;
                } else {
                    iter = 0;
                }
            }
            status (rowa,rowb,t);
        }
    }

    public void status (Pit [] rowa , Pit[] rowb , Pit t) {

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
            if (!kazanan ())
                this.sira = !this.sira;

        }

    }


    public boolean kazanan () {
        Pit [] row ;
        boolean bool = true;
        if (this.sira)
            row = row1;
        else
            row = row2;
        for (Pit each : row) {
            if (each.getStone () != 0)
                bool=false;
        }

        if (bool && this.sira) {
            this.winner = "Player1";
            this.playable = false;
            return true;
        }
        else if (bool) {
            this.winner = "Player2";
            this.playable = false;
            return true;
        }
        return false;
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
        while ( !kazanan() ) {
            tahtayiYaz ( );
            Hamle ( );

        }
        tahtayiYaz ( );
        System.out.println (winner + " KAZANDI \\o/" );
    }

}