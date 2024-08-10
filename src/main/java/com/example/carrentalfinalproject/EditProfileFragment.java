package com.example.carrentalfinalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EditProfileFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView editProfileImage;
    private EditText editEmail;
    private Uri imageUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        editProfileImage = view.findViewById(R.id.edit_profile_image);
        editEmail = view.findViewById(R.id.edit_email);
        Button changeImageButton = view.findViewById(R.id.change_image_button);
        Button saveChangesButton = view.findViewById(R.id.save_changes_button);

        changeImageButton.setOnClickListener(v -> openImagePicker());

        saveChangesButton.setOnClickListener(v -> saveProfileChanges());

        return view;
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            editProfileImage.setImageURI(imageUri);
        }
    }

    private void saveProfileChanges() {
        String newEmail = editEmail.getText().toString().trim();

        if (TextUtils.isEmpty(newEmail)) {
            Toast.makeText(getContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save the new email and profile picture URI
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserProfile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", newEmail);
        if (imageUri != null) {
            editor.putString("profileImageUri", imageUri.toString());
        }
        editor.apply();

        Toast.makeText(getContext(), "Profile updated", Toast.LENGTH_SHORT).show();

        // Navigate back to the account fragment or update UI in current fragment
        getFragmentManager().popBackStack();
    }
}
