package pt.ipbeja.aula5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pt.ipbeja.aula5.data.ChatDatabase;
import pt.ipbeja.aula5.data.Contact;

public class CreateContactActivity extends AppCompatActivity {

    private EditText contactNameInput;


    public static void start(Context context) {
        Intent starter = new Intent(context, CreateContactActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);



        contactNameInput = findViewById(R.id.contact_name_input);
    }

    public void createContact(View view) {

        String name = contactNameInput.getText().toString();
        if(!name.isEmpty()) {
            ChatDatabase.getInstance(this).contactDao().insert(new Contact(0, name));
            finish();
        }
        else {
            Snackbar.make(findViewById(android.R.id.content), R.string.create_contact_empty_name_alert, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "Name field is empty.", Toast.LENGTH_SHORT).show();
        }



    }
}
