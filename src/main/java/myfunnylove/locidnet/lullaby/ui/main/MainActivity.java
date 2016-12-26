package myfunnylove.locidnet.lullaby.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import myfunnylove.locidnet.lullaby.M;
import myfunnylove.locidnet.lullaby.R;
import myfunnylove.locidnet.lullaby.data.model.PlayList;
import myfunnylove.locidnet.lullaby.data.source.AppRepository;
import myfunnylove.locidnet.lullaby.ui.base.BaseActivity;
import myfunnylove.locidnet.lullaby.ui.base.BaseFragment;
import myfunnylove.locidnet.lullaby.ui.local.LocalFilesFragment;

import myfunnylove.locidnet.lullaby.ui.local.all.LocalMusicPresenter;
import myfunnylove.locidnet.lullaby.ui.music.MusicPlayerFragment;
import myfunnylove.locidnet.lullaby.ui.playlist.PlayListFragment;
import myfunnylove.locidnet.lullaby.ui.settings.SettingsFragment;

public class MainActivity extends BaseActivity {

    static final int DEFAULT_PAGE_INDEX = 2;

//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
//    @BindViews({R.id.radio_button_play_list, R.id.radio_button_music, R.id.radio_button_local_files})
//    List<RadioButton> radioButtons;

    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    String[] mTitles;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private int MY_PERMISSIONS_REQUEST_READ_MEDIA = 1;
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getVersion();
      //  setSupportActionBar(toolbar);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7807956605870387/2744032353");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        // Main Controls' Titles
        mTitles = getResources().getStringArray(R.array.mp_main_titles);

        // Fragments
        BaseFragment[] fragments = new BaseFragment[mTitles.length];
        fragments[0] = new LocalFilesFragment();
        fragments[1] = new MusicPlayerFragment();
        fragments[2] = new PlayListFragment();
        fragments[3] = new SettingsFragment();

        // Inflate ViewPager
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), mTitles, fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount() - 1);
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.mp_margin_large));

        tabLayout.setupWithViewPager(viewPager);
        try{
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_main_nav_local_files_selected);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_main_nav_music_selected);
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_main_nav_play_list_selected);
            tabLayout.getTabAt(3).setIcon(R.drawable.ic_main_nav_settings_selected);
        }catch (Exception ignored){}
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                // Empty
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                // Empty
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                radioButtons.get(position).setChecked(true);
//            }
//        });
//
//        radioButtons.get(DEFAULT_PAGE_INDEX).setChecked(true);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "mainActivity");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "thumbnail");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    private void getVersion() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("versionCode");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                String value  = dataSnapshot.getValue(String.class);
                try {
                    /*FROM FIREBAE GET VERSION CODE*/
                    final int newVersion = dataSnapshot.getValue(Integer.class);

                    /*CURRENT APPLICATION VERSION CODE*/
                    final int appVersionCode = M.getInstance().getApplicationContext().getPackageManager().getPackageInfo(M.getInstance().getApplicationContext().getPackageName(),0).versionCode;

                    if (appVersionCode < newVersion) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                                .setMessage(getResources().getString(R.string.msg_version))
                                .setCancelable(true)

                                .setPositiveButton(getResources().getString(R.string.ok_version), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                        try {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                        } catch (android.content.ActivityNotFoundException anfe) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                        }
                                        dialogInterface.dismiss();
                                    }
                                });


                        builder.show();
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("GET VERSION","error"+databaseError.toString());

            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

//    @OnCheckedChanged({R.id.radio_button_play_list, R.id.radio_button_music, R.id.radio_button_local_files})
//    public void OnCheckedChanged(RadioButton button,boolean isChecked ) {
//        if (isChecked) {
//            onItemChecked(radioButtons.indexOf(button));
//        }
//    }

    private void onItemChecked(int position) {
        viewPager.setCurrentItem(position);
       // toolbar.setTitle(mTitles[position]);
    }





}
