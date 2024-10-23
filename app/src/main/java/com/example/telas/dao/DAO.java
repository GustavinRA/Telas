package com.example.telas.dao;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.ContentValues;
import android.content.Context;

import com.example.telas.model.Usuario;

public class DAO extends SQLiteOpenHelper {

    public DAO(Context context) {
        super(context, "banco_pain", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_usuarios = "CREATE TABLE usuarios (id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email_usuario TEXT UNIQUE NOT NULL," +
                "senha_usuario TEXT NOT NULL);";
        db.execSQL(sql_usuarios);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql_upgrade_usuarios = "DROP TABLE IF EXISTS usuarios;";
        db.execSQL(sql_upgrade_usuarios);
        onCreate(db);
    }

    public String insereUsuario(Usuario usuario){
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues dados_usuario = new ContentValues();
            dados_usuario.put("email_usuario", usuario.getEmail());
            dados_usuario.put("senha_usuario", usuario.getSenha());
            db.insertOrThrow("usuarios", null, dados_usuario);
        } catch (SQLiteConstraintException erro) {
            // Usuário já existe, podemos considerar isso como sucesso
            return "Usuário já existente";
        } catch (Exception e) {
            return "Erro ao cadastrar o usuário: " + e.getMessage();
        } finally {
            db.close();
        }
        return "Sucesso ao cadastrar o usuário";
    }

    public String autenticaUsuario(Usuario usuario){
        SQLiteDatabase db = getReadableDatabase();
        String sqli_busca_usuarios = "SELECT * FROM usuarios WHERE email_usuario = ? AND senha_usuario = ?";
        Cursor c = db.rawQuery(sqli_busca_usuarios, new String[]{usuario.getEmail(), usuario.getSenha()});
        if (c.moveToFirst()){
            c.close();
            db.close();
            return "login efetuado com sucesso.";
        } else {
            c.close();
            db.close();
            return "login falhou";
        }
    }
}