    package com.example.justdoit;

    import android.app.Activity;
    import android.content.DialogInterface;
    import android.graphics.Color;
    import android.os.Bundle;
    import android.text.Editable;
    import android.text.TextWatcher;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.EditText;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.core.content.ContextCompat;

    import com.example.justdoit.Model.TODO;
    import com.example.justdoit.Utils.DatabaseHelper;
    import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

    public class AddNewTask extends BottomSheetDialogFragment {
        public static final String TAG = "AddNewTask";

        private EditText mEditText;
        private Button saveBtn;

        private DatabaseHelper myDB;
        public static AddNewTask newInstance()
        {
            return new AddNewTask();
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.add_newtask, container, false);

            return v;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            mEditText = view.findViewById(R.id.newtaskText);
            saveBtn = view.findViewById(R.id.newtaskBtn);

            myDB = new DatabaseHelper(getActivity());

            boolean isUpdate = false;
            Bundle bundle = getArguments();
            if (bundle != null) {
                isUpdate = true;

                String task = bundle.getString("TASK");
                mEditText.setText(task);

                if (task.length() > 0) {
                    saveBtn.setEnabled(false);
                }
            }

            mEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().equals("")){
                        saveBtn.setEnabled(false);
                        saveBtn.setBackgroundColor(Color.DKGRAY);
                    }else {
                        saveBtn.setEnabled(true);
                        saveBtn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

          final boolean finalIsUpdate = isUpdate;
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = mEditText.getText().toString();
                    if(finalIsUpdate)
                    {
                        myDB.updateTask(bundle.getInt("ID"), text);
                    }
                    else {
                        TODO item = new TODO();
                        item.setTask(text);
                        item.setStatus(0);
                        myDB.insertTask(item);
                    }
                    dismiss();
                }
            });
        }

        @Override
        public void onDismiss(@NonNull DialogInterface dialog) {
            super.onDismiss(dialog);

            Activity activity = getActivity();
            if (activity instanceof onDialogCloseListener)
            {
                ((onDialogCloseListener)activity).onDialogClose(dialog);
            }
        }
    }

