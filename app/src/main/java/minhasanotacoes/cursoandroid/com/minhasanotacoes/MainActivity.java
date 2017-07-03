package minhasanotacoes.cursoandroid.com.minhasanotacoes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText texto;
    private ImageView botaoSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = (EditText) findViewById(R.id.textoId);
        botaoSalvar = (ImageView) findViewById(R.id.botaoSalvarId);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoDigitado = texto.getText().toString();
                gravarNoArquivo(textoDigitado);
            }
        });

        //Recuperar o que foi gravado
        if (lerArquivo() != null){
            texto.setText(lerArquivo());
        }
    }
    //função para gravar no armazenamento interno do celular
    private void gravarNoArquivo(String texto){
        try{
            //criar o arquivo anotacao.txt e o modo private para só minha aplicação poder ler o arquivo
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("anotacao.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(texto);
            outputStreamWriter.close();

            Toast.makeText(MainActivity.this, "Anotação salva", Toast.LENGTH_SHORT).show();

        }catch(IOException e){
            //sinalizando o erro
            Log.v("MainActivity", e.toString());
        }
    }

    private String lerArquivo(){
        String resultado = "";

        try{
            //Abrir o arquivo
            InputStream arquivo = openFileInput("anotacao.txt");

            //testar se o arquivo existe
            if (arquivo != null){

                //ler o arquivo
                InputStreamReader inputStreamReader = new InputStreamReader(arquivo);

                //gerar buffer do arquivo lido
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                //Recuperar textos do arquivo
                String linhaArquivo = "";

                while ((linhaArquivo = bufferedReader.readLine()) != null){

                    //se tiver vazia, nao pular linha
                    if (resultado.equals("")){
                        resultado += linhaArquivo;
                    }
                    else{
                        //se nao for vazio, pula linha ntes de inserir nova linha
                        resultado = resultado + "\n" + linhaArquivo;
                    }
                }

                arquivo.close();
            }

        }catch (IOException e){
            Log.v("MainActivity", e.toString());
        }

        return resultado;
    }
}
