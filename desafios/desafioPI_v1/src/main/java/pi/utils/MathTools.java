package pi.utils;

public class MathTools {

//    public static int[] toIntArray(String string) {
//        String[] arrayString = StringTools.toStringArray(string);
//        int[] v = new int[arrayString.length];
//        for(int i = 0; i < arrayString.length; i++){
//            v[i] = Integer.parseInt(arrayString[i]);
//        }
//        return v;
//    }
    public static boolean isPrimo(String palindromo) {
        //boolean isPrimo = false;
        long v = Long.parseLong(palindromo);
        for (int i = 2; i < v; i++) {
            if (v % i == 0) {
                return false;
            }
        }
        return true;
    }
}
