# Chat App

## Activities
### MainActivity - Lista de contactos

Lista todos os contactos existentes na BD.
Ao pressionar um dos itens da lista deve ser lançada a `ChatActivity`. Deve ser colocado nos extras do `Intent` o ID do contacto.

- RecyclerView
    - ContactAdapter
    - ContactViewHolder
        - O layout desta View deve conter uma ImageView com um ícone e uma TextView com o nome do contacto.
        - Long press para apagar o contacto (AlertDialog para confirmação da acção)


- Botão (FloatingActionButton) para lançar a CreateContactActivity

- Menu
    - Menu item para apagar todos os contactos da lista e respectivas mensagens (AlertDialog para confirmação da acção)

### CreateContactActivity
Formulário que permita criar um contacto e adicioná-lo à BD. Deve ter pelo menos um EditText para o utilizador colocar o nome do contacto e um botão para confirmar a acção. Deve validar o input (não deixar que se insira um contacto com o EditText vazio).

### ChatActivity - Lista de mensagens de um contacto
- RecyclerView
    - MessageAdapter
    - MessageViewHolder


- EditText para escrever uma mensagem
- Botão para enviar a mensagem

- Menu
    - Menu item para apagar as mensagens do contacto (AlertDialog para confirmação da acção)

Ao pressionar o botão de enviar a mensagem, deve ser criada uma Message com o conteúdo da EditText. Esta deve ser inserida na BD e na lista do MessageAdapter. Não esquecer notificar o adapter que um item foi inserido na lista (ver método `notifyItemInserted`). 

    
## Dados

### ChatDatabase (RoomDatabase)
- Entities
    - Contact(id, name)
    - Message(id, contactId, text)
___
- DAOs
    - ContactDao (operações para criar um contacto e obter a lista de contactos)
    - MessageDao (obter a lista de mensagens para um determinado contacto)


## Sugestão de exercícios
- Na lista de contactos, mostrar também a última mensagem de cada contacto.
- Apagar uma mensagem com um clique longo (ver método `setOnLongClickListener`)
    - Passo adicional: Exibir um diálogo de confirmação desta acção destrutiva!
