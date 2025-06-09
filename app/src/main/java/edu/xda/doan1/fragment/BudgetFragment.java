package edu.xda.doan1.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import edu.xda.doan1.data.MyDatabaseHelper;
import edu.xda.hongtt.R;

public class BudgetFragment extends Fragment {

    private TextView tvTotalThu, tvTotalChi, tvBudgetSaved;
    private TextView tvWarningChiVuotNganSach;
    private EditText edtBudgetChi;
    private Button btnSaveBudget;
    private MyDatabaseHelper databaseHelper;

    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "budget_prefs";
    private static final String KEY_BUDGET_CHI = "key_budget_chi";

    private LinearLayout layoutInputBudget;
    private Button btnToggleInput;

    public BudgetFragment() {
        // Required empty public constructor
    }

    public double getBudgetChi() {

        return databaseHelper.getTotalChi();
    }

    public double getBudgetThu() {
        return databaseHelper.getTotalThu();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.budget_layout, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutInputBudget = view.findViewById(R.id.layoutInputBudget);
        btnToggleInput = view.findViewById(R.id.btnToggleInput);

        tvTotalThu = view.findViewById(R.id.tvTotalThu);
        tvTotalChi = view.findViewById(R.id.tvTotalChi);
        tvBudgetSaved = view.findViewById(R.id.tvBudgetSaved);
        tvWarningChiVuotNganSach = view.findViewById(R.id.tvWarningChiVuotNganSach);

        edtBudgetChi = view.findViewById(R.id.edtBudgetChi);
        btnSaveBudget = view.findViewById(R.id.btnSaveBudget);

        databaseHelper = new MyDatabaseHelper(getContext());
        sharedPreferences = getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        tvBudgetSaved.setVisibility(View.VISIBLE);

        loadBudgetData();
        loadSavedBudget();

        btnSaveBudget.setOnClickListener(v -> {
            String budgetStr = edtBudgetChi.getText().toString().trim();
            if (budgetStr.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập ngân sách", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                double budgetChi = Double.parseDouble(budgetStr);
                saveBudgetChi(budgetChi);
                tvBudgetSaved.setText("Ngân sách hiện tại: " + budgetChi);
                tvBudgetSaved.setVisibility(View.VISIBLE);
                loadBudgetData();
                Toast.makeText(getContext(), "Lưu ngân sách thành công", Toast.LENGTH_SHORT).show();

                layoutInputBudget.setVisibility(View.GONE);
                btnToggleInput.setText("+");
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Ngân sách không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });
        btnToggleInput.setOnClickListener(v -> {
            if (layoutInputBudget.getVisibility() == View.GONE) {
                layoutInputBudget.setVisibility(View.VISIBLE);
                btnToggleInput.setText("-"); // đổi thành dấu trừ nếu muốn
            } else {
                layoutInputBudget.setVisibility(View.GONE);
                btnToggleInput.setText("+"); // đổi lại dấu cộng
            }
        });

    }

    private void loadBudgetData() {
        String sqlTotalThu = "SELECT SUM(dinhMucThu) FROM thu WHERE deleteFlag = 0";
        Cursor cursorThu = databaseHelper.GetDate(sqlTotalThu);
        if (cursorThu.moveToFirst()) {
            double totalThu = cursorThu.getDouble(0);
            tvTotalThu.setText("Tổng thu: " + totalThu);
        }
        cursorThu.close();

        String sqlTotalChi = "SELECT SUM(dinhMucChi) FROM chi WHERE deleteFlag = 0";
        Cursor cursorChi = databaseHelper.GetDate(sqlTotalChi);
        if (cursorChi.moveToFirst()) {
            double totalChi = cursorChi.getDouble(0);
            tvTotalChi.setText("Tổng chi: " + totalChi);
        }
        checkChiVuotNganSach();
        cursorChi.close();
    }

    private void loadSavedBudget() {
        double budgetChi = Double.longBitsToDouble(sharedPreferences.getLong(KEY_BUDGET_CHI, Double.doubleToLongBits(0)));
        tvBudgetSaved.setText("Ngân sách hiện tại: " + budgetChi);
        checkChiVuotNganSach();
    }

    private void saveBudgetChi(double budgetChi) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(KEY_BUDGET_CHI, Double.doubleToLongBits(budgetChi));
        editor.apply();
    }
    private void checkChiVuotNganSach() {
        double budgetChi = Double.longBitsToDouble(sharedPreferences.getLong(KEY_BUDGET_CHI, Double.doubleToLongBits(0)));

        String sqlTotalChi = "SELECT SUM(dinhMucChi) FROM chi WHERE deleteFlag = 0";
        Cursor cursorChi = databaseHelper.GetDate(sqlTotalChi);
        double totalChi = 0;
        if (cursorChi.moveToFirst()) {
            totalChi = cursorChi.getDouble(0);
        }
        cursorChi.close();

        if (budgetChi > 0 && totalChi > budgetChi) {
            tvWarningChiVuotNganSach.setVisibility(View.VISIBLE);
        } else {
            tvWarningChiVuotNganSach.setVisibility(View.GONE);
        }
    }

}
