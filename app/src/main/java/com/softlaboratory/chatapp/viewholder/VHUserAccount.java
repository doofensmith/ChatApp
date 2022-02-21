package com.softlaboratory.chatapp.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softlaboratory.chatapp.databinding.ItemUserAccountBinding;
import com.softlaboratory.chatapp.model.MAccount;

public class VHUserAccount extends RecyclerView.ViewHolder {

    //VIEW BINDING
    private ItemUserAccountBinding binding;

    public VHUserAccount(@NonNull ItemUserAccountBinding itemUserAccountBinding) {
        super(itemUserAccountBinding.getRoot());

        //INIT BINDING
        binding = itemUserAccountBinding;

    }

    //SET DATA
    public void setUserAccountData(MAccount mAccount) {
        binding.itemUserName.setText(mAccount.fullName);
        binding.itemUserEmail.setText(mAccount.email);
        binding.itemUserFirstLetter.setText(mAccount.fullName.charAt(0));
    }
}
