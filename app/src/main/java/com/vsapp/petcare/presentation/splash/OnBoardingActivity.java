package com.vsapp.petcare.presentation.splash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vsapp.petcare.R;
import com.vsapp.petcare.Utils.SharedPreferenceUtil;
import com.vsapp.petcare.Utils.ui.pageindicatorview.PageIndicatorView;
import com.vsapp.petcare.presentation.home.MainActivity;

/**
 * Created by venkat on 28/1/18.
 */

public class OnBoardingActivity extends AppCompatActivity {

    private static final String TAG = "OnBoardingActivity";
    private ScreenSlidePagerAdapter screenSlidePagerAdapter;
    private ViewPager viewPager;
    private Button skip, done;
    PageIndicatorView pageIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        skip = findViewById(R.id.skip_onboarding);
        done = findViewById(R.id.done_onboarding);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: done");
                onMainScreen();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: skip");
                onMainScreen();
            }
        });

        viewPager = findViewById(R.id.viewPager);
        screenSlidePagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(screenSlidePagerAdapter);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        setPageListener(viewPager);
    }

    private void onMainScreen() {
        SharedPreferenceUtil.getInstance(getContext()).setOnBoardingDone(true);
        Intent signUpScreenIntent = new Intent(getContext(), MainActivity.class);
        signUpScreenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(signUpScreenIntent);
    }

    private Context getContext() {
        return OnBoardingActivity.this;
    }


    private void setPageListener(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 3) {
                    skip.setVisibility(View.INVISIBLE);
                    done.setVisibility(View.VISIBLE);
                } else {
                    if (skip.getVisibility() == View.INVISIBLE) {
                        skip.setVisibility(View.VISIBLE);
                        done.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ScreenSlideFragment extends Fragment {

        public ScreenSlideFragment() {
        }


        @SuppressLint("SetTextI18n")
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            Bundle args = getArguments();
            assert args != null;
            int position = args.getInt("position");
            int layoutId = R.layout.fragment_onboarding;
            ViewGroup rootView = (ViewGroup) inflater.inflate(layoutId, container, false);

            if (position == 0) {
                initFirstScreenViews(rootView);
            }
            if (position == 1) {
                initSecondScreenViews(rootView);
            }
            if (position == 2) {
                initThirdScreenViews(rootView);
            }
            if (position == 3) {
                initFourthScreenViews(rootView);
            }
            return rootView;
        }

        @SuppressLint("SetTextI18n")
        private void initFourthScreenViews(ViewGroup rootView) {
            ImageView introImage = rootView.findViewById(R.id.intro_image);
            TextView descriptionText = rootView.findViewById(R.id.description_text);
            descriptionText.setText("Find any animal here");
            introImage.setImageResource(R.drawable.animals);
        }

        @SuppressLint("SetTextI18n")
        private void initThirdScreenViews(ViewGroup rootView) {
            ImageView introImage = rootView.findViewById(R.id.intro_image);
            TextView descriptionText = rootView.findViewById(R.id.description_text);
            descriptionText.setText("Find any animal here");
            introImage.setImageResource(R.drawable.animals);
        }

        @SuppressLint("SetTextI18n")
        private void initSecondScreenViews(ViewGroup rootView) {
            ImageView introImage = rootView.findViewById(R.id.intro_image);
            TextView descriptionText = rootView.findViewById(R.id.description_text);
            descriptionText.setText("Find any animal here");
            introImage.setImageResource(R.drawable.animals);
        }

        @SuppressLint("SetTextI18n")
        private void initFirstScreenViews(ViewGroup rootView) {
            ImageView introImage = rootView.findViewById(R.id.intro_image);
            TextView descriptionText = rootView.findViewById(R.id.description_text);
            descriptionText.setText("Find any animal here");
            introImage.setImageResource(R.drawable.animals);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ScreenSlideFragment fragment = new ScreenSlideFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
