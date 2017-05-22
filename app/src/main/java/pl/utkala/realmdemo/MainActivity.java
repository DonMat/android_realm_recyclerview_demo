package pl.utkala.realmdemo;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import pl.utkala.realmdemo.adapters.PersonsRecyclerViewAdapter;
import pl.utkala.realmdemo.models.Person;

public class MainActivity extends AppCompatActivity implements PersonsRecyclerViewAdapter.PersonListListener {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_person:
                showAddPersonDialog();
                break;
            case R.id.add_random_persons:
                generateRandomPersons();
                break;
            case R.id.remove_random_persons:
                removeRandomPerson();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void showAddPersonDialog() {
        DialogFragment dialog = new NewPersonFragment();
        dialog.show(getSupportFragmentManager(), "new_person");
    }

    private void removeRandomPerson() {
        RealmResults<Person> random = realm.where(Person.class).findAllSorted(Person.ID);
        if(!random.isEmpty())
            removePerson(random.first());
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

    @Override
    public void editPerson(Person person) {
        NewPersonFragment dialog = new NewPersonFragment();
        dialog.setEditPerson(person);
        dialog.show(getSupportFragmentManager(), "person_dialog");
    }

    private void generateRandomPersons(){
        realm.beginTransaction();

        for (int i = 0; i < 3; i++) {
            String time = System.currentTimeMillis() + "";
            Person person = new Person("John", "Doe_" + time.substring(time.length() - 4), (int)(Math.random() * 100));
            realm.insertOrUpdate(person);
        }

        realm.commitTransaction();
    }
}
