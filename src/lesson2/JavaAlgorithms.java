package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;
import lesson1.Sorts;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     * <p>
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     * <p>
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     * <p>
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     * Сложность = O(n), Ресурсоемкость = O(n)
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) throws IOException {

        ArrayList<Integer> list = new ArrayList<>();
        Pair<Integer, Integer> res;

        for (String line : Files.readAllLines(Paths.get(inputName)))
            list.add(Integer.parseInt(line));

        int[] delta = new int[list.size() - 1];
        for (int i = 0; i < list.size() - 2; i++) delta[i] = list.get(i + 1) - list.get(i);

        res = maxSubArray(delta, 0, delta.length - 1);

        return res;
    }


    private static Pair<Integer, Integer> maxSubArray(int[] array, int first, int last) {
        int sum = 0;
        int maxSum = 0;
        int firstInd = 0;
        int lastInd = 0;
        int minus = -1;
        for (int i = first; i <= last; i++) {
            sum += array[i];
            if (sum > maxSum) {
                maxSum = sum;
                firstInd = minus + 1;
                lastInd = i;
            }
            if (sum < 0) {
                sum = 0;
                minus = i;
            }
        }
        return new Pair<>(firstInd + 1, lastInd + 2);
    }



    /**
     * Задача Иосифа Флафия.
     * Простая
     * <p>
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 5
     * <p>
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 х
     * <p>
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 х 3
     * 8   4
     * 7 6 Х
     * <p>
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     * <p>
     * 1 Х 3
     * х   4
     * 7 6 Х
     * <p>
     * 1 Х 3
     * Х   4
     * х 6 Х
     * <p>
     * х Х 3
     * Х   4
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   х
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   Х
     * Х х Х
     * <p>
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     * <p>
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     * Сложность = O(n*m), Ресурсоемкость = O(m), где n - длина first, m - длина second
     */
    static public String longestCommonSubstring(String first, String second) {
       /*попытка решить задачу без запоминания матрицы
         для каждого символа строки first прохожусь по символам строки second,
         заполняю массив mas2 совпадениями с second.
         Чтобы оценить длину совпадения, при прихождении по каждому символу строки first
         мне надо знать совпадения предыдущего символа строки first, поэтому при переходе к след символу
         я запоминаю совпадения с предыдущем символом в mas1
        */
        int lenFirst = first.length();
        int lenSec = second.length();
        int len;
        int maxLen = 0;
        int lastInd = -1;
        int firstInd = -1;
        String res;

        int[] mas1 = new int[lenSec]; //запоминаем столб
        int[] mas2 = new int[lenSec]; //проверяем состояние


        for (int i = 0; i < lenFirst; i++) {
            for (int j = 0; j < lenSec; j++) {
                if (first.charAt(i) != second.charAt(j)) {
                    len = 0;
                } else {
                    if ((i == 0) || (j == 0)) {
                        len = 1;

                    } //если совпадение в начальном символах
                    else {
                        len = mas1[j - 1] + 1; // добавляю предыдущее состояние
                    }
                }
                mas2[j] = len;

                if (len > maxLen) {
                    maxLen = len;
                    lastInd = i + 1;
                    firstInd = lastInd - maxLen;
                }

            }

            int[] swap = mas1;
            mas1 = mas2;
            mas2 = swap;
        }

        if (firstInd != -1) res = first.substring(firstInd, lastInd);
        else res = "";

        return res;
    }



    /**
     * Число простых чисел в интервале
     * Простая
     *
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     *
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    static public int calcPrimesNumber(int limit) {
        throw new NotImplementedError();
    }

    /**
     * Балда
     * Сложная
     *
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     *
     * И Т Ы Н
     * К Р А Н
     * А К В А
     *
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     *
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     *
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     *
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {
        throw new NotImplementedError();
    }
}
