package pi.utils;

import java.util.*;
import java.math.*;

public class Pi {

    public Pi (int casas) {
        this.casas = casas;
        this.folga = 2;
        mc = new MathContext(casas + folga, RoundingMode.HALF_EVEN);
        eps = BigDecimal.ONE.scaleByPowerOfTen (- (casas + folga));
    }
    private static long numerador (int k) {
        // 4(8k+4)(8k+5)(8k+6) - 2(8k+1)(8k+5)(8k+6) -1 (8k+1)(8k+4)(8k+6) -1 ((8k+1)(8k+4)(8k+5)
        int _8k = 8 * k;
        int _8k1 = _8k + 1;
        int _8k4 = _8k + 4;
        int _8k5 = _8k + 5;
        int _8k6 = _8k + 6;
        return 4L * _8k4 * _8k5 * _8k6
                - 2L * _8k1 * _8k5 * _8k6
                - (long) _8k1 * _8k4 * _8k6
                - (long) _8k1 * _8k4 * _8k5;
    }
    private static long denominador (int k) {
        // (8k+1)(8k+4)(8k+5)(8k+6)
        int _8k = 8 * k;
        int _8k1 = _8k + 1;
        int _8k4 = _8k + 4;
        int _8k5 = _8k + 5;
        int _8k6 = _8k + 6;
        return (long) _8k1 * _8k4 * _8k5 * _8k6;
    }
    private BigDecimal factor (BigDecimal _16k, int k) {
        return BigDecimal.valueOf (numerador(k)).divide (_16k.multiply (BigDecimal.valueOf (denominador(k))), mc);
    }
    public BigDecimal calc() {
        BigDecimal ret = BigDecimal.ZERO;
        BigDecimal _16k = BigDecimal.ONE;
        BigDecimal _16 = BigDecimal.valueOf (16L);
        int k = 0;
        BigDecimal f;
        do {
            f = factor (_16k, k);
            ret = ret.add (f);
            _16k = _16k.multiply (_16);
            k++;
        } while (f.abs().compareTo (eps) >= 0);
        return ret.plus (new MathContext (casas+1, RoundingMode.HALF_EVEN));
    }
    private int casas;
    private int folga;
    private MathContext mc;
    private BigDecimal eps;
}
