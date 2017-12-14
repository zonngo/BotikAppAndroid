package appzonngo.com.app.ismcenter.ZonngoApp.recovery;

import android.view.View;

import appzonngo.com.app.ismcenter.zonngo2.R;

public class MH_BottomSheetControl {
    public static void opaqueView(View viewTranslucido, boolean isOpaque) {
        if (isOpaque)
            viewTranslucido.setBackgroundColor(viewTranslucido.getContext().getResources().getColor(R.color.gris_semitransparente_dark));
        else
            viewTranslucido.setBackgroundColor(viewTranslucido.getContext().getResources().getColor(R.color.colorTransparent));

        viewTranslucido.setClickable(isOpaque);
    }


}
