package appzonngo.com.app.ismcenter.ZonngoApp.recovery;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import appzonngo.com.app.ismcenter.ZonngoApp.recovery.Utilities.MyDialoges;
import appzonngo.com.app.ismcenter.zonngo2.R;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.Sesion.Preferences;

public class MH_Activity_Presentacion extends AppCompatActivity {

    static int PAGE_TOTAL=3;
    static int PAGE_1=0;
    static int PAGE_2=1;
    static int PAGE_3=2;

    ViewPager pagerPresentacion;
    View viewFlipper;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Preferences.isLogin(MH_Activity_Presentacion.this)){
            Intent intent = new Intent(getApplicationContext(),MH_Principal.class);
            startActivity(intent);
            finish();
        }else {
            setContentView(R.layout.mh_activity_presentacion);

            dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
            dots = new TextView[PAGE_TOTAL];

            pagerPresentacion = (ViewPager) findViewById(R.id.pagerPresentacion);
            pagerPresentacion.setAdapter(new MainPageAdapter(PAGE_TOTAL));
            pagerPresentacion.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    addBottomDots(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


            if (!Preferences.isNotNewApp(MH_Activity_Presentacion.this)) {
                addBottomDots(PAGE_1);
                pagerPresentacion.setCurrentItem(PAGE_1);
            } else {
                addBottomDots(PAGE_3);
                pagerPresentacion.setCurrentItem(PAGE_3);
            }
        }
    }


    Button btnWithFacebook, btnWithEmail;
    TextView ImgBtnEntrar;
    class MainPageAdapter extends PagerAdapter {
        int numView=0;

        public MainPageAdapter(int numView) {
            this.numView = numView;
        }

        @Override
        public int getCount() {
            return numView;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            View page = null;

            if(position==PAGE_1){
                viewFlipper = (FrameLayout) LayoutInflater.from(MH_Activity_Presentacion.this).inflate(R.layout.mh_presentacion_1, null);

            } else if (position==PAGE_2){
                viewFlipper = (FrameLayout) LayoutInflater.from(MH_Activity_Presentacion.this).inflate(R.layout.mh_presentacion_2, null);

            } else if (position==PAGE_3){
                viewFlipper = (FrameLayout) LayoutInflater.from(MH_Activity_Presentacion.this).inflate(R.layout.mh_presentacion_3, null);
                //btnWithFacebook=(Button) viewFlipper.findViewById(R.id.btnWithFacebook);
                btnWithEmail=(Button) viewFlipper.findViewById(R.id.btnWithEmail);
                ImgBtnEntrar=(TextView) viewFlipper.findViewById(R.id.ImgBtnEntrar);

                /*btnWithFacebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MH_Activity_Presentacion.this,"Opción en construción",Toast.LENGTH_LONG).show();
                    }
                });*/

                btnWithEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyDialoges.showProgressDialog(MH_Activity_Presentacion.this,"Espere...");
                        Intent intent = new Intent(getApplicationContext(), MH_Activity_Register.class);
                        startActivity(intent);
                        //finish();
                    }
                });

                ImgBtnEntrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyDialoges.showProgressDialog(MH_Activity_Presentacion.this,"Espere...");
                        Intent intent = new Intent(getApplicationContext(), MH_Activity_Login.class);
                        startActivity(intent);
                        //finish();
                    }
                });
            }


            page = viewFlipper;
            collection.addView(page, 0);
            return page;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            Log.e("destroyItem",String.valueOf(position));
        }
    }

    private void addBottomDots(int currentPage) {


        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyDialoges.dismissProgressDialog();
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyDialoges.dismissProgressDialog();
    }
}
