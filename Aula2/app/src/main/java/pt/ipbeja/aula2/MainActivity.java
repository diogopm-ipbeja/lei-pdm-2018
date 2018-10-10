package pt.ipbeja.aula2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    // Request codes para os startActivityForResult
    public static final int REQUEST_PHOTO = 1000;
    public static final int REQUEST_FORM = 1001;
    // --------------------------------------------

    private ImageView photo;
    private TextView fullName;
    private TextView ageAndGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(TAG + " onCreate");

        this.photo = findViewById(R.id.photo);
        this.fullName = findViewById(R.id.full_name);
        this.ageAndGender = findViewById(R.id.age_gender);

    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println(TAG + " onStart");
        // Activity já está visível para o utilizador mas ainda não está interactiva

    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println(TAG + " onResume");
        // A partir daqui a Activity está a correr. O utilizador já consegue interagir com as Views
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println(TAG + " onPause");
        // A Activity ainda está visível mas o utilizador já não consegue interagir com as Views
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println(TAG + " onStop");
        // Neste momento a Activity já não está visível
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println(TAG + " onDestroy");
        // Último passo no ciclo de vida da Activity. Não é garantido que este callback seja invocado.
    }

    public void takePhoto(View view) {
        // Método de callback do botão de tirar a foto (ver XML)

        // Intent IMPLÍCITO. Apenas definimos a acção que queremos tomar e delegamos para o
        // sistema operativo a responsabilidade de lançar a aplicação que dá resposta ao pedido

        // MediaStore.ACTION_IMAGE_CAPTURE é uma constante (String) que servirá para que o Android
        // encontre aplicações que tenham a capacidade de lidar com a acção de tirar uma foto.
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Quando esperamos um resultado, lançamos a Activity com o método abaixo
        // Temos de definir um 'request code' que não é mais que um identificador do nosso pedido.
        // Ver onActivityResult(int,int,Intent) abaixo
        startActivityForResult(intent, REQUEST_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Quando lançamos uma Activity com o startActivityForResult, quando a Activity é encerrada
        // este método é invocado com o requestCode definido, um resultCode (RESULT_OK,
        // RESULT_CANCELED) e um Intent que contém os dados que a Activity devolveu.


        // Filtramos pelo requestCode (pode ser com if-else ou switch-case)
        if(requestCode == REQUEST_PHOTO) {

            // Convém também tratar o resultCode que nos diz se o resultado da operação correu bem
            // (RESULT_OK) ou terá sido cancelado (RESULT_CANCELED)
            if(resultCode == RESULT_OK) {
                System.out.println("Tirou foto");

                // O thumbnail da foto tirada está guardado com a chave "data"
                Bitmap thumbnail = data.getParcelableExtra("data");

                // Colocamos o bitmap na ImageView
                this.photo.setImageBitmap(thumbnail);
            }
            else {
                // Caso o utilizador tenha saído da app da câmera sem tirar uma foto
                Toast.makeText(this, "Utilizador não tirou fotografia.", Toast.LENGTH_SHORT).show();
                System.out.println("Cancelou");
            }
        }
        else if (requestCode == REQUEST_FORM) {

            if(resultCode == RESULT_OK) {

                // Para retirar a informação do intent usamos os métodos get____Extra
                String firstName = data.getStringExtra(FormActivity.FORM_FIRST_NAME);
                String lastName = data.getStringExtra(FormActivity.FORM_LAST_NAME);

                // Alguns métodos precisam de um valor por defeito (para o caso de a chave não existir)
                int age = data.getIntExtra(FormActivity.FORM_AGE,0);

                String gender = data.getStringExtra(FormActivity.FORM_GENDER);

                // Colocamos o resultado nas TextViews (ver método String.format na documentação Java)
                fullName.setText(String.format("%s %s", firstName, lastName));
                ageAndGender.setText(String.format("%d | %s", age, gender));

            }

        }

    }

    public void fillForm(View view) {
        // Método de callback do botão para lançar a Activity com o formulário (ver XML)

        // Ter em atenção que o requestCode é único para cada acção
        FormActivity.startForResult(this, REQUEST_FORM);

        // A chamada do metódo static acima é equivalente a esta:
        //Intent intent = new Intent(this, FormActivity.class);
        //startActivityForResult(intent, REQUEST_FORM);

    }
}
