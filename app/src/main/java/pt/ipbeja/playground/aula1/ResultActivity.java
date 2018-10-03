package pt.ipbeja.playground.aula1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import pt.ipbeja.playground.R;

public class ResultActivity extends AppCompatActivity {

    public static String TEXT_EXTRA = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultView = findViewById(R.id.result);

        // Para ir buscar a String que adicionámos como extra, acedemos ao Intent
        String result = getIntent().getStringExtra(TEXT_EXTRA);

        resultView.setText(result);
    }

    public static void start(Context context, String text) {
        // Para iniciar uma nova Activity, temos de criar um Intent que indica ao SO o Context (de onde parte este pedido) e qual a Activity a iniciar
        Intent starter = new Intent(context, ResultActivity.class);

        // Para passar dados à nova Activity, adicionamos extras ao Intent.
        starter.putExtra(TEXT_EXTRA, text);

        // Por fim, chamamos o método startActivity com o Intent como argumento.
        context.startActivity(starter);
    }
}
