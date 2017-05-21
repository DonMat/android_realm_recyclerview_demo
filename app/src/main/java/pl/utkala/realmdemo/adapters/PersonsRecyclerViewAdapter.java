package pl.utkala.realmdemo.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import pl.utkala.realmdemo.R;
import pl.utkala.realmdemo.models.Person;


public class PersonsRecyclerViewAdapter extends RealmBasedRecyclerViewAdapter<Person, PersonsRecyclerViewAdapter.ViewHolder> {

    RemoveListener listener;

    public PersonsRecyclerViewAdapter(Context context, RealmResults<Person> realmResults, RemoveListener listener) {
        super(context, realmResults, true, true);
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int position) {
        View v = inflater.inflate(R.layout.person_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
        Person person = realmResults.get(position);
        viewHolder.person = person;
        viewHolder.setPersonText();
        viewHolder.setRemoveButton();
    }

    class ViewHolder extends RealmViewHolder {
        TextView personTextView;
        Button removeButton;
        Person person;

        public ViewHolder(View itemView) {
            super(itemView);
            personTextView = (TextView) itemView.findViewById(R.id.person_name_text);
            removeButton = (Button) itemView.findViewById(R.id.remove_person_button);
        }

        public void setPersonText() {
            personTextView.setText(person.toString());
        }

        public void setRemoveButton() {
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.removePerson(person);
                    notifyDataSetChanged();
                }
            });
        }
    }

    public interface RemoveListener {
        void removePerson(Person person);
    }
}
