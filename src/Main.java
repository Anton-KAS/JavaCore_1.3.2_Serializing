import ru.netology.GameProgress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        String filePath = "D://Games/savegames/";
        GameProgress[] gameProgresses = new GameProgress[]{
                new GameProgress(3, 0, 1, 34.5),
                new GameProgress(15, 3, 20, 58.),
                new GameProgress(100, 20, 80, 99.9)
        };
        String[] savePaths = new String[gameProgresses.length];

        for (int n = 0; n < gameProgresses.length; n++) {
            String saveFilePath = filePath + "save" + n + ".dat";
            saveGame(saveFilePath, gameProgresses[n]);
            savePaths[n] = saveFilePath;
        }
        zipFiles(filePath + "zip.zip", savePaths);
        deleteSaves(savePaths);
    }

    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String filePath, String[] savePaths) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(filePath))) {
            for (String savePath : savePaths) {
                try (FileInputStream fis = new FileInputStream(savePath)) {
                    ZipEntry entry = new ZipEntry(savePath.substring(savePath.lastIndexOf("/") + 1));
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteSaves(String[] savePaths) {
        for (String savePath : savePaths) {
            File file = new File(savePath);
            file.delete();
        }
    }

}
