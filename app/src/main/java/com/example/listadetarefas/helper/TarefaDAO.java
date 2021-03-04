package com.example.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public TarefaDAO(Context context){
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try{
            escreve.insert(DbHelper.TABELA_TAREFAS, null, cv);
            Log.i("Feedback", "Sucesso ao salvar tarefa");

        } catch (Exception e){
            Log.e("Feedback", "Erro ao salvar tarefa " + e.getMessage());
            return false;
        }


        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try{
            String[] args = {tarefa.getId().toString()};
            escreve.update(DbHelper.TABELA_TAREFAS, cv, "id=?", args);
            Log.i("Feedback", "Sucesso ao atualizar tarefa");

        } catch (Exception e){
            Log.e("Feedback", "Erro ao atualizar tarefa " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try{
            String[] args = {tarefa.getId().toString()};
            escreve.delete(DbHelper.TABELA_TAREFAS, "id=?", args);
            Log.i("Feedback", "Sucesso ao atualizar tarefa");

        } catch (Exception e){
            Log.e("Feedback", "Erro ao atualizar tarefa " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar() {

        List<Tarefa> lista = new ArrayList<>();

        String consulta = "SELECT * FROM " + DbHelper.TABELA_TAREFAS + " ;";
        Cursor cursor = le.rawQuery(consulta, null);

        while ( cursor.moveToNext() ){

            Tarefa tarefa = new Tarefa();

            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String nome = cursor.getString(cursor.getColumnIndex("nome"));

            tarefa.setId(id);
            tarefa.setNomeTarefa(nome);

            lista.add(tarefa);

        }


        return lista;
    }
}
