package nz.co.hamishcundy.zomatoapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import nz.co.hamishcundy.zomatoapp.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @BindView(R.id.content_layout)
    FrameLayout contentLayout;

    private Fragment fragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Butterknife bind views
        ButterKnife.bind(this);

        //Realm initialisation
        Realm.init(this);

        //Setup navigation
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        switchFragment(R.id.navigation_restaurants);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switchFragment(item.getItemId());

            return true;
        }
    };

    private void switchFragment(int itemId) {
        switch (itemId) {
            case R.id.navigation_restaurants:
                fragment = new RestaurantListFragment();
                loadFragment(fragment);
            case R.id.navigation_favourites:
                fragment = new RestaurantFavouriteFragment();
                loadFragment(fragment);

        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
