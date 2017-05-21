package pl.utkala.realmdemo;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import io.realm.Realm;
import pl.utkala.realmdemo.models.Person;


public class NewPersonFragment extends DialogFragment {
    private EditText nameEdit;
    private EditText surnameEdit;
    private EditText ageEdit;
    private Button saveBtn;

    public NewPersonFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_person_dialog, container, false);

        nameEdit = (EditText) view.findViewById(R.id.person_name_edit);
        surnameEdit = (EditText) view.findViewById(R.id.person_surname_edit);
        ageEdit = (EditText) view.findViewById(R.id.person_age_edit);
        saveBtn = (Button) view.findViewById(R.id.save_person_btn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePerson();
                dismiss();
            }
        });
        return view;
    }

    private void savePerson() {
        int age = 0;
        try {
            age = Integer.parseInt(ageEdit.getText().toString());
        } catch (Exception ignored) {
        }

        Person person = new Person(
                nameEdit.getText().toString(),
                surnameEdit.getText().toString(),
                age);

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(person);
        realm.commitTransaction();
        realm.close();
    }

}
