import java.util.LinkedList;
import java.util.Random;

class HashTableImplementation {
    public static void main(String[] args) throws Exception {
        IPHashSet test = new IPHashSet();
        test.add("87.23.125.4");
        System.out.println(test.get("87.23.125.4"));
        test.remove("87.23.125.4");
        System.out.println(test.get("87.23.125.4"));
    }

}

class IPHashSet {

    int size = 257; //default
    int k = 4; //default
    int[] hashCoeff = new int[k];
    Random rand = new Random();
    LinkedList<String>[] storage;

    int position;

    public IPHashSet() {
        this._initHashCoeff();
        this._initStorage();
    }

    // if user is able to estimate a expected number of IPs to store
    public IPHashSet(int expectedSize) {
        this.size = MathHelper.getNextPrime(expectedSize);
        this._initHashCoeff();
        this._initStorage();
    }

    public void add(String IPStr) throws Exception {
        int[] IPAddress = this._convertIPAddress(IPStr);
        int position = this._getHash(IPAddress);
        LinkedList<String> bucket = this.storage[position];
        if (this.storage[position] == null) bucket = new LinkedList<>();
        bucket.add(IPStr);
        this.storage[position] = bucket;
    }

    public boolean get(String IPStr) throws Exception {
        int[] IPAddress = this._convertIPAddress(IPStr);
        int position = this._getHash(IPAddress);
        LinkedList<String> bucket = this.storage[position];
        if (bucket == null) return false;
        return bucket.contains(IPStr);
    }

    public void remove(String IPStr) throws Exception {
        int[] IPAddress = this._convertIPAddress(IPStr);
        int position = this._getHash(IPAddress);
        LinkedList<String> bucket = this.storage[position];
        if (bucket == null) return;
        bucket.remove(IPStr);
        this.storage[position] = bucket;
    }

    private int[] _convertIPAddress(String IPStr) throws Exception {
        int[] IPaddress = new int[4];
        String[] IPStrSplit = IPStr.split("\\.");
        try {
            for (int i=0; i<IPStrSplit.length; i++) {
                IPaddress[i] = Integer.parseInt(IPStrSplit[i]);
            }
        } catch (NumberFormatException e) {
            throw new Exception("IP address format incorrect");
        };
        return IPaddress;
    }

    private int _getHash(int[] IPAddress) {
        int sum = 0;
        for (int i=0; i<k; i++) {
            sum += hashCoeff[i] * IPAddress[i];
        }
        return MathHelper.mod(sum, this.size);
    }

    // arrays covariant generics invariant problem
    @SuppressWarnings("unchecked")
    private void _initStorage() {
        this.storage = new LinkedList[this.size];
    }

    private void _initHashCoeff() {
        for (int i=0; i<k; i++) {
            this.hashCoeff[i] = rand.nextInt(size-1);
        }
    }

}

class MathHelper {

    // ***In Java, % returns remainder, not modulo
    public static int mod(int num, int modulus) {
        int rem = num % modulus;
        if (rem < 0) rem += modulus;
        return rem;
    }

    // x^y mod N
    public static int modexp(int x, int y, int N) {
        if (y == 0) return 1;
        int z = modexp(x, y >> 1, N);

        int pow = (int) Math.pow(z, 2);
        if (mod(y, 2) == 0) {
            return mod(pow, N);
        } else {
            return mod(pow * x, N);
        }
    }

    // check if num is a prime number
    // if prime - return true
    public static boolean primality(int num) {
        for (int i=1; i<num; i++) {
            if (modexp(i, num-1,num) != 1) {
                return false;
            }
        }
        return true;
    }

    // Bertrand's postulate (actually a theorem) states that if n > 3 is an integer,
    // then there always exists at least one prime number p with n < p < 2n − 2.
    // A weaker but more elegant formulation is: for every n > 1
    // there is always at least one prime p such that n < p < 2n.
    public static int getNextPrime(int number) {
        if (number % 2 == 1) number += 3;

        for (int i=number; i<number*2; i+=2) {
            if (primality(i)) return i;
        }

        return 0;
    }
}