package com.softlaboratory.chatapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softlaboratory.chatapp.databinding.ItemUserAccountBinding;
import com.softlaboratory.chatapp.model.MAccount;
import com.softlaboratory.chatapp.viewholder.VHUserAccount;

import java.util.List;

public class AUserAccount extends RecyclerView.Adapter<VHUserAccount> {

    //LIST OF MACCOUNT
    private List<MAccount> mAccountList;
    //CONTEXT
    Context context;

    //CONSTRUCTOR
    public AUserAccount(List<MAccount> mAccountList, Context context) {
        this.mAccountList = mAccountList;
        this.context = context;
    }

    @NonNull
    @Override
    public VHUserAccount onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserAccountBinding itemUserAccountBinding = ItemUserAccountBinding.inflate(
                LayoutInflater.from(parent.getContext()),parent,false
        );
        return new VHUserAccount(itemUserAccountBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull VHUserAccount holder, int position) {

        holder.setUserAccountData(mAccountList.get(position));

    }

    @Override
    public int getItemCount() {
        return mAccountList.size();
    }
}
