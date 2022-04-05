import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main{

    private static final String LINEBREACK = "\n";
    private static StringBuilder log = new StringBuilder();
    public static void main (String[] args){

        List<String> namesDir = Arrays.asList("Games\\src",
                "Games\\res",
                "Games\\savegames",
                "Games\\temp",
                "Games\\src\\main",
                "Games\\src\\test");

        for (String nameDir : namesDir) {
            newDir(nameDir);
        }

        List<String> namesFile = Arrays.asList(
                "Games\\src\\main//Main.java",
                "Games\\src\\main//Utils.java");

        for (String nameFile : namesFile) {
            newFile(nameFile);
        }

        List<String> namesDir2 = Arrays.asList(
                "Games\\res\\drawables",
                "Games\\res\\vectors",
                "Games\\res\\icons");

        for (String nameDir : namesDir2) {
            newDir(nameDir);
        }

        String logString = String.valueOf(log);
        BufferedWriter bw = null;
        FileWriter tempTxt;
        try {
            bw = new BufferedWriter(tempTxt = new FileWriter("Games\\temp//temp.txt"));
            bw.write(logString);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Задача 2

        GameProgress[] gameProgress = {
                new GameProgress(100, 15, 2, 2.2),
                new GameProgress(58, 55, 4, 3.3),
                new GameProgress(760, 100, 80, 150)
        };

        String[] saves = {"D:\\DZ java core\\DZ 3.1 installation\\Games\\savegames//save1.dat",
                "D:\\DZ java core\\DZ 3.1 installation\\Games\\savegames//save2.dat",
                "D:\\DZ java core\\DZ 3.1 installation\\Games\\savegames//save3.dat"};

        for (int i = 0; i < gameProgress.length; i++) {
            saveGames(saves[i], gameProgress[i]);
        }

        try (ZipOutputStream zout = new ZipOutputStream( new FileOutputStream(
                "D:\\DZ java core\\DZ 3.1 installation\\Games\\savegames//save.zip"));
             FileInputStream fis = new FileInputStream(saves[0]);
             FileInputStream fis1 = new FileInputStream(saves[1]);
             FileInputStream fis2 = new FileInputStream(saves[2])) {
            List<FileInputStream> list = Arrays.asList(fis, fis1, fis2);
            for (int i =0 ; i < list.size(); i++) {
                ZipEntry entry1 = new ZipEntry(saves[i]);
//            ZipEntry entry2 = new ZipEntry("D:\\DZ java core\\DZ 3.1 installation\\Games\\savegames//save2.dat");
                zout.putNextEntry(entry1);
//            zout.putNextEntry(entry2);
                byte[] buffer = new byte[list.get(i).available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
//                Files.delete(Paths.get(saves[i]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            for (String adres: saves) {
                Files.delete(Paths.get(adres));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static StringBuilder newDir(String nameDir) {
        File newDir = new File(nameDir);
        if (newDir.mkdir()) {
            return log.append(String.format("Папка %s создана в каталоге %s %s",
                    newDir.getName(), newDir.getPath(), LINEBREACK));
        } else {
            return log.append(String.format("Папка %s НЕ создана!%s", newDir.getName(), LINEBREACK));
        }

    }

    static StringBuilder newFile(String nameFile){
        File myFile = new File(nameFile);
        try {
            if (myFile.createNewFile()) {
                return log.append(String.format("Файл %s создан%s", myFile.getName(), LINEBREACK));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return log.append(ex.getMessage()).append(LINEBREACK);
        }
        return log;
    }

    static  void saveGames(String name, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(name);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
