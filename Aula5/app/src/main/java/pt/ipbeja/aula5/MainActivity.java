package pt.ipbeja.aula5;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pt.ipbeja.aula5.data.ChatDatabase;
import pt.ipbeja.aula5.data.Contact;
import pt.ipbeja.aula5.data.Message;

public class MainActivity extends AppCompatActivity {


    private ContactAdapter contactAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView contactList = findViewById(R.id.contact_list);

        contactAdapter = new ContactAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(this);

        contactList.setAdapter(contactAdapter);
        contactList.setLayoutManager(llm);


    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Contact> contacts = ChatDatabase.getInstance(this).contactDao().getAllContacts();
        contactAdapter.setData(contacts);

    }


    private void startChatActivity(Contact contact) {
        ChatActivity.start(this, contact.getId());
    }

    public void createContact(View view) {
        CreateContactActivity.start(this);
    }



    class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Contact contact;

        TextView name;


        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            name = itemView.findViewById(R.id.contact_name);

        }

        public void bind(Contact contact) {
            this.contact = contact;
            name.setText(contact.getName());
        }

        @Override
        public void onClick(View view) {
            startChatActivity(contact);
        }

    }


    class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {

        private List<Contact> data = new ArrayList<>();

        public void setData(List<Contact> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_list_item, viewGroup, false);
            return new ContactViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, int i) {
            Contact contact = data.get(i);
            contactViewHolder.bind(contact);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

}
