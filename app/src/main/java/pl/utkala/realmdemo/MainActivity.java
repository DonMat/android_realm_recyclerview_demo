package pl.utkala.realmdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import pl.utkala.realmdemo.adapters.PersonsRecyclerViewAdapter;
import pl.utkala.realmdemo.models.Person;

public class MainActivity extends AppCompatActivity implements PersonsRecyclerViewAdapter.RemoveListener {

    private RealmRecyclerView recyclerView;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RealmRecyclerView) findViewById(R.id.realm_recycler_view);

        realm = Realm.getDefaultInstance();

        initData();

        showAllPersons();
    }

    private void showAllPersons() {
        RealmResults<Person> results = realm.where(Person.class).findAllSorted(Person.AGE, Sort.ASCENDING);
        setAdapter(results);
    }

    private void setAdapter(RealmResults<Person> results) {
        PersonsRecyclerViewAdapter adapter = new PersonsRecyclerViewAdapter(this, results, this);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        Person john = new Person("John", "Doe", 20);
        Person jenny = new Person("Jenny", "Doe", 26);

        realm.beginTransaction();
        realm.insertOrUpdate(john);
        realm.insertOrUpdate(jenny);
        realm.commitTransaction();
    }

    @Override
    protected void onDestroy() {
        if (realm != null)
            realm.close();
        super.onDestroy();
    }

    @Override
    public void removePerson(Person person) {
        realm.beginTransaction();
        person.deleteFromRealm();
        realm.commitTransaction();
    }
}
