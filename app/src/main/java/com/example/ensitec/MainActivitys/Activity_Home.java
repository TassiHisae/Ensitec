package com.example.ensitec.MainActivitys;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.example.ensitec.BackgroundTask.Background_Task_Noticias;
import com.example.ensitec.Class_Fragment.Class_Fragment_Chat;
import com.example.ensitec.Class_Fragment.Class_Fragment_Home;
import com.example.ensitec.Class_Fragment.Class_Fragment_Noticias;
import com.example.ensitec.Class_Fragment.Class_Fragment_Reset_Pass;
import com.example.ensitec.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import androidx.annotation.RestrictTo;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;

public class Activity_Home extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    BottomNavigationItemView btn_noticias, btn_Home, btn_chat;

    private WebView webView_noticia;

    ArrayAdapter adp_text;
    ArrayList arrayList_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        final ListView listView_msg = (ListView) findViewById(R.id.ListView_chat);
        EditText editText_text = (EditText) findViewById((R.id.edt_msg));

        mAppBarConfiguration = new AppBarConfiguration.Builder(

                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();

        btn_noticias = findViewById(R.id.nav_noticias);

        btn_noticias.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                ft.replace(R.id.nav_host_fragment, new Class_Fragment_Noticias());
                ft.commitNow();
                webView_noticia = (WebView) findViewById(R.id.webView_Home);
                WebSettings webSettings = webView_noticia.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webView_noticia.setWebViewClient(new WebViewClient());
                webView_noticia.loadUrl("https://www.cps.sp.gov.br/noticias/");
                ListView listView_msg = (ListView) findViewById(R.id.ListView_chat);
                EditText editText_text = (EditText) findViewById((R.id.edt_msg));
            }

        });
        btn_Home = findViewById(R.id.nav_home);

        btn_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                ft.replace(R.id.nav_host_fragment, new Class_Fragment_Home());
                ft.commit();
            }

        });

        btn_chat = findViewById(R.id.nav_chat);
        btn_chat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                ft.replace(R.id.nav_host_fragment, new Class_Fragment_Chat());
                ft.commit();
                String teste = "Teste";
                arrayList_text.add(teste.toString());
         //       adp_text = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList_text);
                listView_msg.setAdapter(adp_text);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void Call_Fragment_Noticias(View v) {
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
//        ft.replace(R.id.nav_host_fragment, new Class_Fragment_Noticias());
//        ft.commit();
//        System.out.println("teste");
    }
//    public ArrayAdapter Send_msg(View v)
//    {
//        ListView listView_msg = (ListView) findViewById(R.id.ListView_chat);
//        EditText editText_text = (EditText) findViewById((R.id.edt_msg));
//
//
//
//    }
}
