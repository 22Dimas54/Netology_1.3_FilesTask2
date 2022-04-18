package ru.netology.honeybadger;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    private static List<String> arrayPath = Arrays.asList("G://Games/savegames/save1.dat",
            "G://Games/savegames/save2.dat",
            "G://Games/savegames/save3.dat");

    public static void main(String[] args) {
        //1. Создать три экземпляра класса GameProgress.
        List<GameProgress> arrayGameProgress = Arrays.asList(new GameProgress(1, 2, 3, 4),
                new GameProgress(5, 6, 7, 8),
                new GameProgress(9, 10, 11, 12));

        for (int i = 0; i < arrayGameProgress.size(); i++) {
            //2. Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи.
            saveGame(arrayGameProgress.get(i), arrayPath.get(i));
            //3. Созданные файлы сохранений из папки savegames запаковать в архив zip.
            zipFiles(arrayPath.get(i) + ".zip", arrayPath.get(i), "save" + (i + 1) + ".dat");
            //4. Удалить файлы сохранений, лежащие вне архива.
            deleteFileByPath(new File(arrayPath.get(i)));
        }
    }

    private static void saveGame(GameProgress gameProgress, String path) {
        try (FileOutputStream gameProgressSerializable = new FileOutputStream(path);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(gameProgressSerializable)) {
            objectOutputStream.writeObject(gameProgress);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void zipFiles(String nameZip, String path, String nameFile) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(nameZip));
             FileInputStream fis = new FileInputStream(path)) {
            ZipEntry entry = new ZipEntry(nameFile);
            zipOutputStream.putNextEntry(entry);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            zipOutputStream.write(bytes);
            zipOutputStream.closeEntry();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void deleteFileByPath(File file) {
        if (file.delete()) {
            System.out.printf("File %s delete\n", file.getName());
        } else {
            System.out.printf("File %s not delete\n", file.getName());
        }
    }
}
