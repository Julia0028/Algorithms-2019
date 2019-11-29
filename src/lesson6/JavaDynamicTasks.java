package lesson6;

import kotlin.NotImplementedError;

import java.util.*;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    //проверка на прохождение тестов котоеда, чуть позже добавлю оценку
    public static String longestCommonSubSequence(String first, String second) {
        if (first.isEmpty() || second.isEmpty()) return "";
        StringBuilder res = new StringBuilder();
        int[][] len = new int[first.length() + 1][second.length() + 1];
        for (int i = 1; i < first.length() + 1; i++) {
            for (int j = 1; j < second.length() + 1; j++) {
                if (first.charAt(i - 1) != second.charAt(j - 1)) len[i][j] = Math.max(len[i - 1][j], len[i][j - 1]);
                else len[i][j] = len[i - 1][j - 1] + 1;
            }
        }
        subSequence(len.length - 1, len[0].length - 1, len, first, second, res);
        return res.reverse().toString();
    }


    private static void subSequence(int i, int j, int[][] len, String first, String second, StringBuilder res) {
        if (i == 0 || j == 0) return;
        if (first.charAt(i - 1) == second.charAt(j - 1)) {
            res.append(second.charAt(j - 1));
            subSequence(i - 1, j - 1, len, first, second, res);
        } else {
            if (len[i - 1][j] >= len[i][j-1]) subSequence(i - 1, j, len, first, second, res);
            else subSequence(i, j - 1, len, first, second, res);
        }
    }


    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    //проверка на прохождение тестов котоеда, чуть позже добавлю оценку
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        List<Integer> res = new ArrayList<>();
        if (list.isEmpty()) return res;
        int[] len = new int[list.size()];
        int max = -1;
        int indMax = 0;
        for (int i = 1; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (list.get(i) > list.get(j) && len[j] > max) {
                    max = len[j];
                    len[i] = max + 1;
                }
            }
            max = -1;
        }
    max = len[0];
    for (int i = 0; i < list.size(); i++) {
        if (len[i]> max) {
            max = len[i];
            indMax = i;
        }
    }
    int a = indMax;
    int b = 0;
    res.add(list.get(indMax));
    while (b != a) {
        for (int i = 0; i < a; i++) {
            if (list.get(i) < list.get(indMax) && len[indMax] - len[i] == 1) {
                res.add(list.get(i));
                indMax = i;
                break;
            }
        }
        b++;
    }
    Collections.reverse(res);
    return res;
    }




    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
