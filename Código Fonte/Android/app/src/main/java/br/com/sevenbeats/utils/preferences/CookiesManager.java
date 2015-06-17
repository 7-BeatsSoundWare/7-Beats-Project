package br.com.sevenbeats.utils.preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by diogojayme on 6/15/15.
 */
public class CookiesManager {

    public static CookiesManager cookies;
    private  SharedPreferences.Editor editor;

    private  SharedPreferences preferences;
    public CookiesManager(){}

    public static CookiesManager getInstance(){
        return cookies == null ? new CookiesManager() : cookies;
    }

    public CookiesManager getPreferences(Context context){
        preferences = context.getSharedPreferences(PreferencesConstants.SevenBeatsPreferences, Context.MODE_PRIVATE);
        return this;
    }
    /**
     * Use edit para quando o usuário
     * for salvar algum dado
     *
     * */
    @SuppressLint("CommitPrefEdits")
    public CookiesManager edit(){
        editor = preferences.edit();
        return this;
    }

    public void setCurrentAuthUser(String user){
        if(editor == null){
            throw new NullPointerException("Editor == null Você deve instanciar o Editor de preferencias antes de usa-lo");
        }

        editor.putString("user", user);
        editor.commit();
    }

    public String getCurrentAuthUser(){
       return preferences.getString("user", null);
    }


}
