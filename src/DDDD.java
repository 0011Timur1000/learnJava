import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class DDDD {
    // Метод для получения списка всех файлов в директории и ее поддиректориях
    public static List<File> getAllFiles(File directory) {
        List<File> fileList = new ArrayList<>();
        File[] files = directory.listFiles(); // Получаем список файлов и директорий в указанной директории
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileList.add(file); // Если файл, то добавляем его в список
                } else if (file.isDirectory()) {
                    fileList.addAll(getAllFiles(file)); // Если директория, то вызываем рекурсивно этот же метод для поддиректории
                }
            }
        }
        return fileList;
    }

    public static List<File> getAllFiles2(File dir) {
        List<File> files = new ArrayList<>();
        Stack<File> stack = new Stack<>();
        stack.push(dir);
        while (!stack.isEmpty()) {
            File current = stack.pop();
            if (current.isFile()) {
                files.add(current);
            } else {
                File[] listFiles = current.listFiles();
                if (listFiles != null) {
                    for (File file : listFiles) {
                        stack.push(file);
                    }
                }
            }
        }
        return files;
    }

    public static void main(String[] args) {
        // Пример использования
        File directory = new File("C:/Users/212/IdeaProjects/MyPractice"); // Укажите путь к директории, для которой нужно получить список файлов
        List<File> fileList = getAllFiles(directory);
//        for (File file : fileList) {
//            System.out.println(file.getAbsolutePath());
//        }
        List<File> newFileList = fileList.stream()
                .filter(file -> file.getName().endsWith(".iml"))
                .collect(Collectors.toList());
        for (File file : newFileList) {
            System.out.println(file.getAbsolutePath());
        }
    }
}
