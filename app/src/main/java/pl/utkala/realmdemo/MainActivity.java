package pl.utkala.realmdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private RealmRecyclerView recyclerView;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        recyclerView = (RealmRecyclerView) findViewById(R.id.realm_recycler_view);
    }

    @Override
    protected void onDestroy() {
        if(realm != null)
            realm.close();
        super.onDestroy();
    }
}
