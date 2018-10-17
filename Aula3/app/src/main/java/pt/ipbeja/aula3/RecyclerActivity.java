package pt.ipbeja.aula3;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactAdapter adapter;

    private EditText gridColsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        // Inicialização das Views
        this.gridColsEditText = findViewById(R.id.grid_cols);
        this.recyclerView = findViewById(R.id.list);

        setupRecyclerView();


    }

    /**
     * Método que faz a configuração inicial da RecyclerView
     */
    private void setupRecyclerView() {
        // Criar a instânca do Adapter e passamos logo uma fonte de dados
        this.adapter = new ContactAdapter(ContactDataSource.getContacts(2000));

        // Agora podemos atribuir o LayoutManager e Adapter à RecyclerView
        this.recyclerView.setAdapter(this.adapter);
        setLinearLayout();
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, RecyclerActivity.class);
        context.startActivity(starter);
    }

    /**
     * Click handler do botão de LinearLayout
     * @param view
     */
    public void onLinearButtonClicked(View view) {
        setLinearLayout();
    }

    /*
     * Click handler do botão de GridLayout.
     * @param view
     */
    public void onGridButtonClicked(View view) {
        // Primeiro vamos ver que valor o utilizador colocou na EditText
        int cols = Integer.parseInt(gridColsEditText.getText().toString());
        setGridLayout(cols);
    }

    /**
     * Cria uma instância de um LinearLayoutManager e atribui este à RecyclerView
     */
    private void setLinearLayout() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this); // Ver o 2º constructor (reverseLayout, orientation) e experimentar
        this.recyclerView.setLayoutManager(layoutManager);
    }


    /**
     * Cria uma instância de um GridLayoutManager e atribui este à RecyclerView
     * @param cols O número de colunas que a grelha vai ter (>0)
     */
    private void setGridLayout(int cols) {
        if(cols < 1) {
            cols = 1;
            Toast.makeText(this, "Must have at least 1 column.", Toast.LENGTH_SHORT).show();
        }
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, cols));
    }


    /**
     * Este ViewHolder vai guardar a View criada pelo Adapter e tem também aqui a responsabilidade
     * de vincular os dados às suas TextViews.
     * Atenção ao processo de reciclagem destes ViewHolders.
     */
    private class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView email;

        /**
         * Construtor que recebe a View
         * @param itemView View do item da lista (neste caso a View gerada a partir do layout contact_item.xml)
         */
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            // Tal como nas Activities, temos de encontrar as Views que queremos ler ou modificar
            // Mas ao contrário das Activities, temos de nos referir à View (itemView) para encontrar as sub-Views
            this.name = itemView.findViewById(R.id.name);
            this.email = itemView.findViewById(R.id.email);
        }

        /**
         * Método que será invocado no corpo do método onBindViewHolder do Adapter para preencher
         * as Views deste item
         * @param contact O item de dados que vamos usar para preencher as TextViews
         */
        public void bind(ContactDataSource.Contact contact) {
            this.name.setText(contact.getName());
            this.email.setText(contact.getEmail());
        }
    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {

        // O adapter precisa de uma fonte de dados para gerar e preencher os ViewHolders
        private List<ContactDataSource.Contact> data;

        public ContactAdapter() {}

        // Um construtor que recebe logo uma lista de Contact
        public ContactAdapter(List<ContactDataSource.Contact> data) {
            this.data = data;
        }

        // Um método alternativo para guardar os dados no Adapter
        public void setData(List<ContactDataSource.Contact> data) {
            this.data = data;
        }


        // Campo só para demonstração da criação dos ViewHolders (abaixo). Puramente para debug.
        private int viewHolderCounter = 0;

        // Método de criação de ViewHolders. Aqui apenas criamos o ViewHolder. Não o manipulamos!
        @NonNull
        @Override
        public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

            // Apesar da lista ter milhares de elementos, veja quanto ViewHolders são de facto criados.
            System.out.println("Viewholders created = " + (++viewHolderCounter));

            // Criar a View a partir do ficheiro XML.
            // Tipicamente é feito desta forma, apenas sendo necessário alterar o layout a interpretar (R.layout.****)
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.contact_item, viewGroup,
                    false);
            // Agora que temos uma View, podemos criar um ViewHolder com esta como argumento.
            return new ContactViewHolder(view);
        }

        // Neste método entra um viewholder (pode ser novo ou reciclado) e a posição na lista que este vai ocupar
        // É neste método onde devemos manipular o ViewHolder. Neste momento podemos colocar as Strings
        // nas suas TextViews. Neste caso estamos a delegar essa função para o ViewHolder (método bind(contact))
        @Override
        public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, int position) {
            // Com a position, podemos ir à nossa fonte de dados recolher o elemento nessa posição.
            ContactDataSource.Contact contact = this.data.get(position);
            // Depois podemos invocar o método bind e passar-lhe esse elemento como argumento (ver implementação de bind)
            contactViewHolder.bind(contact);
        }

        // Cada vez que exista alguma alteração à lista, o Adapter precisa de saber quantos elementos
        // terá de exibir. Tipicamente apenas precisamos de passar o tamanho da fonte de dados.
        @Override
        public int getItemCount() {
            return this.data.size();
        }

    }

}
