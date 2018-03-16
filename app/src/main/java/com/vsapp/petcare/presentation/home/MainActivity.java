package com.vsapp.petcare.presentation.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ramotion.foldingcell.FoldingCell;
import com.vsapp.petcare.BR;
import com.vsapp.petcare.R;
import com.vsapp.petcare.Utils.Utils;
import com.vsapp.petcare.datamodels.DogList;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    DogAdapter dogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // progressDialog = Utils.showLoadingDialog(getContext(), true);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("dogs");
        databaseReference.keepSynced(true);

        FirebaseRecyclerOptions<DogList> options =
                new FirebaseRecyclerOptions.Builder<DogList>()
                        .setQuery(databaseReference, DogList.class)
                        .build();
        Log.d("info",databaseReference.toString());
        dogAdapter = new DogAdapter(options);
        ((RecyclerView) findViewById(R.id.dogs_list)).setAdapter(dogAdapter);
        dogAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
               /* if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.cancel();
                }*/
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        dogAdapter.startListening();
    }

    class DogAdapter extends FirebaseRecyclerAdapter<DogList, ViewHolder> {

        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        DogAdapter(FirebaseRecyclerOptions<DogList> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(final ViewHolder holder, int position, DogList model) {
            holder.getViewDataBinding().setVariable(BR.dog, model);
            holder.getViewDataBinding().setVariable(BR.img,model);
            Glide.with(getContext()).load(model.getDogImage()).into((ImageView) holder.getViewDataBinding().getRoot().findViewById(R.id.dog_icon));
            Glide.with(getContext()).load(model.getDogImage()).into((ImageView) holder.getViewDataBinding().getRoot().findViewById(R.id.image2));

            ((FoldingCell) holder.getViewDataBinding().getRoot().findViewById(R.id.dog_folding_cell)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((FoldingCell) v).toggle(false);
                }
            });
            Log.d("vamshi",model.getDogImage());
           holder.getViewDataBinding().getRoot().findViewById(R.id.information).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Toast.makeText(MainActivity.this,"pressed",Toast.LENGTH_SHORT).show();

               }
           });

            holder.getViewDataBinding().executePendingBindings();



        }

        @Override
        public int getItemCount() {
            return super.getItemCount();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return (ViewHolder) (new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_dog_item, parent, false)));
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ViewDataBinding viewDataBinding;
        ImageView img;
        Button b1;

        ViewHolder(View itemView) {
            super(itemView);
            viewDataBinding = DataBindingUtil.bind(itemView);
            img=findViewById(R.id.dog_icon);
            b1=findViewById(R.id.info);
        }

        ViewDataBinding getViewDataBinding() {
            return viewDataBinding;
        }
    }

    private Context getContext() {
        return MainActivity.this;
    }
}
