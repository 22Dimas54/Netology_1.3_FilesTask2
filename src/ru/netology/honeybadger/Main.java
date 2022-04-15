package ru.netology.honeybadger;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    private static String path1 = "G://Games/savegames/save1.dat";
    private static String path2 = "G://Games/savegames/save2.dat";
    private static String path3 = "G://Games/savegames/save3.dat";

    public static void main(String[] args) {
//1. Создать три экземпляра класса GameProgress.
        GameProgress gameProgress1 = new GameProgress(1, 2, 3, 4);
        GameProgress gameProgress2 = new GameProgress(5, 6, 7, 8);
        GameProgress gameProgress3 = new GameProgress(9, 10, 11, 12);
//2. Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи.
        saveGame(gameProgress1, path1);
        saveGame(gameProgress2, path2);
        saveGame(gameProgress3, path3);
//3. Созданные файлы сохранений из папки savegames запаковать в архив zip.
        zipFiles(path1+".zip",path1,"save1.dat");
        zipFiles(path2+".zip",path2,"save2.dat");
        zipFiles(path3+".zip",path3,"save3.dat");
//4. Удалить файлы сохранений, лежащие вне архива.
        deleteFileByPath(new File(path1));
        deleteFileByPath(new File(path2));
        deleteFileByPath(new File(path3));
    }

    private static void saveGame(GameProgress gameProgress, String path) {
        try (FileOutputStream gameProgressSerializable = new FileOutputStream(path);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(gameProgressSerializable)) {
            objectOutputStream.writeObject(gameProgress);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void zipFiles(String nameZip,String path,String nameFile) {
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

    private static void deleteFileByPath(File file){
        if(file.delete()){
            System.out.printf("File %s delete\n",file.getName());
        }else {
            System.out.printf("File %s not delete\n",file.getName());
        }
    }
}
