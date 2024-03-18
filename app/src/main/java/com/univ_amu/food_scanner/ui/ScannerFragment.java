package com.univ_amu.food_scanner.ui;

import static com.univ_amu.food_scanner.ui.ScannerFragmentDirections.*;
import static com.univ_amu.food_scanner.ui.ScannerFragmentDirections.actionScannerFragmentToFoodFragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.zxing.BarcodeFormat;
import com.univ_amu.food_scanner.R;
import com.univ_amu.food_scanner.data.Repository;
import com.univ_amu.food_scanner.databinding.FragmentScannerBinding;

import java.util.Collections;

import javax.xml.transform.Result;

public class ScannerFragment extends Fragment {

    private FragmentScannerBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment using data binding
        binding = FragmentScannerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize and configure the ZXingScannerView
        binding.scanner.setAutoFocus(true);
        // Add any additional configuration for the scanner view if needed
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Release the binding when the view is destroyed to avoid memory leaks
        binding = null;
    }


    private void requestPermission() {
        requestPermissions(new String[] { android.Manifest.permission.CAMERA } ,0);
    }

    private boolean hasNoPermissions() {
        return ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (hasNoPermissions()) { requestPermission(); }
        if (hasNoPermissions()) { navigateUp(); }
    }


    @Override
    public void onResume() {
        super.onResume();
        binding.scanner.startCamera();
        binding.scanner.setFormats(Collections.singletonList(BarcodeFormat.EAN_13));
        binding.scanner.setResultHandler(this::handleResult);
    }

    private void handleResult(com.google.zxing.Result result) {
        String code = result.getText();
        repository.downloadFood(code).observe(getViewLifecycleOwner(), success -> {
            if (success) {
                // Aller vers le fragment affichant la composition de l'aliment
                onFoodDetected(code);
            } else {
                // Afficher une boite de dialogue indiquant que le code-barres n'a pas été trouvé
                onUnknownBarcode();
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        binding.scanner.stopCamera();
    }

    private void onFoodDetected(String code) {
        NavDirections action = (NavDirections) ScannerFragmentDirections.actionScannerFragmentToFoodFragment(code);
        Navigation.findNavController(requireView()).navigate(action);
    }

    private void onUnknownBarcode() {
        new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.unknown_barcode))
                .setPositiveButton(android.R.string.ok, (dialog, which) -> navigateUp())
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    private Repository repository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new Repository(getContext());
    }


}
