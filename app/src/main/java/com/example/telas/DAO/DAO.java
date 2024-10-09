package com.example.telas.DAO;

import android.database.sqlite.SQLiteOpenHelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.telas.OBJETOS.Usuario;
public class DAO extends SQLiteOpenHelper {

    public DAO(Context context) {
        super(context, "banco_pain", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_usuarios = "CREATE TABLE usuarios (id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome_usuario TEXT UNIQUE NOT NULL," +
                "senha_usuario TEXT NOT NULL);";
        db.execSQL(sql_usuarios);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql_upgrade_usuarios = "DROP TABLE IF EXISTS usuarios;";
        db.execSQL(sql_upgrade_usuarios);
        onCreate(db);
    }
    private void autenticaUsuario(String nome, String senha){

    }
    public String insereUsuario(Usuario usuario){
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues dados_usuario = new ContentValues();
            dados_usuario.put("nome_usuario", usuario.getNome());
            dados_usuario.put("senha_usuario", usuario.getSenha());
            db.insertOrThrow("usuarios", null, dados_usuario);
            db.close();
        } catch (SQLiteConstraintException erro) {
            return "Erro ao cadastrar o usuário";
        }

        return "Sucesso ao cadastrar o usuário";
    }


    @SuppressLint("Range")
    public String autenticaUsuario(Usuario usuario){
        SQLiteDatabase db = getReadableDatabase();
        String sqli_busca_usuarios = "SELECT * FROM usuarios WHERE nome_usuario = " + "'" + usuario.getNome() + "'";
        Cursor c = db.rawQuery(sqli_busca_usuarios, null);
        while (c.moveToNext()){
            Log.d("SenhaNoBanco ", c.getString(c.getColumnIndex("senha_usuario")));
            Log.d("SenhaDigitada ", usuario.getSenha());
            if(usuario.getNome().equals(c.getString(c.getColumnIndex("nome_usuario")))){
                if (usuario.getSenha().equals(c.getString(c.getColumnIndex("senha_usuario")))){
                    return "login efetuado com sucesso.";
                }
            }
        }
        db.close();
        c.close();
        return "login falhou";
    }

}
