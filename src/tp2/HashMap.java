package tp2;

public class HashMap<KeyType, DataType> {

    private static final int DEFAULT_CAPACITY = 20;
    private static final float DEFAULT_LOAD_FACTOR = 0.5f;
    private static final int CAPACITY_INCREASE_FACTOR = 2;

    private Node[] map;
    private int size = 0;
    private int capacity;
    private final float loadFactor; // Compression factor

    /**
     * Constructeur par défaut
     */
    public HashMap() { this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR); }

    /**
     * Constructeur par parametre
     * @param initialCapacity
     */
    public HashMap(int initialCapacity) {
        this(initialCapacity > 0 ? initialCapacity : DEFAULT_CAPACITY,
                DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructeur par parametres
     * @param initialCapacity
     * @param loadFactor
     */
    public HashMap(int initialCapacity, float loadFactor) {
        capacity = initialCapacity;
        this.loadFactor = 1 / loadFactor;
        map = new Node[capacity];
    }

    /**
     * Finds the index attached to a particular key
     * This is the hashing function ("Fonction de dispersement")
     * @param key Value used to access to a particular instance of a DataType within map
     * @return Index value where this key should be placed in attribute map
     */
    private int hash(KeyType key){
        int keyHash = key.hashCode() % capacity;
        return Math.abs(keyHash);
    }

    /**
     * @return if map should be rehashed
     */
    private boolean needRehash() {
        return size * loadFactor > capacity;
    }

    /**
     * @return Number of elements currently in the map
     */
    public int size() {
        return size;
    }

    /**
     * @return Current reserved space for the map
     */
    public int capacity(){ return capacity; }

    /**
     * @return if map does not contain any element
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /** TODO
     * Increases capacity by CAPACITY_INCREASE_FACTOR (multiplication) and
     * reassigns all contained values within the new map
     */
    private void rehash() {
        /*Node[] newMap = new Node[capacity * CAPACITY_INCREASE_FACTOR];
        for (int i = 0; i < map.length; i++) {
            newMap[i] = map[i];
        }
        map = newMap;*/
        Node[] oldMap = map.clone();
        clear();
        capacity *= CAPACITY_INCREASE_FACTOR;
        map = new Node[capacity];

        /*for(int i = 0; i < oldMap.length; i++){
            if(oldMap[i] != null) {
                put((KeyType) oldMap[i].key, (DataType) oldMap[i].data);
            }
        }*/
        for(Node node : oldMap){
            if(node != null)
                put((KeyType) node.key, (DataType) node.data);
        }
    }

    /** TODO
     * Finds if map contains a key
     * @param key Key which we want to know if exists within map
     * @return if key is already used in map
     */
    public boolean containsKey(KeyType key) {
        int keyIndex = hash(key);
        DataType removedValue = null;
        Node<KeyType, DataType> node = map[keyIndex];
        while(node != null) {
            if (node.key.equals(key))
                return true;
            node = node.next;
        }
        return false;
    }

    /** TODO
     * Finds the value attached to a key
     * @param key Key which we want to have its value
     * @return DataType instance attached to key (null if not found)
     */
    public DataType get(KeyType key) {
        DataType value = null;
        int keyIndex = hash(key);
        Node<KeyType, DataType> node = map[keyIndex];
        while(node != null){
            if(node.key.equals(key)){
                value = node.data;
                break;
            }
            node = node.next;
        }
        return value;
    }

    /**TODO
     * Assigns a value to a key
     * @param key Key which will have its value assigned or reassigned
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType put(KeyType key, DataType value) {
        int keyIndex = hash(key);
        DataType oldValue = null;

        if(map[keyIndex] == null) {
            map[keyIndex] = new Node(key, value);
            size++;
            if (needRehash())
                rehash();
        }else{
            size++;
            Node<KeyType, DataType> nodePrecedente = null;
            Node<KeyType, DataType> node = map[keyIndex];
            while(node != null){
                if(node.key.equals(key)){
                    size--; // key already existed
                    oldValue = node.data;
                    node.data = value;
                    break;
                }
                nodePrecedente = node;
                node = node.next;
            }
            if(nodePrecedente != null)
                nodePrecedente.next = new Node(key, value);
        }
        return oldValue;
    }

    /**TODO
     * Removes the node attached to a key
     * @param key Key which is contained in the node to remove
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType remove(KeyType key) {
        int keyIndex = hash(key);
        DataType removedValue = null;
        Node<KeyType, DataType> precedente = null;
        Node<KeyType, DataType> node = map[keyIndex];
        while(node != null) {
            if (node.key.equals(key)) {
                if (precedente == null) {
                    removedValue = node.data;
                    node = node.next;
                    map[keyIndex] = node;
                    size--;
                    return removedValue;
                } else {
                    removedValue = node.data;
                    precedente.next = node.next;
                    size--;
                    return removedValue;
                }
            }
            precedente = node;
            node = node.next;
        }
        return removedValue;
    }

    /**TODO
     * Removes all nodes contained within the map
     */
    public void clear() {
        for(var node: map){
            if(node != null)
                remove((KeyType) node.key);
        }
    }

    /**
     * Definition et implementation de la classe Node
     */
    static class Node<KeyType, DataType> {
        final KeyType key;
        DataType data;
        Node<KeyType, DataType> next; // Pointer to the next node within a Linked List

        Node(KeyType key, DataType data)
        {
            this.key = key;
            this.data = data;
            next = null;
        }
    }
}