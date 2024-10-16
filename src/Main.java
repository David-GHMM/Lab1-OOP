import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        Pattern pattern = Pattern.compile("[а-яa-z0-9 ё]");
        Matcher matcher;

        System.out.println("Данная программа находит все пары слов в тексте, где одно является обращением другого.");
        System.out.println("[INFO] Введение текста вручную (напишите цифру 1); Считывание текста из файла (напишите цифру 2).");

        String choice = in.nextLine();
        String line_in;
        String line = "";

        switch (choice)
        {
            case "1":
                System.out.println("Введите текст: ");
                line_in = in.nextLine().toLowerCase();

                matcher = pattern.matcher(line_in);

                while (matcher.find()) {
                    line += line_in.substring(matcher.start(), matcher.end());
                }
                break;
            case "2":
                try {
                    System.out.println("\nВведите абсолютный путь до файла: ");
                    String file = in.nextLine();
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String file_line;
                    String file_text = "";

                    while ((file_line = reader.readLine()) != null) {
                        file_text += file_line.toLowerCase() + " ";
                    }

                    matcher = pattern.matcher(file_text);

                    while (matcher.find()) {
                        line += file_text.substring(matcher.start(), matcher.end());
                    }

                    break;
                }
                catch (Exception e)
                {
                    System.out.println("[INFO] Введенный путь некорректен, программа завершается...");
                    System.exit(0);
                }
            default:
                System.out.println("[INFO] Произошла ошибка, программа завершается");
                System.exit(0);
        }

        String[] linearr = line.split(" ");
        String[] linearr_rev = new StringBuilder(line).reverse().toString().split(" ");

        int ni = linearr.length;
        int nj = linearr_rev.length;
        boolean check;

        List<List<String>> pair = new ArrayList<>();

        for (int i = 0; i < ni; i++)
        {
            for (int j = nj-i-2; j >= nj-ni; j--)
            {
                if (linearr[i].equals(linearr_rev[j]))
                {
                    check = true;
                    for (List<String> strings: pair)
                    {
                        if (strings.getFirst().equals(linearr[i]))
                        {
                            check = false;
                            break;
                        }
                    }
                    if (check) pair.add(Arrays.asList(linearr[i], linearr[nj-j-1]));
                }
            }
        }

        if (pair.isEmpty())
        {
            System.out.println("\nПары не были найдены...");
        }
        else
        {
            System.out.println("\nНайденные пары: ");
            for (List<String> strings : pair) {
                if (strings.getLast().isEmpty() || strings.getFirst().isEmpty()) continue;

                System.out.println(strings.getFirst() + " - " + strings.getLast());
            }
        }
    }
}