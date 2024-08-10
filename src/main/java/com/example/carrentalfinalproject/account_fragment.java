package com.example.carrentalfinalproject;

import static android.content.Context.MODE_PRIVATE;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class account_fragment extends Fragment {
    private Button logoutButton;
    private SharedPreferences sharedPreferences;
    private TextView userNameTextView;
    private TextView emailTextView;
    private ImageView profileImage;
    private Button editProfileButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment, container, false);

        userNameTextView = view.findViewById(R.id.user_name);
        emailTextView = view.findViewById(R.id.email);
        logoutButton = view.findViewById(R.id.logout);

        // Set user data (this could come from a saved user session or database)
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", 0);
        String userName = sharedPreferences.getString("username", "");
        String email = sharedPreferences.getString("email", "guest@example.com");

        userNameTextView.setText(userName);
        emailTextView.setText(email);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        profileImage = view.findViewById(R.id.profile_image);
        editProfileButton = view.findViewById(R.id.edit_profile_button);

        loadProfileData();

        editProfileButton.setOnClickListener(v -> {
            // Navigate to EditProfileFragment
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new EditProfileFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private void logout() {
        // Clear saved username and password
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("password");
        editor.apply();

        // Navigate back to the login screen
        Intent intent = new Intent(requireContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
    private void loadProfileData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserProfile", Context.MODE_PRIVATE);
        String emailText = sharedPreferences.getString("email", "user@example.com");
        String imageUriString = sharedPreferences.getString("profileImageUri", null);

        emailTextView.setText(emailText);
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            profileImage.setImageURI(imageUri);
        }
    }
}
