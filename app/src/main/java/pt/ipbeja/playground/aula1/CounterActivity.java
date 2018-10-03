package pt.ipbeja.playground.aula1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import pt.ipbeja.playground.R;

public class CounterActivity extends AppCompatActivity {

    private TextView counterLabel;
    private EditText nameInput;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);



        // Só após a chamada a setContentView as Views estão disponíveis
        this.counterLabel = findViewById(R.id.counter); // Usamos os ids definidos no layout (xml) para aceder às Views
        this.nameInput = findViewById(R.id.name);

        /*
        Button incBtn = findViewById(R.id.increment);
        incBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter--;
                CounterActivity.this.counterLabel.setText(String.valueOf(counter));
            }
        });
        */

    }

    // Este método handler de um click é referenciado no XML do layout (ver atributo android:onClick nos Buttons)
    // Uma alternativa é criar um OnClickListener e atribui-lo ao botão (ver exemplo no código comentado no método acima onCreate)
    public void onCounterButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.decrement:
                counter--;
                break;
            case R.id.increment:
                counter++;
                break;
        }
        // Atenção aqui. Apesar de se poder passar um int, este int tem de ser o identificador de um recurso
        // Neste caso apenas convertemos o int para uma String
        this.counterLabel.setText(String.valueOf(counter));


    }

    public void onResultButtonClicked(View view) {
        String name = this.nameInput.getText().toString();
        String text = name + " " + counter;
        ResultActivity.start(this, text);

        //Ver alternativa em ResultActivity
        //Intent starter = new Intent(this, ResultActivity.class);
        //starter.putExtra(ResultActivity.TEXT_EXTRA, text);
        //startActivity(starter);

    }
}
