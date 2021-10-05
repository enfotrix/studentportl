package com.enfotrix.cgs.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.enfotrix.cgs.Adapters.Adapter_Notifi;
import com.enfotrix.cgs.Models.Model_Notifi;
import com.enfotrix.cgs.Models.NotificationsViewModel;
import com.enfotrix.cgs.R;
import com.enfotrix.cgs.Utils;
import com.enfotrix.cgs.databinding.FragmentNotificationsBinding;
import com.enfotrix.cgs.lottiedialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FirebaseFirestore firestore;
    private Utils utils;


    List<Model_Notifi> list_Notifi = new ArrayList<>();
    RecyclerView recyc_Notifi;
    //recyc_eNotifi;


    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        firestore = FirebaseFirestore.getInstance();
        utils = new Utils(getContext());

        recyc_Notifi = root.findViewById(R.id.list_Notifi);
        recyc_Notifi.setHasFixedSize(true);
        recyc_Notifi.setLayoutManager(new LinearLayoutManager(getContext()));

//        recyc_eNotifi = root.findViewById(R.id.list_e_Notifi);
//        recyc_eNotifi.setHasFixedSize(true);
//        recyc_eNotifi.setLayoutManager(new LinearLayoutManager(getContext()));


        fetchNotifi(utils.getToken());

        final SwipeRefreshLayout pullToRefresh = root.findViewById(R.id.swiperefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchNotifi(utils.getToken());
                pullToRefresh.setRefreshing(false);
            }
        });


        return root;
    }

    private void fetchNotifi(String token) {
        final lottiedialog lottie = new lottiedialog(getContext());
        lottie.show();

        list_Notifi.clear();
        firestore.collection("Students").document(token)
                .collection("Notification").orderBy("date", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Model_Notifi model_Notifi = new Model_Notifi(
                                document.getId(),
                                document.getString("data"),
                                document.getString("date"),
                                document.getString("heading"));
                        list_Notifi.add(model_Notifi);
                    }
                    Adapter_Notifi adapter_notifi = new Adapter_Notifi(list_Notifi);
                    recyc_Notifi.setAdapter(adapter_notifi);

                    lottie.dismiss();
                } else {
                    lottie.dismiss();
                    Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        lottie.dismiss();
                        Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}