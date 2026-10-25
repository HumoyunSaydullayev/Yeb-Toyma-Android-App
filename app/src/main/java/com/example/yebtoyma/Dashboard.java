package com.example.yebtoyma;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.yebtoyma.Fragments.HistoryFragment;
import com.example.yebtoyma.Fragments.MenuFragment;
import com.example.yebtoyma.Fragments.StoreFragment;
import com.example.yebtoyma.Fragments.User.UserFragment;

public class Dashboard extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    public static MeowBottomNavigation bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        if (isConnected(this)) {
            showInternetDialog();
        }
        bottomNav = findViewById(R.id.bottomNav);

        bottomNav.add(new MeowBottomNavigation.Model(1, R.drawable.ic_menu));
        bottomNav.add(new MeowBottomNavigation.Model(2, R.drawable.ic_history));
        bottomNav.add(new MeowBottomNavigation.Model(3, R.drawable.ic_store));
        bottomNav.add(new MeowBottomNavigation.Model(4, R.drawable.ic_person));

        bottomNav.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment;
                if (item.getId()==4){
                    fragment = new UserFragment();
                }else  if (item.getId() ==3){
                    fragment = new StoreFragment();
                }
                else  if (item.getId() ==2){
                    fragment = new HistoryFragment();
                }
                else {
                    fragment = new MenuFragment();
                }
                loadFragment(fragment);
            }
        });

        bottomNav.show(1, true);

        bottomNav.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
            }
        });

        bottomNav.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
            }
        });

    }


    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment_container,fragment, null)
                .commit();
    }

    private void showInternetDialog() {
        Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        View view = LayoutInflater.from(this).inflate(R.layout.no_internet_dialog, findViewById(R.id.no_internet_layout));
        view.findViewById(R.id.try_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected(Dashboard.this)) {
                    showInternetDialog();
                } else {
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    finish();
                }
            }
        });

        dialog.setContentView(view);
        dialog.show();

    }

    private boolean isConnected(Dashboard dashboard) {
        ConnectivityManager connectivityManager = (ConnectivityManager) dashboard.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiConn == null || !wifiConn.isConnected()) && (mobileConn == null || !mobileConn.isConnected());
    }

    @Override
    public void onBackPressed() {
        Dialog dialog = new Dialog(this);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations= android.R.style.Animation_Dialog;
        dialog.setContentView(R.layout.exit_dialog);
        dialog.show();
        ImageButton yes_btn=dialog.findViewById(R.id.btn_yes);
        ImageButton no_btn=dialog.findViewById(R.id.btn_no);

        yes_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
        no_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }
}