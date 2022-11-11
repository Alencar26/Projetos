public class FatorialDesejado02 {
    public static void  main(String[] args) {
        //ESSA FORMA ACHEI NA INTERNET
        System.out.println(fatorailDesejado(10));
    }

        public static int fatorailDesejado(int n) {
        int ans = 0, p = -1, m = 0;
        for (int i = 0; n > 0; n--, i++){
            switch(i % 4){
                case 0 -> {ans -= m * p; m = n;}
                case 1 -> m *= n;
                case 2 -> m /= n;
                case 3 -> m -= n * p;
            }
            if (i == 4){
                p = 1;
            }
        }

        return ans - m * p;
    }
}