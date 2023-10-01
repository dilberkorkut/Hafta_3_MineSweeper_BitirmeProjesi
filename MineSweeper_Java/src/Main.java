import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int rowNumber, colNumber;
       // Degerlendirme 7 : Dizi (matris) boyutu kullanıcı tarafından belirlendi.
        System.out.print("Lutfen satir sayisini giriniz: ");
        rowNumber = input.nextInt();
        System.out.print("Lutfen sutun sayisini giriniz: ");
        colNumber = input.nextInt();
        System.out.println("=============================");

        MineSweeper game = new MineSweeper(rowNumber,colNumber);
        game.run();


    }
}