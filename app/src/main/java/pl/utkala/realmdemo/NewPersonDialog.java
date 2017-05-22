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


public class NewPersonDialog extends DialogFragment {
    private EditText nameEdit;
    private EditText surnameEdit;
    private EditText ageEdit;
    private Button saveBtn;

    private Person editPerson;

    public NewPersonDialog() {
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

        if(editPerson != null)
            bindPersonData();
        return view;
    }

    private void bindPersonData() {
        nameEdit.setText(editPerson.getName());
        surnameEdit.setText(editPerson.getSurname());
        ageEdit.setText(String.valueOf(editPerson.getAge()));
    }

    private void savePerson() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        int age = 0;
        try {
            age = Integer.parseInt(ageEdit.getText().toString());
        } catch (Exception ignored) {
        }

        Person person;
        if (editPerson != null)
            person = editPerson;
        else
            person = new Person();

        person.setName(nameEdit.getText().toString());
        person.setSurname(surnameEdit.getText().toString());
        person.setAge(age);

        realm.copyToRealmOrUpdate(person);
        realm.commitTransaction();
        realm.close();
    }

    public void setEditPerson(Person editPerson) {
        this.editPerson = editPerson;
    }
}
