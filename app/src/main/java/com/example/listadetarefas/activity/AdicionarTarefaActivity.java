package com.example.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.listadetarefas.R;
import com.example.listadetarefas.helper.TarefaDAO;
import com.example.listadetarefas.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editTarefa = findViewById(R.id.editTarefa);

        //recuperar tarefa, caso seja edição
        tarefaSelecionada = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        if (tarefaSelecionada != null){
            editTarefa.setText(tarefaSelecionada.getNomeTarefa());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemAdicionar:
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                if (tarefaSelecionada != null){

                    if (!editTarefa.getText().toString().isEmpty()){

                        tarefaSelecionada.setNomeTarefa(editTarefa.getText().toString());
                        tarefaDAO.atualizar(tarefaSelecionada);

                        finish();
                    }

                } else {
                    if (!editTarefa.getText().toString().isEmpty()) {
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(editTarefa.getText().toString());

                        tarefaDAO.salvar(tarefa);
                        finish();
                    }
                }

                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
