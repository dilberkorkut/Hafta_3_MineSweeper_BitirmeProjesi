import java.util.Random;
import java.util.Scanner;

public class MineSweeper { // Degerlendirme 5: Proje MineSweeper sinifi icerisinde.

    int rowNumber; //oyuncunun belirledigi satir sayisi
    int colNumber; //oyuncunun belirledigi sutun sayisi
    int mineNumber; // oyunun basinda hesaplanan mayin sayisi
    boolean[][] mineLocations; // Hucrede mayin olup olmadiginin kontrolu.varsayilan false/mayin yok
    String[][] mineBoard; // Mayinlarin yerlestigi board
    String[][] playerBoard; // Oyuncunun oyunu oynarken gordugu board
    boolean isGameInProgress;// Oyunun durumunu takip

    MineSweeper(int rowNumber, int colNumber) {
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
        this.mineLocations = new boolean[rowNumber][colNumber];
        this.mineBoard = new String[rowNumber][colNumber];
        this.playerBoard = new String[rowNumber][colNumber];
        this.isGameInProgress = false;
    }

    // Degerlendirme 6: Sınıf içerisinde ilgili metotlar
    public void run() {
        setUpMines();
        System.out.println("Mayinlarin konumu");
        printBoard(mineBoard);
        System.out.println("=================================");
        System.out.println("Mayin Tarlasi Oyununa Hosgeldiniz");
        printBoard(playerBoard);
        System.out.println("=================================");

        while (!isGameInProgress) {
            startGame(); // Oyuncunun hamlelerini kontrol eder ve oyunun durumunu yönetir.
        }
    }

    public void setUpMines() { // oyun hazirlik metodu. mayin ve oyuncu boardu hazirlanir.
        //mayinlarin gorundugu board hazirlanir.
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < colNumber; j++) {
                mineBoard[i][j] = "-";
            }
        }
        // rastgele mayinlar yerlestirilir.
        int mineCount = 0;
        //Değerlendirme 8 : Diziye boyutunun 4 te 1 i kadar mayın aşağıdaki metod ile yerleştiriliyor.
        mineNumber = rowNumber * colNumber / 4;
        Random random = new Random();

        while (mineCount < mineNumber) { //belirlenen mayın sayısına ulaşana kadar devam eden bir döngü kullaniyoruz.
            int randRow = random.nextInt(rowNumber);
            int randCol = random.nextInt(colNumber);

            /*Degerlendirme 10: Kullanıcının seçtiği noktanin dizinin sınırları içerisinde olup
            olmadığı isValidCell metodu ile kontrol ediliyor
            ve bu hucreye daha once mayin yerlesmemisse mayin yerlestiriliyor.*/
            if (isValidCell(randRow, randCol) && (!mineLocations[randRow][randCol])) {
                mineLocations[randRow][randCol] = true; //mayin yerleştirildiğini işaretlemek için ilgili hucreyi true olarak ayarliyoruz.
                mineBoard[randRow][randCol] = "*";
                mineCount++;
            }
        }
        // oyuncunun oynayacagi boardu hazirlanir.
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < colNumber; j++) {
                playerBoard[i][j] = "-";
            }
        }
    }
    //10:hucre sinirlari icerisinde olup olmadigini kontrol eden metod.
    public boolean isValidCell(int row, int col) {
        return row >= 0 && row < rowNumber && col >= 0 && col < colNumber; //Gecerli bir oyun hucresi ise true, degilse false.
    }

    public void printBoard(String[][] field) { //Matrisi yazdirma, board gosterme metodu.
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }
    // Degerlendirme 15: Kullanıcının oyunu kaybetme ya da kazanma durumunda uygun mesajlar bu metotta
    public void startGame() {
        int selectedRow, selectedCol;
        boolean isValidMove = false; // girilen satir ve sutunun gecerli olup olmadigi kontrol ediliyor.
        Scanner input = new Scanner(System.in);

        //Degerlendirme 9: Kullanıcıdan işaretlemek istediği satır ve sütun bilgisi asagidadir.
        while (!isValidMove) { //Verilen satır ve sütun koordinatlarının geçerli bir oyun hücresini temsil edip etmediğini kontrol eder.
            System.out.print("Satir numarasi giriniz: ");
            selectedRow = input.nextInt();
            System.out.print("Sutun numarasi giriniz: ");
            selectedCol = input.nextInt();

            if (isValidCell(selectedRow, selectedCol)) {
                isValidMove = true;
                //Degerlendirme 13: Kullanıcı mayına bastığında oyunu kaybetme durumu
                if (isMine(selectedRow, selectedCol)) { // ve mayin olup olmadigini kontrol eder.
                    isGameInProgress = true; //mayina basti
                    System.out.println("Game Over!");
                    System.out.println("=============================");

                } else {
                    //Degerlendirme 11: Kullanıcı her hamle yaptığında oyun alanı güncelleniyor
                    openCell(selectedRow, selectedCol);
                    printBoard(playerBoard);
                    if (isGameWon()) {  //Degerlendirme 14: Tüm noktalar mayınsız bir şekilde seçilirse oyunu kazanmanın kontrolü
                        isGameInProgress = true;
                        System.out.println("Tebrikler! Oyunu Kazandiniz ");
                    }
                }
            } else {
                System.out.println("Hatali bir giris yaptiniz! Lutfen gecerli satir ve sutun numarasi giriniz.");
            }
        }
    }

    public boolean isMine(int selectedRow, int selectedCol) { // satir ve sutunda mayin olup olmadigini kontrol eder
        return mineLocations[selectedRow][selectedCol];
    }
    //Degerlendirme 14: Tüm noktalar mayınsız bir şekilde seçilirse oyunu kazanmanın kontrolunu yapan metod
    public boolean isGameWon() {
        //tum acilmamis hucreleri kontrol et
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < colNumber; j++) {
                if (playerBoard[i][j].equals("-") && !isMine(i, j)) { //playerboard'ta kapali hucre varsa ve isMine false donerse
                    return false; // hala acilmamis hucre vardir, oyun devam ediyor.
                }
            }
        }
        return true; // tum hucreler mayina yakalanmadan acmissa oyun kazanildi.
    }

    public void openCell(int selectedRow, int selectedCol) {//Secilen hucreyi ac ve etrafindaki mayinlari goster metodu
        if (!playerBoard[selectedRow][selectedCol].equals("-")) {
            return; // hucre zaten acilmissa islem yapma
        }

        int surroundingMineCount = countSurroundingMines(selectedRow, selectedCol);
        playerBoard[selectedRow][selectedCol] = String.valueOf(surroundingMineCount);// secilen hucrenin etrafindaki mayin sayisini boarda yazar.
    }
    //Degerlendirme 12: Bu metot icerisinde girilen noktada mayın yoksa etrafındaki mayın sayısı veya 0 değeri yerine yazılir.
    public int countSurroundingMines(int selectedRow, int selectedCol) {
        int surroundingMineCount = 0;
        // gezecegi hucre sayisi kendisinden onceki ve sonraki kadar olacak.
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // etrafindaki 8 hucre gezdikten sonra yeni satir ve sutunlara gececek.
                int newRow = selectedRow + i;
                int newCol = selectedCol + j;

                if (i != 0 || j != 0) {
                    if (isValidCell(newRow, newCol) && isMine(newRow, newCol)) {
                        surroundingMineCount++;
        /*etrafindaki mayinlari kontrol ederken kendisini tekrar kontrol etmeyecek.
        mayinlari sayip yazacak. mayina bastiysa game over!*/
                    }
                }
            }
        }
        return surroundingMineCount;

    }
}





