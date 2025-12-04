import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

/**
 * Клас BasicDataOperationUsingList реалізує операції з колекціями типу Vector для даних типу int.
 *
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataOperations()} - Виконує комплекс операцій з даними.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив цілих чисел.</li>
 *   <li>{@link #findInArray()} - Здійснює пошук елемента в масиві цілих чисел.</li>
 *   <li>{@link #locateMinMaxInArray()} - Визначає найменше і найбільше значення в масиві.</li>
 *   <li>{@link #sortList()} - Сортує колекцію Vector з цілих чисел.</li>
 *   <li>{@link #findInList()} - Пошук конкретного значення в списку.</li>
 *   <li>{@link #locateMinMaxInList()} - Пошук мінімального і максимального значення в списку.</li>
 * </ul>
 */
public class BasicDataOperationUsingList {

    private int intValueToSearch;
    private Integer[] intArray;
    private Vector<Integer> intList;

    /**
     * Конструктор, який ініціалізує об’єкт з даними.
     *
     * @param intValueToSearch Значення для пошуку
     * @param intArray Масив цілих чисел
     */
    BasicDataOperationUsingList(int intValueToSearch, Integer[] intArray) {
        this.intValueToSearch = intValueToSearch;
        this.intArray = intArray;
        this.intList = new Vector<>(Arrays.asList(intArray));
    }

    /**
     * Виконує комплексні операції з даними:
     * - пошук у списку
     * - визначення мін/макс у списку
     * - сортування списку
     * - пошук у масиві
     * - визначення мін/макс у масиві
     * - сортування масиву
     */
    public void executeDataOperations() {

        findInList();
        locateMinMaxInList();

        sortList();

        findInList();
        locateMinMaxInList();

        findInArray();
        locateMinMaxInArray();

        performArraySorting();

        findInArray();
        locateMinMaxInArray();

        // збереження відсортованого масиву
        DataFileHandler.writeArrayToFile(intArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Упорядковує масив цілих чисел за зростанням.
     */
    void performArraySorting() {
        long timeStart = System.nanoTime();

        Arrays.sort(intArray);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву цілих чисел");
    }

    /**
     * Здійснює пошук значення в масиві цілих чисел.
     */
    void findInArray() {
        long timeStart = System.nanoTime();

        int position = Arrays.binarySearch(this.intArray, intValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в масиві цілих чисел");

        if (position >= 0) {
            System.out.println("Елемент '" + intValueToSearch + "' знайдено в масиві за позицією: " + position);
        } else {
            System.out.println("Елемент '" + intValueToSearch + "' відсутній у масиві.");
        }
    }

    /**
     * Визначає мінімальне та максимальне значення в масиві цілих чисел.
     */
    void locateMinMaxInArray() {
        if (intArray == null || intArray.length == 0) {
            System.out.println("Масив порожній або не ініціалізований.");
            return;
        }

        long timeStart = System.nanoTime();

        int minValue = intArray[0];
        int maxValue = intArray[0];

        for (int currentValue : intArray) {
            if (currentValue < minValue) {
                minValue = currentValue;
            }
            if (currentValue > maxValue) {
                maxValue = currentValue;
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, "визначення мінімального і максимального значення в масиві");

        System.out.println("Мінімальне значення в масиві: " + minValue);
        System.out.println("Максимальне значення в масиві: " + maxValue);
    }

    /**
     * Пошук значення у списку Vector цілих чисел.
     */
    void findInList() {
        long timeStart = System.nanoTime();

        int position = Collections.binarySearch(this.intList, intValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в списку Vector");

        if (position >= 0) {
            System.out.println("Елемент '" + intValueToSearch + "' знайдено в Vector за позицією: " + position);
        } else {
            System.out.println("Елемент '" + intValueToSearch + "' відсутній у Vector.");
        }
    }

    /**
     * Визначає мінімальне та максимальне значення у списку Vector.
     */
    void locateMinMaxInList() {
        if (intList == null || intList.isEmpty()) {
            System.out.println("Список Vector порожній або не ініціалізований.");
            return;
        }

        long timeStart = System.nanoTime();

        int minValue = Collections.min(intList);
        int maxValue = Collections.max(intList);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мінімального і максимального значення в Vector");

        System.out.println("Мінімальне значення в Vector: " + minValue);
        System.out.println("Максимальне значення в Vector: " + maxValue);
    }

    /**
     * Упорядковує список Vector з цілих чисел.
     */
    void sortList() {
        long timeStart = System.nanoTime();

        Collections.sort(intList);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування Vector цілих чисел");
    }
}
