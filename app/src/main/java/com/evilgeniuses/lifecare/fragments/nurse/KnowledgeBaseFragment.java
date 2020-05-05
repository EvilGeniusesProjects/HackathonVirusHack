package com.evilgeniuses.lifecare.fragments.nurse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.evilgeniuses.lifecare.R;
import com.evilgeniuses.lifecare.activities.WebViewActivity;
import com.evilgeniuses.lifecare.interfaces.SwitchFragment;

public class KnowledgeBaseFragment extends Fragment implements View.OnClickListener {
    SwitchFragment switchFragment;

    ImageView imageViewSearch;

    EditText editTextSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_knowledge_base, container,false);
        editTextSearch = rootView.findViewById(R.id.editTextSearch);
        imageViewSearch = rootView.findViewById(R.id.imageViewSearch);
        imageViewSearch.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageViewSearch:
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("search",  editTextSearch.getText().toString());
                getContext().startActivity(intent);
                break;
        }
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof SwitchFragment){
            switchFragment = (SwitchFragment) context;
        }
    }

    public static KnowledgeBaseFragment newInstance(){
        return new KnowledgeBaseFragment();
    }
}