package com.example.davidloris_project.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davidloris_project.Activity.AddAnswerActivity;
import com.example.davidloris_project.Activity.AddSubjectActivity;
import com.example.davidloris_project.Adapter.MessageAdapter;
import com.example.davidloris_project.CompositeObjects.AnswerWithUsername;
import com.example.davidloris_project.CompositeObjects.SubjectWithUserName;
import com.example.davidloris_project.Model.Answer;
import com.example.davidloris_project.R;
import com.example.davidloris_project.ViewModel.AnswerVM;
import com.example.davidloris_project.ViewModel.SubjectVM;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.davidloris_project.Fragment.LoginFragment.DEFAULT_ID;

public class InSubjectFragment extends Fragment {
    public static final int ADD_ANSWER_REQUEST = 1;
    private SubjectVM subjectVM;
    private AnswerVM answerVM;
    private int idSubject;
    private DateFormat date = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.getDefault());


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /* Get the id of the subject that was clicked in the last fragment */
        idSubject = getArguments().getInt("idSubject");

        final View inSubjectView = inflater.inflate(R.layout.fragment_insubject, container, false);

        final TextView textViewTitleSubject = inSubjectView.findViewById(R.id.text_view_title_subject_inMessage);
        final TextView textViewContentSubject = inSubjectView.findViewById(R.id.text_view_messageFromSubject);
        final TextView textViewPseudoSubject = inSubjectView.findViewById(R.id.text_view_pseudoFromSubject);
        final TextView textViewDateSubject = inSubjectView.findViewById(R.id.text_view_dateFromSubject);

        RecyclerView recyclerView = inSubjectView.findViewById(R.id.recycler_viewInSubject);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final MessageAdapter adapter = new MessageAdapter();
        recyclerView.setAdapter(adapter);

        answerVM = ViewModelProviders.of(this).get(AnswerVM.class);
        subjectVM = ViewModelProviders.of(this).get(SubjectVM.class);

        /* Show subject on top of the page */
        subjectVM.getSubjectById(idSubject).observe(this, new Observer<SubjectWithUserName>() {
            @Override
            public void onChanged(@Nullable SubjectWithUserName subjectWithUserName) {
                textViewTitleSubject.setText(subjectWithUserName.getTitle());
                textViewContentSubject.setText(subjectWithUserName.getTextSubject());
                textViewPseudoSubject.setText(subjectWithUserName.getPseudo());
                textViewDateSubject.setText(subjectWithUserName.getDate());
            }
        });

        /* Method that keep the fragment up to date whenever their is a new subject inserted */
        answerVM.getAllMessageFromSubject(idSubject).observe(this, new Observer<List<AnswerWithUsername>>() {
            @Override
            public void onChanged(@Nullable List<AnswerWithUsername> messages) {
                adapter.setMessages(messages);
            }
        });

        /* The button that open the addAnswer activity */
        FloatingActionButton buttonAddSubject = inSubjectView.findViewById(R.id.button_add_answer);
        buttonAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddAnswerActivity.class);
                startActivityForResult(intent, ADD_ANSWER_REQUEST);
            }
        });

        return inSubjectView;
    }

    /* This method create the insertion that the activity AddAnswer send back*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /* First if is when the user click on backButton */
        if (data != null) {
            if (requestCode == ADD_ANSWER_REQUEST || resultCode == getActivity().RESULT_OK) {
                String message = data.getStringExtra(AddSubjectActivity.EXTRA_MESSAGE);
                String PostingDate = date.format(Calendar.getInstance().getTime());

                Answer answer = new Answer(message, PostingDate, DEFAULT_ID, idSubject);

                answerVM.insert(answer);

                Toast.makeText(getActivity(), "Answer posted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "A wild problem appeared !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
