package pt.ipbeja.aula2;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

public class FormActivity extends AppCompatActivity {

    public static final String FORM_FIRST_NAME = "firstName";
    public static final String FORM_LAST_NAME = "lastName";
    public static final String FORM_AGE = "age";
    public static final String FORM_GENDER = "gender";

    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private NumberPicker agePicker;

    private RadioGroup genderRadioGroup;

    private String selectedGender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        // Inicialização das variáveis das Views
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        agePicker = findViewById(R.id.age_picker);

        this.genderRadioGroup = findViewById(R.id.gender_group);

        //---------------------------------------

        setupAgePicker();

        setupGenderRadioGroup();
    }

    private void setupGenderRadioGroup() {

        // Uma forma alternativa à de atribuir um onClickListener a cada RadioButton é atribuir um
        // OnCheckedChangeListener ao RadioGroup
        this.genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                // No método handler deste Listener recebemos o Radiogroup e o id do RadioButton
                // que foi seleccionado
                System.out.printf("RadioButton selected id: %d\n", id);
            }
        });

    }

    private void setupAgePicker() {
        this.agePicker.setMinValue(0);
        this.agePicker.setMaxValue(100);

        // Se precisarmos de ser notificados cada vez que o valor no NumberPicker muda, podemos
        // utilizar um OnValueChangeListener:
        this.agePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int previous, int current) {
                System.out.format("NumberPicker value changed from %d to %d\n", previous, current);
            }
        });
    }

    public void onGenderSelected(View view) {
        // Método de callback dos RadioButtons no formulário (ver XML)
        // Ver forma alternativa no método setupGenderRadioGroup()

        switch (view.getId()) {

            case R.id.male:
                this.selectedGender = "M";
                break;

            case R.id.female:
                this.selectedGender = "F";
                break;

        }

    }

    public void submitForm(View view) {
        // Método de callback do botão de submissão do formulário (ver XML)

        // Vamos buscar os valores dos campos de input do utilizador
        String first = firstName.getText().toString();
        String last = lastName.getText().toString();
        int age = agePicker.getValue();

        // Criamos um Intent e incluímos os valores na forma de pares Chave-Valor com os métodos
        // putExtra. Para aceder aos valores são estas as chaves que utilizamos
        // (ver onActivityResult na MainActivity)
        Intent intent = new Intent();
        intent.putExtra(FORM_FIRST_NAME, first);
        intent.putExtra(FORM_LAST_NAME, last);
        intent.putExtra(FORM_AGE, age);
        intent.putExtra(FORM_GENDER, this.selectedGender);

        // Guardamos o Intent com o resultCode (constante definida nas Activities)
        setResult(RESULT_OK, intent);

        // Chamada ao método finish() para terminar esta Activity e voltar à anterior
        finish();

    }

    public static void start(Context context) {
        Intent starter = new Intent(context, FormActivity.class);
        context.startActivity(starter);
    }

    public static void startForResult(AppCompatActivity context, int requestCode) {
        Intent starter = new Intent(context, FormActivity.class);
        context.startActivityForResult(starter, requestCode);
    }
}
