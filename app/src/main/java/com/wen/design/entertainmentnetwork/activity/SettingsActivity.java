package com.wen.design.entertainmentnetwork.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.wen.design.entertainmentnetwork.R;

/**
 * Created by wen on 2018/3/17.
 */

public class SettingsActivity  extends AppCompatActivity {

    private String TAG="SettingsActivity";
    private SharedPreferences themeSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeSp=getSharedPreferences("theme",MODE_PRIVATE);
        String themeName=themeSp.getString("theme","1");
        if(themeName.equals("知乎蓝")){
            setTheme(R.style.AppTheme);
        }
        else if(themeName.equals("水鸭绿")){
            setTheme(R.style.AppTheme_Teal);
        }
        else if(themeName.equals("哔哩粉")){
            setTheme(R.style.AppTheme_Pink);
        }
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(R.id.content,new PrefsFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
               return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PrefsFragment extends PreferenceFragment {
        private ListPreference listChangeTheme;
        private String TAG="SettingsActivity";
        private SharedPreferences themeSp;
        private SharedPreferences.Editor editor;
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            listChangeTheme=(ListPreference)findPreference("theme_type_number");
            listChangeTheme.setSummary(listChangeTheme.getEntry());

            listChangeTheme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                public ListPreference listPreference;

                @SuppressLint("ApplySharedPref")
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    if(preference instanceof ListPreference){
                        //把preference这个Preference强制转化为ListPreference类型
                        listPreference=(ListPreference)preference;
                        //获取ListPreference中的实体内容
                        CharSequence[] entries=listPreference.getEntries();
                        //获取ListPreference中的实体内容的下标值
                        int index=listPreference.findIndexOfValue((String)o);
                        //把listPreference中的摘要显示为当前ListPreference的实体内容中选择的那个项目
                        themeSp=getActivity().getSharedPreferences("theme",MODE_PRIVATE);
                        editor=themeSp.edit();
                        editor.putString("theme",String.valueOf(entries[index]) );
                        editor.commit();
                        //Toast.makeText(getActivity(),String.valueOf(entries[index]),Toast.LENGTH_LONG).show();
                        Intent intent = getActivity().getIntent();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();
                        return true;

                    }
                    return true;
                }});

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK && event.getRepeatCount()==0){
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }
}
