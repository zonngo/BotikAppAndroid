package appzonngo.com.app.ismcenter.ZonngoApp.recovery.Utilities;

/**
 * Created by Marwuin on 21/3/2017.
 */

public class Calculate {

    public double Redondear(double numero) {
        return Math.rint(numero * 1000) / 1000;
    }
}
