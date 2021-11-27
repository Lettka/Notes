package com.example.notes;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class ImageFragment extends Fragment {

    private static final String ARG_PARAM1 = "position";

    private int position;

    public ImageFragment() {
        // Required empty public constructor
    }

    public static ImageFragment newInstance(int position) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.close).setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
            Toast.makeText(getContext(), "image was closed.", Toast.LENGTH_SHORT).show();
        });

        initView(view);
    }

    private void initView(View view) {
        ImageView imageView = view.findViewById(R.id.image_view_child);
        imageView.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireActivity(), v);
            requireActivity().getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                int id = menuItem.getItemId();
                if (id == R.id.action_popup_clear) {
                    imageView.setImageResource(0);
                    return true;
                } else if (id == R.id.action_popup_exit) {
                    requireActivity().onBackPressed();
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });
        TypedArray images = getResources().obtainTypedArray(R.array.img_for_notes);
        imageView.setImageResource(images.getResourceId(position, 0));
        images.recycle();
    }
}