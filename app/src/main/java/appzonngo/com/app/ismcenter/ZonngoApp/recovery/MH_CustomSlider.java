package appzonngo.com.app.ismcenter.ZonngoApp.recovery;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.slider.library.R.id;
import com.daimajia.slider.library.R.layout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

/**
 * Created by Epica on 9/3/2017.
 */

public class MH_CustomSlider extends BaseSliderView {

    public MH_CustomSlider(Context context) {
        super(context);
    }

    public View getView() {
        View v = LayoutInflater.from(this.getContext()).inflate(layout.render_type_text, null);
        ImageView target = (ImageView) v.findViewById(id.daimajia_slider_image);
        LinearLayout frame = (LinearLayout) v.findViewById(id.description_layout);
        frame.setBackgroundColor(Color.TRANSPARENT);

//      if you need description
//      description.setText(this.getDescription());

        this.bindEventAndShow(v, target);

        return v;
    }
}
