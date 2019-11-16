package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }

    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    public int height() {
        return height(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }


    /**
     * Удаление элемента в дереве
     * Средняя
     */
    //обращалась к https://neerc.ifmo.ru/wiki/index.php?title=Дерево_поиска_наивная_реализация#
    //рекурсивный метод
    //Сложность: O(h), h - высота дерева, Ресурсоемкость: O(1)
    @Override
    public boolean remove(Object o) {
        //есть ли у нас дерево и содержится ли в нем удаляемый объект
        if (root == null || o == null) return false;
        if (!contains(o)) return false;
        //привели o к value и создали объект узла для удаления
        T delValue = (T) o;
        Node<T> delNode = new Node<>(delValue);
        root = del(root, delNode);
        size--;
        return true;
    }

    private Node<T> del(Node<T> root, Node<T> o) {
        if (root == null || o == null) return null;
        int compare = root.value.compareTo(o.value);
        if (compare > 0) root.left = del(root.left, o);//удаление из левого поддерева
        if (compare < 0) root.right = del(root.right, o);//из правого
        if (compare == 0)  root = delRoot(root);//метод для удлаления элемента, который находится в корне
        return root;
    }

    private Node<T> delRoot (Node <T> root) {
        //я не могу менять значениу value у root, поэтому создаю новый объект, присваиваю нужное значение value,
        // при этом поддеревья остаются как у root,  затем ссылку на новый объект копирую в root
        Node<T> node;
        //если 2 дочерних узла
        if (root.left != null && root.right != null) {
                node = new Node<>(minNode(root.right).value);
                node.right = root.right;
                node.left = root.left;
                root = node;
                root.right = del(root.right, root);
            } //если 1 дочерний узел
            else if (root.left != null) root = root.left;
            else root = root.right;
            return root;
        }



    private Node<T> minNode(Node<T> x){
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }


            @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {
        private LinkedList<Node<T>> list = new LinkedList<>();
        private Node<T> current = null;


        private BinaryTreeIterator(Node<T> node) {
            addToList(node);

        }

        private void addToList(Node<T> node) {
            while (node != null) {
                list.addFirst(node);
                node = node.left;
            }
        }



        /**
         * Проверка наличия следующего элемента
         * Средняя
         */

        //Сложность: O(1), рексурсоемкость: O(1)
        @Override
        public boolean hasNext() {
            if (list.isEmpty()) return false;
            return true;
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        //Сложность: O(h), рексурсоемкость: O(1), h - высота дерева
        @Override
        public T next() {
            current = list.getFirst();
            list.removeFirst();
            addToList(current.right);
            return current.value;

        }






        /**
         * Удаление следующего элемента
         * Сложная
         */
        //Сложность: O(h), рексурсоемкость: O(1), h - высота дерева
        @Override
        public void remove() {
            root = del(root, current);
            size--;
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator(root);
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        // TODO
        throw new NotImplementedError();
    }


    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
