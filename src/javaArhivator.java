import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class javaArhivator {

        // Метод для архивирования файла
        public static void archiveFile(String sourceFilePath, String archiveFilePath) throws IOException {
            byte[] buffer = new byte[1024]; // Буфер для чтения и записи данных

            try (FileInputStream fis = new FileInputStream(sourceFilePath); // Создание потока для чтения исходного файла
                 FileOutputStream fos = new FileOutputStream(archiveFilePath); // Создание потока для записи архивного файла
                 ZipOutputStream zos = new ZipOutputStream(fos)) { // Создание объекта ZipOutputStream для записи в архив

                ZipEntry zipEntry = new ZipEntry(new File(sourceFilePath).getName()); // Создание ZipEntry с именем исходного файла
                zos.putNextEntry(zipEntry); // Добавление ZipEntry в архив

                int length;
                while ((length = fis.read(buffer)) > 0) { // Чтение данных из исходного файла и запись в архив
                    zos.write(buffer, 0, length);
                }

                zos.closeEntry(); // Завершение записи ZipEntry
            }
        }

    // Метод для архивирования нескольких файлов
    public static void archiveFiles(String[] sourceFilePaths, String archiveFilePath) throws IOException {
        byte[] buffer = new byte[1024]; // Буфер для чтения и записи данных

        try (FileOutputStream fos = new FileOutputStream(archiveFilePath); // Создание потока для записи архивного файла
             ZipOutputStream zos = new ZipOutputStream(fos)) { // Создание объекта ZipOutputStream для записи в архив

            for (String sourceFilePath : sourceFilePaths) {
                File file = new File(sourceFilePath);
                if (!file.exists()) { // Проверка наличия файла
                    System.err.println("Файл " + sourceFilePath + " не существует. Пропуск...");
                    continue;
                }

                FileInputStream fis = new FileInputStream(file); // Создание потока для чтения исходного файла
                ZipEntry zipEntry = new ZipEntry(file.getName()); // Создание ZipEntry с именем исходного файла
                zos.putNextEntry(zipEntry); // Добавление ZipEntry в архив

                int length;
                while ((length = fis.read(buffer)) > 0) { // Чтение данных из исходного файла и запись в архив
                    zos.write(buffer, 0, length);
                }

                zos.closeEntry(); // Завершение записи ZipEntry
                fis.close(); // Закрытие потока чтения исходного файла
            }
        }
    }


    // Метод для архивирования нескольких файлов и директорий
    public static void archiveFilesAndDirectories(String[] sourcePaths, String archiveFilePath) throws IOException {
        byte[] buffer = new byte[1024]; // Буфер для чтения и записи данных

        try (FileOutputStream fos = new FileOutputStream(archiveFilePath); // Создание потока для записи архивного файла
             ZipOutputStream zos = new ZipOutputStream(fos)) { // Создание объекта ZipOutputStream для записи в архив

            for (String sourcePath : sourcePaths) {
                File sourceFile = new File(sourcePath);

                if (!sourceFile.exists()) { // Проверка наличия файла или директории
                    System.err.println("Путь " + sourcePath + " не существует. Пропуск...");
                    continue;
                }

                if (sourceFile.isFile()) { // Если путь указывает на файл
                    FileInputStream fis = new FileInputStream(sourceFile); // Создание потока для чтения исходного файла
                    ZipEntry zipEntry = new ZipEntry(sourceFile.getName()); // Создание ZipEntry с именем исходного файла
                    zos.putNextEntry(zipEntry); // Добавление ZipEntry в архив

                    int length;
                    while ((length = fis.read(buffer)) > 0) { // Чтение данных из исходного файла и запись в архив
                        zos.write(buffer, 0, length);
                    }

                    zos.closeEntry(); // Завершение записи ZipEntry
                    fis.close(); // Закрытие потока чтения исходного файла
                } else if (sourceFile.isDirectory()) { // Если путь указывает на директорию
                    archiveDirectory(sourceFile, sourceFile.getName(), zos); // Рекурсивное архивирование директории
                }
            }
        }
    }

    // Метод для архивирования содержимого директории
    public static void archiveDirectory(File directory, String baseName, ZipOutputStream zos) throws IOException {
        byte[] buffer = new byte[1024]; // Буфер для чтения и записи данных

        File[] files = directory.listFiles(); // Получение списка файлов и поддиректорий в директории

        for (File file : files) {
            if (file.isFile()) { // Если это файл
                FileInputStream fis = new FileInputStream(file); // Создание потока для чтения файла
                ZipEntry zipEntry = new ZipEntry(baseName + "/" + file.getName()); // Создание ZipEntry с именем файла, относительно базового имени
                zos.putNextEntry(zipEntry); // Добавление ZipEntry в архив

                int length;
                while ((length = fis.read(buffer)) > 0) { // Чтение данных из файла и запись в архив
                    zos.write(buffer, 0, length);
                }

                zos.closeEntry(); // Завершение записи ZipEntry
            }
        }
    }


    public static void main(String[] args) {
        // Пример использования archiveFiles
        String[] sourceFilePaths = { "/path/to/file1.txt", "/path/to/file2.txt", "/path/to/file3.txt" }; // Укажите пути к исходным файлам
        String archiveFilePath = "/path/to/files.zip"; // Укажите путь для архивированного файла
        try {
            archiveFiles(sourceFilePaths, archiveFilePath);
            System.out.println("Файлы успешно заархивированы.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//        public static void main(String[] args) {
//            // Пример  archiveFile
//            String sourceFilePath = "/path/to/file.txt"; // Укажите путь к исходному файлу
//            String archiveFilePath = "/path/to/file.zip"; // Укажите путь для архивированного файла
//            try {
//                archiveFile(sourceFilePath, archiveFilePath);
//                System.out.println("Файл успешно заархивирован.");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
}
