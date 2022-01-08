package com.cgs.anfotrix.Activities;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cgs.anfotrix.R;
import com.cgs.anfotrix.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide actionbar and statusBar
//        getSupportActionBar().hide();
        getSupportActionBar().setTitle("Home");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        msg();
//        custommsg();
    }

    private void msg() {


    }


    private void custommsg() {

        Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.dialog_msg);
//        CircleImageView principalimg = dialog.findViewById(R.id.principalimg);
//        TextView mesage = dialog.findViewById(R.id.mesage);
//        FrameLayout frameLayout = dialog.findViewById(R.id.fragmentonboarding);

        FragmentManager fragmentManager = getSupportFragmentManager();
        PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataForonBoarding());

//        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentonboarding, paperOnboardingFragment).commit();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragmentonboarding, paperOnboardingFragment);
        fragmentTransaction.commit();

        dialog.show();
    }

    private ArrayList<PaperOnboardingPage> getDataForonBoarding() {

        PaperOnboardingPage paperOnboardingPage = new PaperOnboardingPage("Director Message", "Hello Eceryone",
                Color.parseColor("#ffb174"), R.drawable.ic_baseline_cancel_presentation_24, R.drawable.ic_baseline_file_upload_24);

        ArrayList<PaperOnboardingPage> pageArrayList = new ArrayList<>();
        pageArrayList.add(paperOnboardingPage);
        return pageArrayList;
    }

}