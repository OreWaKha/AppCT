package edu.xda.doan1.fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

import edu.xda.hongtt.R;
import edu.xda.doan1.adapter.spAdapter;
import edu.xda.doan1.data.MyDatabaseHelper;
import edu.xda.doan1.model.ThangNam;

public class ThangFragment extends Fragment {
    TextView txtTongThu, txtTongChi;
    PieChart pieChart;
    PieChart pieChartLoai;
    Spinner spThang;
    int tongThu = 0;
    int tongChi = 0;
    MyDatabaseHelper database;
    Calendar calendar = Calendar.getInstance();
    String thangHienTai;
    ArrayList<ThangNam> thangNamArrayList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thang, container, false);
        initControls(v);
        initEvents();
        return v;
    }
    private void initEvents() {
        spThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ThangNam thangNam = thangNamArrayList.get(i);
                if (thangNam.getId() == 0){
                    ngayHienTai();
                    getChi();
                    getThu();
                    txtTongChi.setText("Tổng Chi: " + FormatCost(tongChi) + " VND");
                    txtTongThu.setText("Tổng Thu: " + FormatCost(tongThu) + " VND");
                    setUpPieChart();
                    setUpPieChartLoaiThuChi();
                }else {
                    thangHienTai = String.valueOf(thangNam.getId()) + "/" + calendar.get(Calendar.YEAR);
                    getChi();
                    getThu();
                    txtTongChi.setText("Tổng Chi: " + FormatCost(tongChi) + " VND");
                    txtTongThu.setText("Tổng Thu: " + FormatCost(tongThu) + " VND");
                    setUpPieChart();
                    setUpPieChartLoaiThuChi();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void initControls(View v) {
        pieChart = v.findViewById(R.id.pieChartThang);
        pieChartLoai = v.findViewById(R.id.pieChartLoai);
        txtTongChi = v.findViewById(R.id.txtTongchiThang);
        txtTongThu = v.findViewById(R.id.txtTongthuThang);
        spThang = v.findViewById(R.id.spChonThang);
        database = new MyDatabaseHelper(getContext());
        thangNamArrayList = new ArrayList<>();

        thangNamArrayList.add(new ThangNam(0,"Chọn tháng"));
        for (int i = 1; i < 13; i++){
            thangNamArrayList.add(new ThangNam(i,"Tháng " + i));
        }
        spAdapter adapter = new spAdapter(getContext(), thangNamArrayList);
        spThang.setAdapter(adapter);
    }
    public String FormatCost(long cost){
        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###", symbols);
            return decimalFormat.format(Integer.parseInt(cost+""));
        }catch (Exception e) {
            return cost + "";
        }
    }
    public void getChi(){
        Cursor cursor = database.GetDate("SELECT * FROM chi WHERE deleteFlag = '0'");
        int usd = 0;
        int toVnd = 23255;
        int vnd = 0;
        int vietNamDong = 0;
        while (cursor.moveToNext()) {
            int dinhMucChi = cursor.getInt(2);
            String donViChi = cursor.getString(3);
            String ngayThang = cursor.getString(4);
            if (ngayThang.contains(thangHienTai)){
                if (donViChi.equalsIgnoreCase("USD")){
                    usd = usd + dinhMucChi;
                    vnd = (usd * toVnd);
                }
                if (donViChi.equalsIgnoreCase("VND")){
                    vietNamDong = vietNamDong + dinhMucChi;
                }
            }
        }
        tongChi = vnd + vietNamDong;
    }
    public void getThu(){
        Cursor cursor = database.GetDate("SELECT * FROM thu WHERE deleteFlag = '0'");
        int usd = 0;
        int toVnd = 23255;
        int vnd = 0;
        int vietNamDong = 0;
        while (cursor.moveToNext()) {
            int dinhMucThu = cursor.getInt(2);
            String donViChi = cursor.getString(3);
            String ngayThang = cursor.getString(4);
            if (ngayThang.contains(thangHienTai)) {
                if (donViChi.equalsIgnoreCase("USD")) {
                    usd = usd + dinhMucThu;
                    vnd = (usd * toVnd);
                }
                if (donViChi.equalsIgnoreCase("VND")) {
                    vietNamDong = vietNamDong + dinhMucThu;
                }
            }
        }
        tongThu = vnd + vietNamDong;
    }

    public void ngayHienTai(){
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        thangHienTai = (mMonth+1) + "/" + mYear;
    }

    private void setUpPieChart() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(tongThu, "Thu"));
        entries.add(new PieEntry(tongChi, "Chi"));

        PieDataSet dataSet = new PieDataSet(entries, "Tổng Thu / Chi");
        dataSet.setColors(Color.rgb(76, 175, 80), Color.rgb(244, 67, 54)); // Xanh lá & đỏ
        dataSet.setValueTextSize(14f);
        dataSet.setValueTextColor(Color.WHITE);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        data.setValueFormatter(new PercentFormatter(pieChart));

        pieChart.setUsePercentValues(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(45f);
        pieChart.setTransparentCircleRadius(50f);
        pieChart.setCenterText("Month");
        pieChart.setCenterTextSize(18f);

        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(true);

        pieChart.invalidate(); // vẽ lại biểu đồ
    }

    private void setUpPieChartLoaiThuChi() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // THU: group by tenLoaiThu
        Cursor cursorThu = database.GetDate("SELECT loaithu.tenLoaiThu, SUM(thu.dinhMucThu), thu.donViThu " +
                "FROM thu INNER JOIN loaithu ON thu.idLoaiThu = loaithu.id " +
                "WHERE thu.deleteFlag = '0' AND thu.thoiDiemApDungThu LIKE '%" + thangHienTai + "%' " +
                "GROUP BY loaithu.tenLoaiThu, thu.donViThu");

        while (cursorThu.moveToNext()) {
            String loai = cursorThu.getString(0);
            int tong = cursorThu.getInt(1);
            String donVi = cursorThu.getString(2);

            if (donVi.equalsIgnoreCase("USD")) {
                tong *= 23255;
            }

            entries.add(new PieEntry(tong, "Thu: " + loai));
        }

        // CHI: group by tenLoaiChi
        Cursor cursorChi = database.GetDate("SELECT loaichi.tenLoaiChi, SUM(chi.dinhMucChi), chi.donViChi " +
                "FROM chi INNER JOIN loaichi ON chi.idLoaiChi = loaichi.id " +
                "WHERE chi.deleteFlag = '0' AND chi.thoiDiemApDungChi LIKE '%" + thangHienTai + "%' " +
                "GROUP BY loaichi.tenLoaiChi, chi.donViChi");

        while (cursorChi.moveToNext()) {
            String loai = cursorChi.getString(0);
            int tong = cursorChi.getInt(1);
            String donVi = cursorChi.getString(2);

            if (donVi.equalsIgnoreCase("USD")) {
                tong *= 23255;
            }

            entries.add(new PieEntry(tong, "Chi: " + loai));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Phân loại Thu & Chi");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.BLACK);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(pieChartLoai)); // hiển thị phần trăm

        pieChartLoai.setData(data);
        pieChartLoai.setUsePercentValues(true);
        pieChartLoai.setDrawEntryLabels(false); // <-- Cho phép hiển thị label từng phần
        pieChartLoai.setEntryLabelTextSize(10f);

        pieChartLoai.setDrawHoleEnabled(true);
        pieChartLoai.setHoleRadius(40f);
        pieChartLoai.setTransparentCircleRadius(45f);
        pieChartLoai.setCenterText("Phân loại");
        pieChartLoai.setCenterTextSize(16f);
        pieChartLoai.getDescription().setEnabled(false);
        pieChartLoai.getLegend().setEnabled(true);

        pieChartLoai.invalidate();
    }

}
