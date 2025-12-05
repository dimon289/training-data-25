import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Клас BasicDataOperationUsingMap реалізує операції з колекціями типу Map
 * для зберігання пар ключ-значення.
 *
 * Методи:
 * - executeDataOperations()
 * - findByKeyInHashtable()
 * - findByValueInHashtable()
 * - addEntryToHashtable()
 * - removeByKeyFromHashtable()
 * - removeByValueFromHashtable()
 * - sortHashtable()
 * - sortLinkedHashMap()
 */
public class BasicDataOperationUsingMap {

    // ============================================================
    // === 1. RECORD DUCK (повинен бути оголошений на початку класу)
    // ============================================================

    /**
     * Домашня тварина Duck у форматі record.
     * Java автоматично генерує:
     * - equals()
     * - hashCode()
     * - toString()
     */
    public record Duck(String nickname, Integer chicks) {}

    // ============================================================
    // === 2. Поля класу
    // ============================================================

    private final Duck KEY_TO_SEARCH_AND_DELETE = new Duck("Кряка", 3);
    private final Duck KEY_TO_ADD = new Duck("Кача", 11);

    private final String VALUE_TO_SEARCH_AND_DELETE = "Олег";
    private final String VALUE_TO_ADD = "Марина";

    private Hashtable<Duck, String> hashtable;
    private LinkedHashMap<Duck, String> linkedHashMap;

    /**
     * Comparator для Duck:
     * - nickname за зростанням
     * - chicks за спаданням
     */
    private static final Comparator<Duck> DUCK_COMPARATOR =
        Comparator.comparing(Duck::nickname)
                  .thenComparing(Duck::chicks, Comparator.reverseOrder());

    /**
     * Comparator для сортовання записів Map.Entry за значенням (власником)
     */
    static class OwnerValueComparator implements Comparator<Map.Entry<Duck, String>> {
        @Override
        public int compare(Map.Entry<Duck, String> e1, Map.Entry<Duck, String> e2) {
            return e1.getValue().compareTo(e2.getValue());
        }
    }

    // ============================================================
    // === 3. Конструктор
    // ============================================================

    public BasicDataOperationUsingMap(Hashtable<Duck, String> hashtable,
                                      LinkedHashMap<Duck, String> linkedHashMap) {
        this.hashtable = hashtable;
        this.linkedHashMap = linkedHashMap;
    }

    // ============================================================
    // === 4. Основне виконання операцій
    // ============================================================

    public void executeDataOperations() {

        // -------------------- Hashtable --------------------
        System.out.println("========= Операції з Hashtable =========");
        System.out.println("Початковий розмір: " + hashtable.size());

        findByKeyInHashtable();
        findByValueInHashtable();

        printHashtable();
        sortHashtable();
        printHashtable();

        addEntryToHashtable();
        removeByKeyFromHashtable();
        removeByValueFromHashtable();

        System.out.println("Кінцевий розмір Hashtable: " + hashtable.size());

        // -------------------- LinkedHashMap --------------------
        System.out.println("\n========= Операції з LinkedHashMap =========");
        System.out.println("Початковий розмір: " + linkedHashMap.size());

        printLinkedHashMap();
        sortLinkedHashMap();
        printLinkedHashMap();

        System.out.println("Кінцевий розмір LinkedHashMap: " + linkedHashMap.size());
    }

    // ============================================================
    // === 5. Методи для Hashtable
    // ============================================================

    private void printHashtable() {
        System.out.println("\n=== Пари ключ-значення в Hashtable ===");
        long t = System.nanoTime();

        for (Map.Entry<Duck, String> e : hashtable.entrySet()) {
            System.out.println(" " + e.getKey() + " -> " + e.getValue());
        }

        PerformanceTracker.displayOperationTime(t, "виведення Hashtable");
    }

    private void sortHashtable() {
        long t = System.nanoTime();

        List<Duck> keys = new ArrayList<>(hashtable.keySet());
        keys.sort(DUCK_COMPARATOR);

        Hashtable<Duck, String> sorted = new Hashtable<>();
        for (Duck d : keys)
            sorted.put(d, hashtable.get(d));

        hashtable = sorted;

        PerformanceTracker.displayOperationTime(t, "сортування Hashtable");
    }

    private void findByKeyInHashtable() {
        long t = System.nanoTime();

        boolean found = hashtable.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(t, "пошук за ключем Hashtable");

        if (found)
            System.out.println("Знайдено: " + hashtable.get(KEY_TO_SEARCH_AND_DELETE));
        else
            System.out.println("Ключ не знайдено.");
    }

    void findByValueInHashtable() {
        long timeStart = System.nanoTime();

        // Копіюємо entries та сортуємо
        List<Map.Entry<Duck, String>> list = new ArrayList<>(hashtable.entrySet());
        OwnerValueComparator comparator = new OwnerValueComparator();
        Collections.sort(list, comparator);

        // Створюємо безпечний пошуковий елемент
        Map.Entry<Duck, String> searchEntry = new Map.Entry<>() {
            @Override public Duck getKey() { return new Duck("TEMP", 0); }
            @Override public String getValue() { return VALUE_TO_SEARCH_AND_DELETE; }
            @Override public String setValue(String value) { return null; }
        };

        int pos = Collections.binarySearch(list, searchEntry, comparator);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за значенням Hashtable");

        if (pos >= 0) {
            Map.Entry<Duck, String> found = list.get(pos);
            System.out.println("Знайдено власника '" + VALUE_TO_SEARCH_AND_DELETE + "' у Duck: " + found.getKey());
        } else {
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' не знайдено.");
        }
    }


    private void addEntryToHashtable() {
        long t = System.nanoTime();
        hashtable.put(KEY_TO_ADD, VALUE_TO_ADD);
        PerformanceTracker.displayOperationTime(t, "додавання до Hashtable");
    }

    private void removeByKeyFromHashtable() {
        long t = System.nanoTime();
        hashtable.remove(KEY_TO_SEARCH_AND_DELETE);
        PerformanceTracker.displayOperationTime(t, "видалення за ключем Hashtable");
    }

    private void removeByValueFromHashtable() {
        long t = System.nanoTime();
        hashtable.entrySet().removeIf(e -> e.getValue().equals(VALUE_TO_SEARCH_AND_DELETE));
        PerformanceTracker.displayOperationTime(t, "видалення за значенням Hashtable");
    }

    // ============================================================
    // === 6. Методи для LinkedHashMap
    // ============================================================

    private void printLinkedHashMap() {
        System.out.println("\n=== Пари ключ-значення в LinkedHashMap ===");
        long t = System.nanoTime();

        linkedHashMap.forEach((k, v) -> System.out.println(" " + k + " -> " + v));

        PerformanceTracker.displayOperationTime(t, "виведення LinkedHashMap");
    }

    private void sortLinkedHashMap() {
        long t = System.nanoTime();

        List<Duck> keys = new ArrayList<>(linkedHashMap.keySet());
        keys.sort(DUCK_COMPARATOR);

        LinkedHashMap<Duck, String> sorted = new LinkedHashMap<>();
        for (Duck d : keys)
            sorted.put(d, linkedHashMap.get(d));

        linkedHashMap = sorted;

        PerformanceTracker.displayOperationTime(t, "сортування LinkedHashMap");
    }

    // ============================================================
    // === 7. MAIN
    // ============================================================

    public static void main(String[] args) {

        // Початкові дані
        Hashtable<Duck, String> hashtable = new Hashtable<>();
        hashtable.put(new Duck("Кряка", 3), "Роман");
        hashtable.put(new Duck("Крила", 5), "Світлана");
        hashtable.put(new Duck("Кряка", 7), "Олег");
        hashtable.put(new Duck("Плавун", 2), "Анжела");
        hashtable.put(new Duck("Біла", 4), "Василь");
        hashtable.put(new Duck("Водяна", 6), "Олег");
        hashtable.put(new Duck("Гуска", 8), "Наталка");
        hashtable.put(new Duck("Крила", 9), "Іван");
        hashtable.put(new Duck("Літачка", 1), "Світлана");
        hashtable.put(new Duck("Перо", 10), "Петро");

        LinkedHashMap<Duck, String> linkedHashMap = new LinkedHashMap<>(hashtable);

        BasicDataOperationUsingMap operations =
                new BasicDataOperationUsingMap(hashtable, linkedHashMap);

        operations.executeDataOperations();
    }
}
