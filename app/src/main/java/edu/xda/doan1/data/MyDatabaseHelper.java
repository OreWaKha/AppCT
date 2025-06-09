package edu.xda.doan1.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.xda.doan1.model.Chi;
import edu.xda.doan1.model.LoaiChi;
import edu.xda.doan1.model.LoaiThu;
import edu.xda.doan1.model.Thu;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "QLCT";


    //table user
    private static final String TABLE_USER = "User";
    private static final String COLUMN_USER_NAME = "username";
    private static final String COLUMN_USER_PASSWORD = "password";

    private static final String CREATE_CLASS_TABLE_USER = "CREATE TABLE " + TABLE_USER + "(" + COLUMN_USER_NAME + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    public boolean insert(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long ins = db.insert("User", null, contentValues);
        if (ins == -1) return false;
        else return true;
    }


    public Boolean checkLogin(String username, String password) {
        boolean check = false;
        String countQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_NAME + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, new String[]{username, password});
        if (cursor.getCount() > 0) {
            check = true;
        }
        return check;
    }

    public Boolean checkUsername(String username) {
        boolean check = true;
        String countQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_NAME + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, new String[]{username});
        if (cursor.getCount() > 0) {
            check = false;
        }
        return check;
    }

    // ---Table Khoan Thu---
    private static final String KEY_NAME_TABLE_LOAITHU = "loaithu";
    private static final String KEY_TABLE_ID_LOAITHU = "id";
    private static final String KEY_TABLE_NAME_LOAITHU = "tenLoaiThu";
    private static final String KEY_TABLE_DELETEFLAG_LOAITHU = "deleteFlag";

    // ---Table Thu---

    private static final String KEY_NAME_TABLE_THU = "thu";
    private static final String KEY_TABLE_ID_THU = "id";
    private static final String KEY_TABLE_TENMUCTHU_THU = "tenMucThu";
    private static final String KEY_TABLE_DINHMUCTHU_THU = "dinhMucThu";
    private static final String KEY_TABLE_DONVITHU_THU = "donViThu";
    private static final String KEY_TABLE_THOIDIEMAPDUNGTHU_THU = "thoiDiemApDungThu";
    private static final String KEY_TABLE_DANHGIA_THU = "danhGia";
    private static final String KEY_TABLE_DELETEFLAG_THU = "deleteFlag";
    private static final String KEY_TABLE_IDLOAITHU_THU = "idLoaiThu";

    // ---Table Loai Chi ---

    private static final String KEY_NAME_TABLE_LOAICHI = "loaiChi";
    private static final String KEY_TABLE_ID_LOAICHI = "id";
    private static final String KEY_TABLE_NAME_LOAICHI = "tenLoaiChi";
    private static final String KEY_TABLE_DELETEFLAG_LOAICHI = "deleteFlag";

    // ---Table Chi---

    private static final String KEY_NAME_TABLE_CHI = "chi";
    private static final String KEY_TABLE_ID_CHI = "id";
    private static final String KEY_TABLE_TENMUCCHI_CHI = "tenMucChi";
    private static final String KEY_TABLE_DINHMUCCHI_CHI = "dinhMucChi";
    private static final String KEY_TABLE_DONVICHI_CHI = "donViCHI";
    private static final String KEY_TABLE_THOIDIEMAPDUNGCHI_CHI = "thoiDiemApDungChi";
    private static final String KEY_TABLE_DANHGIA_CHI = "danhGia";
    private static final String KEY_TABLE_DELETEFLAG_CHI = "deleteFlag";
    private static final String KEY_TABLE_IDLOAICHI_CHI = "idLoaiChi";


    private static final String CREATE_CLASS_TABLE_LOAITHU = "CREATE TABLE "
            + KEY_NAME_TABLE_LOAITHU + "("
            + KEY_TABLE_ID_LOAITHU + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_TABLE_NAME_LOAITHU + " TEXT,"
            + KEY_TABLE_DELETEFLAG_LOAITHU + " INTEGER" + ")";

    private static final String CREATE_CLASS_TABLE_THU = "CREATE TABLE "
            + KEY_NAME_TABLE_THU + "("
            + KEY_TABLE_ID_THU + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_TABLE_TENMUCTHU_THU + " TEXT,"
            + KEY_TABLE_DINHMUCTHU_THU + " DECIMAL,"
            + KEY_TABLE_DONVITHU_THU + " TEXT,"
            + KEY_TABLE_THOIDIEMAPDUNGTHU_THU + " DATETIME,"
            + KEY_TABLE_DANHGIA_THU + " INTEGER,"
            + KEY_TABLE_DELETEFLAG_THU + " INTEGER,"
            + KEY_TABLE_IDLOAITHU_THU + " INTEGER" + ")";

    private static final String CREATE_CLASS_TABLE_LOAICHI = "CREATE TABLE "
            + KEY_NAME_TABLE_LOAICHI + "("
            + KEY_TABLE_ID_LOAICHI + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_TABLE_NAME_LOAICHI + " TEXT,"
            + KEY_TABLE_DELETEFLAG_LOAICHI + " INTEGER" + ")";

    private static final String CREATE_CLASS_TABLE_CHI = "CREATE TABLE "
            + KEY_NAME_TABLE_CHI + "("
            + KEY_TABLE_ID_CHI + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_TABLE_TENMUCCHI_CHI + " TEXT,"
            + KEY_TABLE_DINHMUCCHI_CHI + " DECIMAL,"
            + KEY_TABLE_DONVICHI_CHI + " TEXT,"
            + KEY_TABLE_THOIDIEMAPDUNGCHI_CHI + " DATETIME,"
            + KEY_TABLE_DANHGIA_CHI + " INTEGER,"
            + KEY_TABLE_DELETEFLAG_CHI + " INTEGER,"
            + KEY_TABLE_IDLOAICHI_CHI + " INTEGER" + ")";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor GetDate(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLASS_TABLE_USER);
        db.execSQL(CREATE_CLASS_TABLE_THU);
        db.execSQL(CREATE_CLASS_TABLE_LOAITHU);
        db.execSQL(CREATE_CLASS_TABLE_LOAICHI);
        db.execSQL(CREATE_CLASS_TABLE_CHI);
        db.execSQL(CREATE_TABLE_BUDGET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + KEY_NAME_TABLE_THU);
        db.execSQL("DROP TABLE IF EXISTS " + KEY_NAME_TABLE_LOAITHU);
        db.execSQL("DROP TABLE IF EXISTS " + KEY_NAME_TABLE_CHI);
        db.execSQL("DROP TABLE IF EXISTS " + KEY_NAME_TABLE_LOAICHI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGET);
        onCreate(db);
    }

    public void addLoaiThu(LoaiThu loaiThu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TABLE_NAME_LOAITHU, loaiThu.getTenLoaiThu());
        values.put(KEY_TABLE_DELETEFLAG_LOAITHU, loaiThu.getDeleteFlag());

        db.insert(KEY_NAME_TABLE_LOAITHU, null, values);
        db.close();
    }

    public void addThu(Thu thu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TABLE_TENMUCTHU_THU, thu.getTenMucThu());
        values.put(KEY_TABLE_DINHMUCTHU_THU, thu.getDinhMucThu());
        values.put(KEY_TABLE_DONVITHU_THU, thu.getDonViThu());
        values.put(KEY_TABLE_THOIDIEMAPDUNGTHU_THU, thu.getThoiDiemApDungThu());
        values.put(KEY_TABLE_DANHGIA_THU, thu.getDanhGia());
        values.put(KEY_TABLE_DELETEFLAG_THU, thu.getDeleteFlag());
        values.put(KEY_TABLE_IDLOAITHU_THU, thu.getIdLoaiThu());

        db.insert(KEY_NAME_TABLE_THU, null, values);
        db.close();
    }

    public void addLoaiChi(LoaiChi loaiChi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TABLE_NAME_LOAICHI, loaiChi.getTenLoaiChi());
        values.put(KEY_TABLE_DELETEFLAG_LOAICHI, loaiChi.getDeleteFlag());

        db.insert(KEY_NAME_TABLE_LOAICHI, null, values);
        db.close();
    }

    public void addChi(Chi chi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TABLE_TENMUCCHI_CHI, chi.getTenMucChi());
        values.put(KEY_TABLE_DINHMUCCHI_CHI, chi.getDinhMucChi());
        values.put(KEY_TABLE_DONVICHI_CHI, chi.getDonViChi());
        values.put(KEY_TABLE_THOIDIEMAPDUNGCHI_CHI, chi.getThoiDiemApDungChi());
        values.put(KEY_TABLE_DANHGIA_CHI, chi.getDanhGia());
        values.put(KEY_TABLE_DELETEFLAG_CHI, chi.getDeleteFlag());
        values.put(KEY_TABLE_IDLOAICHI_CHI, chi.getIdLoaiChi());
        db.insert(KEY_NAME_TABLE_CHI, null, values);
        db.close();
    }

    private static final String TABLE_BUDGET = "budget";
    private static final String COLUMN_BUDGET_ID = "id";
    private static final String COLUMN_BUDGET_AMOUNT = "amount";   // số tiền ngân sách
    private static final String COLUMN_BUDGET_MONTH = "month";     // tháng, ví dụ "2025-05"
    private static final String COLUMN_BUDGET_USERNAME = "username"; // nếu theo user

    private static final String CREATE_TABLE_BUDGET = "CREATE TABLE " + TABLE_BUDGET + "("
            + COLUMN_BUDGET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_BUDGET_AMOUNT + " DECIMAL,"
            + COLUMN_BUDGET_MONTH + " TEXT,"
            + COLUMN_BUDGET_USERNAME + " TEXT" + ")";

    public void setBudget(String username, String month, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Kiểm tra nếu đã có ngân sách tháng này cho user này thì cập nhật, ngược lại insert
        String sqlCheck = "SELECT * FROM " + TABLE_BUDGET + " WHERE " + COLUMN_BUDGET_USERNAME + "=? AND " + COLUMN_BUDGET_MONTH + "=?";
        Cursor cursor = db.rawQuery(sqlCheck, new String[]{username, month});
        ContentValues values = new ContentValues();
        values.put(COLUMN_BUDGET_AMOUNT, amount);
        values.put(COLUMN_BUDGET_MONTH, month);
        values.put(COLUMN_BUDGET_USERNAME, username);

        if (cursor.getCount() > 0) {
            // Update
            db.update(TABLE_BUDGET, values, COLUMN_BUDGET_USERNAME + "=? AND " + COLUMN_BUDGET_MONTH + "=?", new String[]{username, month});
        } else {
            // Insert
            db.insert(TABLE_BUDGET, null, values);
        }
        cursor.close();
        db.close();
    }

    public double getBudget(String username, String month) {
        double budget = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT " + COLUMN_BUDGET_AMOUNT + " FROM " + TABLE_BUDGET + " WHERE " + COLUMN_BUDGET_USERNAME + "=? AND " + COLUMN_BUDGET_MONTH + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username, month});
        if (cursor.moveToFirst()) {
            budget = cursor.getDouble(0);
        }
        cursor.close();
        return budget;
    }
    // Trong MyDatabaseHelper.java
    public double getTotalThu() {
        double totalThu = 0;
        Cursor cursor = GetDate("SELECT SUM(dinhMucThu) FROM thu WHERE deleteFlag = 0");
        if (cursor.moveToFirst()) {
            totalThu = cursor.getDouble(0);
        }
        cursor.close();
        return totalThu;
    }

    public double getTotalChi() {
        double totalChi = 0;
        Cursor cursor = GetDate("SELECT SUM(dinhMucChi) FROM chi WHERE deleteFlag = 0");
        if (cursor.moveToFirst()) {
            totalChi = cursor.getDouble(0);
        }
        cursor.close();
        return totalChi;
    }


}


