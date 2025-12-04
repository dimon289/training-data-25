import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;


/**
 * Клас BasicDataOperationUsingMap реалізує операції з колекціями типу Map для зберігання пар ключ-значення.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataOperations()} - Виконує комплекс операцій з даними Map.</li>
 *   <li>{@link #findByKey()} - Здійснює пошук елемента за ключем в Map.</li>
 *   <li>{@link #findByValue()} - Здійснює пошук елемента за значенням в Map.</li>
 *   <li>{@link #addEntry()} - Додає новий запис до Map.</li>
 *   <li>{@link #removeByKey()} - Видаляє запис з Map за ключем.</li>
 *   <li>{@link #removeByValue()} - Видаляє записи з Map за значенням.</li>
 *   <li>{@link #sortByKey()} - Сортує Map за ключами.</li>
 *   <li>{@link #sortByValue()} - Сортує Map за значеннями.</li>
 * </ul>
 */
public class BasicDataOperationUsingMap {

    private final Duck KEY_TO_SEARCH_AND_DELETE = new Duck("Кряка", 3);
    private final Duck KEY_TO_ADD = new Duck("Кача", 11);

    private final String VALUE_TO_SEARCH_AND_DELETE = "Олег";
    private final String VALUE_TO_ADD = "Богдан";

    private Hashtable<Duck, String> hashtable;
    private LinkedHashMap<Duck, String> linkedHashMap;

    // ======= КОМПАРАТОР ДЛЯ ПОШУКУ ЗА ЗНАЧЕННЯМ =======
    static class OwnerValueComparator implements Comparator<Map.Entry<Duck, String>> {
        @Override
        public int compare(Map.Entry<Duck, String> e1, Map.Entry<Duck, String> e2) {
            String v1 = e1.getValue();
            String v2 = e2.getValue();
            if (v1 == null && v2 == null) return 0;
            if (v1 == null) return -1;
            if (v2 == null) return 1;
            return v1.compareTo(v2);
        }
    }

    // ======= КЛАС DUCK (домашня тварина) =======
    public static class Duck implements Comparable<Duck> {
        private final String nickname;
        private final Integer chicks;

        public Duck(String nickname) {
            this.nickname = nickname;
            this.chicks = 0;
        }

        public Duck(String nickname, Integer chicks) {
            this.nickname = nickname;
            this.chicks = chicks;
        }

        public String getNickname() { 
            return nickname; 
        }

        public Integer getChicks() {
            return chicks;
        }

        @Override
        public int compareTo(Duck other) {
            if (other == null) return 1;

            int nicknameComparison = nickname.compareTo(other.nickname);
            if (nicknameComparison != 0) return nicknameComparison;

            return other.chicks.compareTo(this.chicks); // спадання
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Duck duck = (Duck) obj;
            return nickname.equals(duck.nickname) && chicks.equals(duck.chicks);
        }

        @Override
        public int hashCode() {
            return 31 * nickname.hashCode() + chicks.hashCode();
        }

        @Override
        public String toString() {
            return "Duck{nickname='" + nickname + "', chicks=" + chicks + ", hashCode=" + hashCode() + "}";
        }
    }

    // ======= КОНСТРУКТОР =======
    BasicDataOperationUsingMap(Hashtable<Duck, String> hashtable, LinkedHashMap<Duck, String> linkedHashMap) {
        this.hashtable = hashtable;
        this.linkedHashMap = linkedHashMap;
    }

    // ======= ВИКОНАННЯ УСІХ ОПЕРАЦІЙ =======
    public void executeDataOperations() {

        // ===== ОПЕРАЦІЇ З HASHTABLE =====
        System.out.println("========= Операції з Hashtable =========");
        System.out.println("Початковий розмір Hashtable: " + hashtable.size());

        findByKeyInHashtable();
        findByValueInHashtable();

        printHashtable();
        sortHashtable();
        printHashtable();

        findByKeyInHashtable();
        findByValueInHashtable();

        addEntryToHashtable();
        removeByKeyFromHashtable();
        removeByValueFromHashtable();

        System.out.println("Кінцевий розмір Hashtable: " + hashtable.size());

        // ===== ОПЕРАЦІЇ З LinkedHashMap =====
        System.out.println("\n\n========= Операції з LinkedHashMap =========");
        System.out.println("Початковий розмір LinkedHashMap: " + linkedHashMap.size());

        findByKeyInLinkedHashMap();
        findByValueInLinkedHashMap();

        printLinkedHashMap();
        sortLinkedHashMap();
        printLinkedHashMap();

        addEntryToLinkedHashMap();
        removeByKeyFromLinkedHashMap();
        removeByValueFromLinkedHashMap();

        System.out.println("Кінцевий розмір LinkedHashMap: " + linkedHashMap.size());
    }

    // ===== PRINT Hashtable =====
    private void printHashtable() {
        System.out.println("\n=== Пари ключ-значення в Hashtable ===");
        long t = System.nanoTime();
        for (Map.Entry<Duck, String> e : hashtable.entrySet())
            System.out.println("  " + e.getKey() + " -> " + e.getValue());
        PerformanceTracker.displayOperationTime(t, "виведення пари ключ-значення в Hashtable");
    }

    // ===== SORT Hashtable =====
    private void sortHashtable() {
        long t = System.nanoTime();
        List<Duck> keys = new ArrayList<>(hashtable.keySet());
        Collections.sort(keys);

        Hashtable<Duck, String> sorted = new Hashtable<>();
        for (Duck d : keys) sorted.put(d, hashtable.get(d));

        hashtable = sorted;
        PerformanceTracker.displayOperationTime(t, "сортування Hashtable за ключами");
    }

    // ===== FIND KEY Hashtable =====
    void findByKeyInHashtable() {
        long t = System.nanoTime();
        boolean found = hashtable.containsKey(KEY_TO_SEARCH_AND_DELETE);
        PerformanceTracker.displayOperationTime(t, "пошук за ключем в Hashtable");

        if (found)
            System.out.println("Елемент знайдено: " + hashtable.get(KEY_TO_SEARCH_AND_DELETE));
        else
            System.out.println("Елемент НЕ знайдено.");
    }

    // ===== FIND VALUE Hashtable =====
    void findByValueInHashtable() {
        long t = System.nanoTime();

        List<Map.Entry<Duck, String>> list = new ArrayList<>(hashtable.entrySet());
        OwnerValueComparator cmp = new OwnerValueComparator();
        Collections.sort(list, cmp);

        Map.Entry<Duck, String> search = new Map.Entry<Duck, String>() {
            public Duck getKey() { return null; }
            public String getValue() { return VALUE_TO_SEARCH_AND_DELETE; }
            public String setValue(String v) { return null; }
        };

        int pos = Collections.binarySearch(list, search, cmp);
        PerformanceTracker.displayOperationTime(t, "бінарний пошук за значенням в Hashtable");

        if (pos >= 0)
            System.out.println("Власника знайдено: " + list.get(pos).getKey());
        else
            System.out.println("Власника НЕ знайдено.");
    }

    // ===== ADD Hashtable =====
    void addEntryToHashtable() {
        long t = System.nanoTime();
        hashtable.put(KEY_TO_ADD, VALUE_TO_ADD);
        PerformanceTracker.displayOperationTime(t, "додавання запису до Hashtable");
    }

    // ===== REMOVE KEY Hashtable =====
    void removeByKeyFromHashtable() {
        long t = System.nanoTime();
        hashtable.remove(KEY_TO_SEARCH_AND_DELETE);
        PerformanceTracker.displayOperationTime(t, "видалення за ключем з Hashtable");
    }

    // ===== REMOVE VALUE Hashtable =====
    void removeByValueFromHashtable() {
        long t = System.nanoTime();
        List<Duck> toRemove = new ArrayList<>();

        for (Map.Entry<Duck, String> e : hashtable.entrySet())
            if (VALUE_TO_SEARCH_AND_DELETE.equals(e.getValue()))
                toRemove.add(e.getKey());

        for (Duck d : toRemove) hashtable.remove(d);

        PerformanceTracker.displayOperationTime(t, "видалення за значенням з Hashtable");
    }



    // ============================================================
    //                LINKEDHASHMAP (новий тип MAP)
    // ============================================================

    private void printLinkedHashMap() {
        System.out.println("\n=== Пари ключ-значення в LinkedHashMap ===");
        long t = System.nanoTime();
        for (Map.Entry<Duck, String> e : linkedHashMap.entrySet())
            System.out.println("  " + e.getKey() + " -> " + e.getValue());
        PerformanceTracker.displayOperationTime(t, "виведення ключ-значення в LinkedHashMap");
    }

    private void sortLinkedHashMap() {
        long t = System.nanoTime();

        List<Duck> keys = new ArrayList<>(linkedHashMap.keySet());
        Collections.sort(keys);

        LinkedHashMap<Duck, String> sorted = new LinkedHashMap<>();
        for (Duck d : keys) sorted.put(d, linkedHashMap.get(d));

        linkedHashMap = sorted;

        PerformanceTracker.displayOperationTime(t, "сортування LinkedHashMap за ключами");
    }

    void findByKeyInLinkedHashMap() {
        long t = System.nanoTime();
        boolean found = linkedHashMap.containsKey(KEY_TO_SEARCH_AND_DELETE);
        PerformanceTracker.displayOperationTime(t, "пошук за ключем в LinkedHashMap");

        if (found)
            System.out.println("Елемент знайдено: " + linkedHashMap.get(KEY_TO_SEARCH_AND_DELETE));
        else
            System.out.println("Елемент НЕ знайдено.");
    }

    void findByValueInLinkedHashMap() {
        long t = System.nanoTime();

        List<Map.Entry<Duck, String>> list = new ArrayList<>(linkedHashMap.entrySet());
        OwnerValueComparator cmp = new OwnerValueComparator();
        Collections.sort(list, cmp);

        Map.Entry<Duck, String> search = new Map.Entry<Duck, String>() {
            public Duck getKey() { return null; }
            public String getValue() { return VALUE_TO_SEARCH_AND_DELETE; }
            public String setValue(String v) { return null; }
        };

        int pos = Collections.binarySearch(list, search, cmp);
        PerformanceTracker.displayOperationTime(t, "бінарний пошук за значенням в LinkedHashMap");

        if (pos >= 0)
            System.out.println("Власника знайдено: " + list.get(pos).getKey());
        else
            System.out.println("Власника НЕ знайдено.");
    }

    void addEntryToLinkedHashMap() {
        long t = System.nanoTime();
        linkedHashMap.put(KEY_TO_ADD, VALUE_TO_ADD);
        PerformanceTracker.displayOperationTime(t, "додавання в LinkedHashMap");
    }

    void removeByKeyFromLinkedHashMap() {
        long t = System.nanoTime();
        linkedHashMap.remove(KEY_TO_SEARCH_AND_DELETE);
        PerformanceTracker.displayOperationTime(t, "видалення за ключем з LinkedHashMap");
    }

    void removeByValueFromLinkedHashMap() {
        long t = System.nanoTime();
        List<Duck> toRemove = new ArrayList<>();

        for (Map.Entry<Duck, String> e : linkedHashMap.entrySet())
            if (VALUE_TO_SEARCH_AND_DELETE.equals(e.getValue()))
                toRemove.add(e.getKey());

        for (Duck d : toRemove) linkedHashMap.remove(d);

        PerformanceTracker.displayOperationTime(t, "видалення за значенням з LinkedHashMap");
    }




    // ============================================================
    //                    MAIN (дані КАЧОК)
    // ============================================================

    public static void main(String[] args) {

        // Створюємо початкові дані
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

        LinkedHashMap<Duck, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.putAll(hashtable);

        // Запуск програми
        BasicDataOperationUsingMap operations =
            new BasicDataOperationUsingMap(hashtable, linkedHashMap);

        operations.executeDataOperations();
    }
}
