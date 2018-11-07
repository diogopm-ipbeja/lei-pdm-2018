package pt.ipbeja.aula5;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pt.ipbeja.aula5.data.ChatDatabase;
import pt.ipbeja.aula5.data.Message;

public class ChatActivity extends AppCompatActivity {

    public static final String CONTACT_ID = "contactId";


    private long contactId;

    private RecyclerView messageList;
    private MessageAdapter messageAdapter;

    private EditText messageInput;


    public static void start(Context context, long contactId) {
        Intent starter = new Intent(context, ChatActivity.class);
        starter.putExtra(CONTACT_ID, contactId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        contactId = getIntent().getLongExtra(CONTACT_ID, 0);

        if(contactId == 0) {
            finish();
            return;
        }

        messageInput = findViewById(R.id.message_text_input);
        messageList = findViewById(R.id.message_list);

        messageAdapter = new MessageAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        messageList.setAdapter(messageAdapter);
        messageList.setLayoutManager(llm);



    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Message> messages = ChatDatabase.getInstance(this).messageDao().getMessagesForContact(contactId);

        messageAdapter.setData(messages);

    }





    public void sendMessage(View view) {
        String text = messageInput.getText().toString();

        if(!text.isEmpty()) {
            Message message = new Message(0, contactId, text);
            long messageId = ChatDatabase.getInstance(this).messageDao().insert(message);
            message.setId(messageId);
            messageAdapter.addMessage(message);
            messageInput.setText("");
        }
    }


    class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.message_text);
        }

        public void bind(Message message) {
            this.text.setText(message.getText());
        }
    }


    class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

        private List<Message> data = new ArrayList<>();


        public void addMessage(Message message) {
            this.data.add(message);
            notifyItemInserted(data.size()-1);
        }

        public void setData(List<Message> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_item, viewGroup, false);

            return new MessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i) {
            Message message = data.get(i);
            messageViewHolder.bind(message);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


    }



}
